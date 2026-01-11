package com.tms.model;

public class SecurityUserResponse {
	
	public SecurityUserResponse(String username, String firstname, String lastname, String rolename) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.rolename = rolename;
	}
	
	private String username;
	private String firstname;
	private String lastname;
	private String rolename;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
