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
//import org.slf4j.MDC;


@RestController
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/")
    public String hello() {
        return "Hello World in Spring Boot misael";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath(@RequestHeader Map<String, String> headers) {
    	Boolean[] arr = {false}; 
    	String[] user = {""};
    	headers.forEach((key, value) -> {
    		if(key.equals("user")) {
    			user[0] = value;
    		}
        });
    	
    	return "User found return true you are "+ user[0];
    	
//    	if(arr[0]) {
//    		return "User found return true you are "+ user[0];
//    	}else {
//    		return "User NOT found return true";
//    	}
    	
        
    }
}
