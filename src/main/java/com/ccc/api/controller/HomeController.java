package com.ccc.api.controller;


import java.util.Map;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestHeader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ccc.api.model.Users;
import com.ccc.api.model.dao.UsersDao;
//import org.slf4j.MDC;



@RestController
public class HomeController {
	
	@Autowired
    private UsersDao usersDao;
	
	 @RequestMapping("/Users")
    public List<Users> users(ModelMap models) {
        return usersDao.getUsers();
    }

    @RequestMapping("/")
    public String hello() {
        return "Hello World in Spring Boot misael";
    }
    
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(path = "/login", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object>  getFoosBySimplePath(@RequestBody Map<String, String> payload) {
    	String userName = "misael";
    	String pass = "test";
    	HashMap<String, Object> response = new HashMap<>();
    	String testUser = payload.get("username");
    	String testPass = payload.get("password");
    	
    	if(testUser.equals(userName) && testPass.equals(pass)) {
    		response.put("User", userName);
		    response.put("Dashboard", "none");
    	}else {
    		response.put("error", "Username or password is incorrect");
    	}
    	
    	return response;
    }
}
