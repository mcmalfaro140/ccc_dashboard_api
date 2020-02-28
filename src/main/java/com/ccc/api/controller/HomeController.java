package com.ccc.api.controller;


import java.util.Map;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.web.bind.annotation.RequestHeader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ccc.api.model.Users;
import com.ccc.api.model.dao.UsersDao;
//import org.slf4j.MDC;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@RestController
public class HomeController {
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
    private UsersDao usersDao;
	
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
	
	 @RequestMapping("/Users")
    public List<Users> users(ModelMap models) {
        return usersDao.getUsers();
    }

    @RequestMapping("/")
    public String hello() {
        return "Hello World in Spring Boot misael";
    }
    
    
    @RequestMapping(path = "/authenticate", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String , Object>  getFoosBySimplePath(@RequestBody Map<String, String> payload) {
    	HashMap<String, Object> response = new HashMap<>();
    	String inUser = payload.get("username");
    	String inPass = payload.get("password");
    	String dash = payload.get("dash");
    	
    	Users target = usersRepository.findByUsername(inUser);
    	if (target != null)
    	{
    		if(inPass.contentEquals(target.getPassword()))
    		{
    			String jws = jwtutils.toToken(target);
    			System.out.println(jws);
    	    	response.put("dashboard", target.getDashboard());
    	    	response.put("token", jws);
    		}
    		else {
    			response.put("error","Incorrect Password.");
    		}
    	}else {
    	response.put("error", "User Not Found.");
    	}
    	return response;
    }
    
    
    
    @PostMapping(path = "/update", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap <String, Object> updatemap(@RequestBody Map<String, String> payload)
    {
    	HashMap<String, Object> response = new HashMap<>();
    	String token = payload.get("token");
    	String dashBoard = payload.get("dashboard");

    	Users toUsers = jwtutils.toUser(token);
    	if (toUsers == null)
    	{
    		response.put("error", "token is having issue");
    		return response;
    	}
    	String inUser = toUsers.getUsername();
    	Users target = usersRepository.findByUsername(inUser);
    	
    	if(target != null)
    	{
    		target.setDashboard(dashBoard);
        	usersRepository.save(target);
    	}
    	else {
    		response.put("error", "user not found");
    	}
		return response;
    }
    
    @RequestMapping(path = "/get_dashboard" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getdashboard (@RequestHeader("Authorization") String token) {
    	Users toUsers = jwtutils.toUser(token);
    	String inUser = toUsers.getUsername();
//    	System.out.println(inUser);
    	Users target = usersRepository.findByUsername(inUser);
    	return target.getDashboard();
    }
}

