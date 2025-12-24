package com.tms.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tms.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserJpaResource {
	
	private UserService userService;
	
	@Autowired
	public UserJpaResource(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/jpa/users")
	public List<User> getAllUsers(){
		return this.userService.getAllUsers();
	}
	
	/*@GetMapping("/jpa/users/{username}")
	public User getUser(@PathVariable String username) {
		return this.userService.getUser(username);
	}*/
	
	@GetMapping("/jpa/users/{id}")
	public User getUser(@PathVariable Long id) {
		return this.userService.getUser(id);
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		this.userService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/jpa/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		User updatedUser = this.userService.updateUser(id, user.getUsername(), user.getPassword());
	
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/jpa/users")
	public SecurityUser createUser(@RequestBody SecurityUserDto securityUserDto){
		System.out.println(securityUserDto);
		return this.userService.addUser(securityUserDto);
	}
	
	/*
	 * created on 19 dec 2025
	 */ 
	/*@PostMapping("/jpa/users")
	public User createUser(@RequestBody User user) {
		return this.userService.createNew(user);
	}*/

}
