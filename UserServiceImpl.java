package com.User.microservice.dao;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.User.microservice.dao.exception.ResourceNotFoundException;
import com.User.microservice.entities.Hotel;
import com.User.microservice.entities.Rating;
import com.User.microservice.entities.User;
import com.User.microservice.external.services.HotelService;
import com.User.microservice.repository.UserRepository;
import com.User.microservice.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//@Autowired
	//private HotelService hotelService;
	
	@Override
	public User saveUser(User user) {
		
		
		String randomUseId= UUID.randomUUID().toString();
		
		user.setUserId(randomUseId);
		
		return userRepo.save(user);
	
	}

	@Override
	public List<User> getAllUser() {
	
		return userRepo.findAll();
		
	}

	@Override
	public User getUser(String userId) {
		
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id dose not exist :"+userId));
	
		//http://localhost:8083/ratings/users/b4caf1f9-1ac3-44bd-a3b5-857e02e668b2
		
		ArrayList<Rating> l = restTemplate.getForObject("http://RATING-MICROSERVICE/ratings/users/b4caf1f9-1ac3-44bd-a3b5-857e02e668b2",ArrayList.class);
		
		
	//	List<Rating> ratingList=l.stream().map(rating -> { 
			
	//		Hotel hotel = hotelService.getHotel(rating.getHotelId());
			
	//		rating.setHotel(hotel);
			
	//		return rating;
	//		
	//	}).collect(Collectors.toList());
		
		
		user.setRatings(l);
		
		return user;
				
	}

}
