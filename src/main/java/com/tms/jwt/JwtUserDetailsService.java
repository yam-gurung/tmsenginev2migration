package com.tms.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tms.user.SecurityUser;
import com.tms.user.UserRepository;
import static org.springframework.security.core.userdetails.User.withUsername;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	//static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();
	
	@Autowired
	private UserRepository userRepository;
	
	/*static {
		inMemoryUserList.add(new JwtUserDetails(1L, "in28minutes",
				"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e", "ROLE_USER_2"));
		inMemoryUserList.add(new JwtUserDetails(2L, "ranga",
				"$2a$10$IetbreuU5KihCkDB6/r1DOJO0VyU9lSiBcrMDT.biU7FOt2oqZDPm", "ROLE_USER_2"));
		
		//$2a$10$IetbreuU5KihCkDB6/r1DOJO0VyU9lSiBcrMDT.biU7FOt2oqZDPm
	}*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
				.filter(user -> user.getUsername().equals(username)).findFirst();
		

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return findFirst.get();*/
		
		SecurityUser user = this.userRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException(String.format("User with name %s does not exist", username)));

		return withUsername(user.getUsername())
		    .password(user.getPassword())
		    .authorities(user.getRoles())
		    .accountExpired(false)
		    .accountLocked(false)
		    .credentialsExpired(false)
		    .disabled(false)
		    .build();
	}

}
