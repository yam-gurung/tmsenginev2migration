package com.tms.roles;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Role createRole(RoleDTO roleDTO) {
		return this.roleRepository.save(new Role(roleDTO.getRoleName(),roleDTO.getDescription()));
	}
}
