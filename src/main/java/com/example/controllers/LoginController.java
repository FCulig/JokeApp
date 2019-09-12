package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	
	@RequestMapping(value="/login", produces = "text/plain")
    public String login(/*@RequestBody User user*/) {
		/*User us = userRepository.findByUsername(user.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(us != null) {
			if(encoder.matches(user.getPassword(), us.getPassword())) {
				return us;
			}else {
				return null;
			}
		}else {
			return null;
		}*/
		
		return "{\"success\":1}";
    }

}
