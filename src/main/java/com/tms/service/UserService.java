package com.tms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.tms.user.SecurityUser;
import com.tms.user.SecurityUserDto;

public interface UserService {
	Page<SecurityUser> getAllUsers(PageRequest pageRequest);
	List<SecurityUser> getAllUsers();
	SecurityUser getUser(String username);
	SecurityUser getUser(Long id);
	SecurityUser addUser(SecurityUserDto securityUserDto);
	SecurityUser updateUser(Long id, String username, String password);
	void deleteUser(Long id);
}
