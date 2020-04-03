package com.ccc.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ccc.api.model.User;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.util.JwtUtils;

@RestController
public class UserController {
	/*@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtutils;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/update").allowedOrigins("http://localhost:3000");
				registry.addMapping("/authenticate").allowedOrigins("http://localhost:3000");
				registry.addMapping("/get_dashboard").allowedOrigins("http://localhost:3000");
			}
		};
	}


    @RequestMapping("/")
    public String hello() {
        return "Hello, BACKEND server for the CCC Dashboard Project";
    }
    
    @RequestMapping(path = "/authenticate", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String , Object> getUsersBySimplePath(@RequestBody Map<String, String> payload) {
    	HashMap<String, Object> response = new HashMap<>();
    	String inUser = payload.get("username");
    	String inPass = payload.get("password");
    	User target = userRepository.findByUsername(inUser);
    	
    	if (target != null) {
    		if (inPass.contentEquals(target.getPassword())) {
    			String jwt = jwtutils.toToken(target);
    	    	response.put("username", target.getUsername());
    	    	response.put("token", jwt);
    		}
    		else {
    			response.put("error","Incorrect Password.");
    		}
    	} 
    	else {
    		response.put("error", "User Not Found.");
    	}
    	
    	return response;
    }
    
    @PostMapping(path = "/update", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> updatemap(@RequestBody Map<String, String> payload) {
    	HashMap<String, Object> response = new HashMap<>();
    	String token = payload.get("token");
    	String dashBoard = payload.get("dashboard");

    	User toUser = jwtutils.toUser(token);
    	
    	if (toUser == null) {
    		response.put("error", "token is having issue");
    		return response;
    	}
    	
    	String inUser = toUser.getUsername();
    	User target = userRepository.findByUsername(inUser);
    	
    	if (target != null) {
    		target.setDashboard(dashBoard);
    		userRepository.save(target);
    	}
    	else {
    		response.put("error", "user not found");
    	}
    	
		return response;
    }
    
    @RequestMapping(path = "/get_dashboard" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> getdashboard(@RequestHeader("Authorization") String token) {
    	User toUsers = jwtutils.toUser(token);
    	String inUser = toUsers.getUsername();
    	User target = userRepository.findByUsername(inUser);
    	
    	HashMap<String, Object> response = new HashMap<>();
    	response.put("dashboard", target.getDashboard());
    	
    	return response;
    }*/
}
