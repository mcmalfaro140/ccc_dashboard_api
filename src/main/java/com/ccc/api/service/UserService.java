package com.ccc.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccc.api.model.User;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.util.JwtUtils;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public Map<String, Object> getByUsernameAndPassword(String username, String password) {
		HashMap<String, Object> response = new HashMap<String, Object>();
    	User target = this.userRepo.findByUsername(username);
    	
    	if (target != null) {
    		if (password.contentEquals(target.getPassword())) {
    			String jwt = this.jwtUtils.toToken(target);
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
	
	public Map<String, Object> updateDashboard(String token, String dashboard) {
		HashMap<String, Object> response = new HashMap<String, Object>();
    	User user = this.jwtUtils.toUser(token);
    	
    	if (user == null) {
    		response.put("error", "token is having issue");
    		return response;
    	}
    	
    	String username = user.getUsername();
    	User target = this.userRepo.findByUsername(username);
    	
    	if (target != null) {
    		target.setDashboard(dashboard);
    		this.userRepo.save(target);
    	}
    	else {
    		response.put("error", "user not found");
    	}
    	
		return response;
	}
	
	public Map<String, Object> getDashboard(String token) {
		User user = this.jwtUtils.toUser(token);
    	String inUser = user.getUsername();
    	User target = this.userRepo.findByUsername(inUser);
    	
    	HashMap<String, Object> response = new HashMap<>();
    	response.put("dashboard", target.getDashboard());
    	
    	return response;
	}
}
