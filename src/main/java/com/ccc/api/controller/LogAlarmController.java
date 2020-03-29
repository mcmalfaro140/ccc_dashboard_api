package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.User;
import com.ccc.api.repository.LogAlarmRepository;
import com.ccc.api.repository.LogLevelCriteriaRepository;

@RestController
public class LogAlarmController {
	@Autowired
	private LogAlarmRepository logAlarmRepo;
	
	@Autowired
	private LogLevelCriteriaRepository logLevelCriteriaRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Bean
	public WebMvcConfigurer logAlarmConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/update").allowedOrigins("http://localhost:3000");
				registry.addMapping("/authenticate").allowedOrigins("http://localhost:3000");
				registry.addMapping("/get_dashboard").allowedOrigins("http://localhost:3000");
			}
		};
	}
	
	@GetMapping(path="/getLogAlarms", produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<List<LogAlarm>> getLogAlarms(@RequestHeader("Authorization") String token) {
		User user = this.jwtUtils.toUser(token);
		
		List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
		List<LogAlarm> userLogAlarms = this.logAlarmRepo.findByUserId(user.getUserId());
		
		List<List<LogAlarm>> logAlarms = new ArrayList<List<LogAlarm>>();
		logAlarms.add(allLogAlarms);
		logAlarms.add(userLogAlarms);
		
		return logAlarms;
	}
	
	@PostMapping(path="/createLogAlarm")
	@ResponseBody
	public void createLogAlarm(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> logAlarmData) {
		User user = this.jwtUtils.toUser(token);
		
		Integer logLevelCriteriaId = this._getLogLevelCriteriaId(logAlarmData);
		String alarmName = (String)logAlarmData.get("alarmName");
	}
	
	private Integer _getLogLevelCriteriaId(Map<String, Object> logAlarmData) {
		String logLevel = (String)logAlarmData.get("logLevel");
		String comparison = (String)logAlarmData.get("comparison");
		
		this.logLevelCriteriaRepo.insertIfNotExists(logLevel, comparison);
		Integer logLevelCriteriaId = this.logLevelCriteriaRepo.getId(logLevel, comparison);
		
		return logLevelCriteriaId;
	}
	
	@PostMapping(path="/deleteLogAlarm")
	@ResponseBody
	public void deleteLogAlarm(@RequestHeader("Authorization") String token, @RequestBody Map<String, Integer> logAlarmIdContainer) {
		User user = this.jwtUtils.toUser(token);
		Integer userId = user.getUserId();
	}
}
