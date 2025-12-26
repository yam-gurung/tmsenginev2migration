package com.tms.roles;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.tms.model.RoleDTO;
import com.tms.user.Role;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class RoleJpaResource {
	
	private RoleService roleService;
	
	@Autowired
	public RoleJpaResource(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping("/jpa/roles")
	public List<Role> getAllUserRoles(){
		return roleService.getAllRoles();
	}
	
	@PostMapping("/jpa/roles")
	public Role createRole(@RequestBody RoleDTO roleDTO) {
		return this.roleService.createRole(roleDTO);
	}

}
