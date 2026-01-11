package com.tms.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tms.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Page<SecurityUser> getAllUsers(PageRequest pageRequest) {
		return this.userRepository.findAll(pageRequest);
	}
	
	public List<SecurityUser> getAllUsers(){
		return this.userRepository.findAll();
	}

	public SecurityUser getUser(String username) {
		return this.userRepository.findByUsername(username).get();
	}

	public SecurityUser getUser(Long id) {
		return this.userRepository.findById(id).get();
	}

	/*public User createNew(User aUser) {
		return this.userJpaRepository.save(aUser);
	}*/

	public SecurityUser addUser(SecurityUserDto securityUserDto) {
		Optional<Role> role = roleRepository.findByRoleName(securityUserDto.getRolename());

		return this.userRepository.save(new SecurityUser(securityUserDto.getUsername(),
				bCryptPasswordEncoder.encode(securityUserDto.getPassword()), role.get(), securityUserDto.getFirstname(),
				securityUserDto.getLastname()));
	}

	public SecurityUser updateUser(Long id, String username, String password) {
		SecurityUser updateUser = this.userRepository.findById(id).get();
		updateUser.setUsername(username);
		updateUser.setPassword(password);

		return this.userRepository.save(updateUser);
	}

	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

}
