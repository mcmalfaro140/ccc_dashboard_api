package com.ccc.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ccc.api.service.UserService;

@RestController
@Transactional
public class UserController {
	@Autowired
	private UserService userService;
	
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
    
    @RequestMapping(path = "/authenticate", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getUsersBySimplePath(@RequestBody Map<String, String> payload) {
    	String username = payload.get("username");
    	String password = payload.get("password");
    	
    	return this.userService.getByUsernameAndPassword(username, password);
    }
    
    @PostMapping(path = "/update", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> updateDashboard(@RequestBody Map<String, String> payload) {
    	String token = payload.get("token");
    	String dashboard = payload.get("dashboard");
    	
    	return this.userService.updateDashboard(token, dashboard);
    }
    
    @RequestMapping(path = "/get_dashboard" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getDashboard(@RequestHeader("Authorization") String token) {
    	return this.userService.getDashboard(token);
    }
}
