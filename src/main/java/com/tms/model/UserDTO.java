package com.tms.model;

public class UserDTO {
	
	private String username;
	
	private String password;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
