package com.tms.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
