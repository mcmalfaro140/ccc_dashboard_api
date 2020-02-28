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
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/update").allowedOrigins("http://localhost:3000");
				registry.addMapping("/authenticate").allowedOrigins("http://localhost:3000");
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
    
    
//    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(path = "/authenticate", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object>  getFoosBySimplePath(@RequestBody Map<String, String> payload) {
    	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    	HashMap<String, Object> response = new HashMap<>();
    	String inUser = payload.get("username");
    	String inPass = payload.get("password");
    	String dash = payload.get("dash");
    	
    	Users target = usersRepository.findByUsername(inUser);
    	if (target != null)
    	{
    		if(inPass.contentEquals(target.getPassword()))
    		{
    			
    			//JWT
    			Claims claims = Jwts.claims();
    			claims = target.toClaims();
    			String jws = Jwts.builder().setClaims(claims).signWith(key).compact();             
//    			response.put("id",target.getUserId().toString());
//    	    	response.put("username", target.getUsername());
    	    	response.put("dashboard", target.getDashboard());
    	    	response.put("token", jws);
//    	    	response.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDb2RlcnRoZW1lIiwiaWF0IjoxNTU1NjgyNTc1LCJleHAiOjE1ODcyMTg1NzUsImF1ZCI6ImNvZGVydGhlbWVzLmNvbSIsInN1YiI6InRlc3QiLCJmaXJzdG5hbWUiOiJIeXBlciIsImxhc3RuYW1lIjoiVGVzdCIsIkVtYWlsIjoidGVzdEBoeXBlci5jb2RlcnRoZW1lcy5jb20iLCJSb2xlIjoiQWRtaW4ifQ.8qHJDbs5nw4FBTr3F8Xc1NJYOMSJmGnRma7pji0YwB4");
    		}
    		else {
    			response.put("error","Incorrect Password.");
    		}
    	}else {
    	response.put("error", "User Not Found.");
    	}
    	return response;
    }
    
    
//    @CrossOrigin(origins = "http://localhost:3000/dashboard")
    @PostMapping(path = "/update", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap <String, Object> updatemap(@RequestBody Map<String, String> payload)
    {
    	HashMap<String, Object> response = new HashMap<>();
    	String inUser = payload.get("username");
    	String dashBoard = payload.get("dashboard");
    	System.out.println(inUser);
    	System.out.println(dashBoard);
    	response.put("done","Update completed");
    	return response;
//    	Users target = usersRepository.findByUsername(inUser);
//    	
//    	if(target != null)
//    	{
//    		target.setDashboard(dashBoard);
//        	usersRepository.save(target);
//    	}
//    	else {
//    		response.put("error", "user not found");
//    	}
//		return response;
    	
    }
    
    
}

