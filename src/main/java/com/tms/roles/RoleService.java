package com.tms.roles;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import com.tms.user.RoleRepository;
import com.tms.model.RoleDTO;
import com.tms.user.Role;

@Service
public class RoleService {
	private RoleRepository roleRepository;
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public List<Role> getAllRoles(){
		return this.roleRepository.findAll();
	}
	
	public void saveAllRoles(List<RoleDTO> roles) {
		List<Role> roleList=new ArrayList<>();
		for(RoleDTO role:roles) {
			roleList.add(new Role(role.getRoleName(),role.getDescription()));
		}
		this.roleRepository.saveAll(roleList);
	}
	
	public Role createRole(RoleDTO roleDTO) {
		return this.roleRepository.save(new Role(roleDTO.getRoleName(),roleDTO.getDescription()));
	}
}
