package com.tms.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tms.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserJpaResource {
	
	private UserService userService;
	
	@Autowired
	public UserJpaResource(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/jpa/users")
	public PagedModel<?> getAllUsers(
			@RequestParam(name="page",defaultValue="0") int page
			,@RequestParam(name="size",defaultValue="0") int size){
		PageRequest pageRequest= PageRequest.of(page, size);
		Page<SecurityUser> userPageResult = this.userService.getAllUsers(pageRequest);
		return new PagedModel<>(userPageResult);
	}
	
	@GetMapping("/jpa/users/{id}")
	public SecurityUser getUser(@PathVariable Long id) {
		return this.userService.getUser(id);
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		this.userService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/jpa/users/{id}")
	public ResponseEntity<SecurityUser> updateUser(@PathVariable Long id, @RequestBody SecurityUser user){
		SecurityUser updatedUser = this.userService.updateUser(id, user.getUsername(), user.getPassword());
	
		return new ResponseEntity<SecurityUser>(user, HttpStatus.OK);
	}
	
	@PostMapping("/jpa/users")
	public SecurityUser createUser(@RequestBody SecurityUserDto securityUserDto){
		System.out.println(securityUserDto);
		return this.userService.addUser(securityUserDto);
	}

}
