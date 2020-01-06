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
    @RequestMapping(path = "/authenticate", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object>  getFoosBySimplePath(@RequestBody Map<String, String> payload) {
    	String userName = "misael";
    	String pass = "test";
    	HashMap<String, Object> response = new HashMap<>();
    	String testUser = payload.get("username");
    	String testPass = payload.get("password");
    	
    	if(testUser.equals(userName) && testPass.equals(pass)) {
    		response.put("id", "1");
    		response.put("username", userName);
    		response.put("firstName", "Misael");
    		response.put("lastName", "Corvera");
    		response.put("role", "Admin");
    		response.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDb2RlcnRoZW1lIiwiaWF0IjoxNTU1NjgyNTc1LCJleHAiOjE1ODcyMTg1NzUsImF1ZCI6ImNvZGVydGhlbWVzLmNvbSIsInN1YiI6InRlc3QiLCJmaXJzdG5hbWUiOiJIeXBlciIsImxhc3RuYW1lIjoiVGVzdCIsIkVtYWlsIjoidGVzdEBoeXBlci5jb2RlcnRoZW1lcy5jb20iLCJSb2xlIjoiQWRtaW4ifQ.8qHJDbs5nw4FBTr3F8Xc1NJYOMSJmGnRma7pji0YwB4");
		    response.put("Dashboard", "[{\"objectType\":\"graph\",\"graphSettings\":{\"type\":\"line\",\"realTime\":\"false\",\"metricName\":\"CPUUtilization\",\"nameSpace\":\"AWS/EC2\",\"chartName\":\"Test\",\"instanceId\":\"i-01e27ec0da2c4d296\",\"refreshRate\":\"\",\"period\":180},\"coordinates\":{\"x\":0,\"y\":1,\"w\":20,\"h\":19,\"minW\":6,\"minH\":9}}]");
    	}else {
    		response.put("error", "Username or password is incorrect");
    	}
    	
    	return response;
    }
}
