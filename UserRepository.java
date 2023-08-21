package com.User.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.User.microservice.entities.User;

@Component
public interface UserRepository extends JpaRepository<User,String> {

	
	
}
