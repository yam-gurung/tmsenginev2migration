package com.tms.model;

public class RoleDTO {
	private String roleName;
	private String description;
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "RoleDTO [roleName=" + roleName + ", description=" + description + "]";
	}
	
}
