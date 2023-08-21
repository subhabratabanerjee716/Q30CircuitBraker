package com.User.microservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.User.microservice.entities.User;
import com.User.microservice.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	
	
	//create
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		User user1=userservice.saveUser(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
		
	}
	
	
	
	//single user get
	
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name="ratingFallBack",fallbackMethod = "ratingFallBackMethod")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		
		User user1=userservice.getUser(userId);
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	//creating ratingFallBackMethod for circuit breaker
	
	public ResponseEntity<User> ratingFallBackMethod(String userId,Exception ex){
		
		User u = new User();
		
		u.setEmail("dummy@hmail.com");
		u.setName("Dummy");
		u.setAbout("Creating dummy user because one service is down");
		u.setUserId("12345");
		
		return new ResponseEntity<>(u,HttpStatus.OK); 
		
		
	} 
	
	
	//all users get
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		
	  List<User> list=userservice.getAllUser();
	  
	  return ResponseEntity.status(HttpStatus.CREATED).body(list);
		
	}

}
