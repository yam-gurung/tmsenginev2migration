package com.tms.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tms.model.RoleDTO;
import com.tms.roles.RoleService;
import com.tms.service.UserService;

//@Component
public class CreateUserCommandLiner implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public void run(String... args) throws Exception {
		
		RoleDTO roleDTO=new RoleDTO();
		roleDTO.setRoleName("ROLE_ADMIN");
		roleDTO.setDescription("ROLE ADMIN");
		
		RoleDTO roleDTO2=new RoleDTO();
		roleDTO2.setRoleName("ROLE_USER");
		roleDTO2.setDescription("ROLE USER");
		
		this.roleService.createRole(roleDTO);
		this.roleService.createRole(roleDTO2);
		
		
		SecurityUserDto sUser=new SecurityUserDto();
		sUser.setUsername("admin");
		sUser.setFirstname("yam");
		sUser.setLastname("gurung");
		sUser.setPassword("admin");
		sUser.setRolename("ROLE_ADMIN");
		this.userService.addUser(sUser);
		System.out.println("admin user setup done from CreateUserCommandLiner");
	}

}
