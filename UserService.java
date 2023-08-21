package com.User.microservice.services;

import java.util.List;

import com.User.microservice.entities.User;

public interface UserService {
	
	//create
	
	User saveUser(User user);
	
	//get all user
	
	List<User> getAllUser();
	
	//get single user of given userId
	
	User getUser(String userId);
	
	//TODO: delete
	
	//TODO: update

}
