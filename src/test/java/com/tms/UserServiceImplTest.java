package com.tms;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tms.service.UserService;
import com.tms.user.UserRepository;

@SpringBootTest
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	public void test() {
		
		
	}
}
