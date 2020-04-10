package com.ccc.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ccc.api.model.User;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.util.JwtUtils;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtutils;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/update").allowedOrigins("http://localhost:3000");
				registry.addMapping("/get_dashboard").allowedOrigins("http://localhost:3000");
				
				registry.addMapping("/getLogAlarms").allowedOrigins("http://localhost:3000");
				registry.addMapping("/createLogAlarm").allowedOrigins("http://localhost:3000");
				registry.addMapping("/subscribeToLogAlarm").allowedOrigins("http://localhost:3000");
				registry.addMapping("/unsubscribeToLogAlarm").allowedOrigins("http://localhost:3000");
				registry.addMapping("/deleteLogAlarm").allowedOrigins("http://localhost:3000");
			}
		};
	}
    
    @PostMapping(path = "/authenticate", produces = "application/json; charset=UTF-8", consumes = "application/json; charset=UTF-8")
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
    			response.put("Error","Incorrect Password.");
    		}
    	} 
    	else {
    		response.put("Error", "User Not Found.");
    	}
    	
    	return response;
    }
    
    @PostMapping(path = "/update", produces = "application/json; charset=UTF-8", consumes = "application/json; charset=UTF-8")
    @ResponseBody
    public Object updateDahboard(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> payload) {
    	HashMap<String, Object> response = new HashMap<>();
    	String dashBoard = payload.get("dashboard");

    	User toUser = jwtutils.toUser(token);
    	
    	if (toUser != null) {
    		String username = toUser.getUsername();
        	User target = userRepository.findByUsername(username);
        	if(target != null){
        		if(dashBoard != null) {
        			target.setDashboard(dashBoard);
            		userRepository.save(target);
            		response.put("Success", "Update completed.");
        		}else {
        			response.put("Error", "Missing Parameters.");
        		}
        	}else {
        		response.put("Error", "User not found.");
        	}
    	}else {
    		response.put("Error", "Invalid Token.");
    	}
    	return response;
    }
    
    @GetMapping(path = "/getDashboard" , produces = "application/json; charset=UTF-8", consumes = "application/json; charset=UTF-8")
    @ResponseBody
    public Object getdashboard(@RequestHeader("Authorization") String token) {
    	HashMap<String, Object> response = new HashMap<>();
    	User toUsers = jwtutils.toUser(token);
    	
    	if(toUsers != null) {
    		String inUser = toUsers.getUsername();
    		User target = userRepository.findByUsername(inUser);
    		if(target != null) {
            	response.put("dashboard", target.getDashboard());
    		}else {
    			response.put("Error", "User not found.");
    		}
    	}else {
    		response.put("Error", "Invalid Token.");
    	}
    	return response;
    }
}
