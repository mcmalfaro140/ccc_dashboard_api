package com.ccc.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ccc.api.model.User;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.util.JwtUtils;

@RestController
public class HomeController {
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
				registry.addMapping("/authenticate").allowedOrigins("http://localhost:3000");
				registry.addMapping("/get_dashboard").allowedOrigins("http://localhost:3000");
				registry.addMapping("/getLogAlarms").allowedOrigins("http://localhost:3000");
				registry.addMapping("/getMetricAlarms").allowedOrigins("http://localhost:3000");
				registry.addMapping("/addMetricAlarms").allowedOrigins("http://localhost:3000");
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
    }
    
    @RequestMapping(path = "/getLogAlarms" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String,Object> getLogAlarms (@RequestHeader("Authorization") String token) {
    	HashMap<String,Object> response = new HashMap<>();
    	String[] sns_str = new String[2];
    	String[] logGroupName_str = new String[2];
    	sns_str[0] = "SNS Topic 1";
    	sns_str[1] = "SNS Topic 1";
    	logGroupName_str[0] = "Log group name 1";
    	logGroupName_str[1] = "Log group name 1";
    	Users user = jwtutils.toUser(token);
    	
    	if(user != null) {
    		String user_name = user.getUsername();
    		System.out.print(user_name);
    		
    		Object[] alarms = new Object[2];
    		HashMap<String,Object> alarm = new HashMap<>();
    		HashMap<String,String> alarmDesc = new HashMap<>();
    		HashMap<String,String[]> sns = new HashMap<>();
    		HashMap<String,String[]> logGroups = new HashMap<>();
    		
    		sns.put("snsTopics", sns_str);
    		logGroups.put("logGroupNames", logGroupName_str);
		
			alarmDesc.put("name", "My alarm name ");
			alarmDesc.put("filter", "Logs > WARN");
			alarmDesc.put("keyword", "");
			alarmDesc.put("isSubscribed", "false");
			alarmDesc.put("filter", "Logs > WARN");
		
			alarm.put("desc",alarmDesc);
			alarm.put("sns_topic", sns);
			alarm.put("log_groups", logGroups);
			
			alarms[0] = alarm;
			alarms[1] = alarm;
    		
        	response.put("data", alarms);
    	}else {
    		HashMap<String,String> error = new HashMap<>();
    		error.put("message", "Authentication failed. Invalid token.");
        	response.put("Error", error);
    	}
    	return response;
    }
    
//    @RequestMapping(path = "/getMetricAlarms" , produces = "application/json; charset=UTF-8")
//    @ResponseBody
//    public HashMap<String,Object> getMetricAlarms (@RequestHeader("Authorization") String token) {
//    	
//    	HashMap<String,Object> response = new HashMap<>();
//    	String[] alarmARN = new String[2];
//    	HashMap<String,String[]> arns = new HashMap<>();
//    	
//    	alarmARN[0] = "arn:aws:cloudwatch:us-west-1:155103565385:alarm:Alarm Test 2";
//    	alarmARN[1] = "arn:aws:cloudwatch:us-west-1:155103565385:alarm:CPUUtilization";
//    	
//    	arns.put("alarms_arn",alarmARN);
//    	
//    	Users user = jwtutils.toUser(token);
//    	if(user != null) {
//    		String user_name = user.getUsername();
//    		System.out.print(user_name);
//    		
//    		response.put("data",arns);
//    
//    	}else {
//    		HashMap<String,String> error = new HashMap<>();
//    		error.put("message", "Authentication failed. Invalid token.");
//        	response.put("Error", error);
//    	}
//    	return response;
//    }
    
    @PostMapping(path = "/addMetricAlarms" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String,Object> addMetricAlarms (@RequestHeader("Authorization") String token,@RequestBody Map<String, String> payload) {
    	HashMap<String,Object> response = new HashMap<>();
    	String arn = payload.get("alarmArn");
    	boolean isSuccessful = true;
    	
    	Users user = jwtutils.toUser(token);
    	if(user != null) {
    		String user_name = user.getUsername();
    		System.out.println(user_name);
    		HashMap<String,String> status = new HashMap<>();
    		if(isSuccessful) {
    			System.out.println(arn);
    			String status_str = "Sucessful";
    			status.put("status", status_str);
    		}else{
    			String status_str = "Fail";
    			status.put("status", status_str);
    		}
    		response.put("response", status);
    
    	}else {
    		HashMap<String,String> error = new HashMap<>();
    		error.put("message", "Authentication failed. Invalid token.");
        	response.put("Error", error);
    	}
    	return response;
    }
    
    
}

