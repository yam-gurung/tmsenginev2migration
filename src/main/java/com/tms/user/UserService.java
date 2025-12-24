package com.tms.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tms.model.User;

@Service
public class UserService {

	private UserJpaRepository userJpaRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	public List<User> getAllUsers() {
		return this.userJpaRepository.findAll();
	}

	public User getUser(String username) {
		return this.userJpaRepository.findByUsername(username);
	}

	public User getUser(Long id) {
		return this.userJpaRepository.findById(id).get();
	}

	public User createNew(User aUser) {
		return this.userJpaRepository.save(aUser);
	}

	public SecurityUser addUser(SecurityUserDto securityUserDto) {
		Optional<Role> role = roleRepository.findByRoleName(securityUserDto.getRolename());

		return this.userRepository.save(new SecurityUser(securityUserDto.getUsername(),
				bCryptPasswordEncoder.encode(securityUserDto.getPassword()), role.get(), securityUserDto.getFirstname(),
				securityUserDto.getLastname()));
	}

	public User updateUser(Long id, String username, String password) {
		User updateUser = this.userJpaRepository.findById(id).get();
		updateUser.setUsername(username);
		updateUser.setPassword(password);

		return this.userJpaRepository.save(updateUser);
	}

	public void deleteUser(Long id) {
		this.userJpaRepository.deleteById(id);
	}

}
