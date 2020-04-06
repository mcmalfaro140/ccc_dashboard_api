package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.model.User;
import com.ccc.api.repository.MetricAlarmRepository;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.model.MetricAlarm;
import com.ccc.api.util.JwtUtils;

@RestController
@Transactional
public class MetricAlarmController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MetricAlarmRepository metricAlarmRepo;
	
	@Autowired
	private JwtUtils jwtutils;
	
	
	@RequestMapping(path = "/getMetricAlarms" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object getMetricAlarms (@RequestHeader("Authorization") String token) {
		HashMap<String,Object> response = new HashMap<>();
		HashMap<String,String> Error = new HashMap<>();
    	User user = jwtutils.toUser(token);
    	if(user != null) {
    		User target = userRepository.findByUsername(user.getUsername());
    		
    		HashMap<String,Object> alarms = new HashMap<>();
    		try {
    			List<MetricAlarm> allMetricAlarms = this.metricAlarmRepo.findAll();
        		List<MetricAlarm> userMetricAlarms = metricAlarmRepo.findAlarmsById(target.getUserId().toString());
        		alarms.put("all", allMetricAlarms);
        		alarms.put("user", userMetricAlarms);
        		response.put("Data", alarms);
    		}catch (Exception e) {
    			Error.put("message","No metric alarms found.");
        		response.put("Error", Error);
    		}    		
    	}else {
    		Error.put("message","Invalid Token.");
    		response.put("Error", Error);
    	}
    	return response;
    }
	

	@PostMapping(path = "/deleteMetricAlarms" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object deleteMeticAlarms (@RequestHeader("Authorization") String token, @RequestBody Map<String,ArrayList<String>> payload) {
		HashMap<String,Object> response = new HashMap<>();
	    User user = jwtutils.toUser(token);
	   	if(user != null) {
	   		ArrayList<String> ids = payload.get("ids");
	   		
	   		for (int i = 0; i < ids.size(); i++) { 
		   		metricAlarmRepo.deleteAlarmsById(ids.get(i));
		        metricAlarmRepo.cleanXRefUserMetricAlarmById(ids.get(i));
	   		}
	        
	   		HashMap<String,String> Complete = new HashMap<>();
	    	Complete.put("message","Table MetricAlarms and XRefUserMetricAlarm Updated.");
	    	response.put("Data", Complete);
	   	}else {
    		HashMap<String,String> Error = new HashMap<>();
	    	Error.put("message","Invalid Token.");
	    	response.put("Error", Error);
	   	}
	   	return response;
	}
	
	@PostMapping(path = "/subscribeToMetricAlarm" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object subscribeToMeticAlarm (@RequestHeader("Authorization") String token, @RequestBody Map<String,String> payload) {
		HashMap<String,Object> response = new HashMap<>();
		HashMap<String,String> Message = new HashMap<>();
	    User user = jwtutils.toUser(token);
	   	if(user != null) {
	   		Long target = userRepository.findById(user.getUserId()).get().getUserId();
	   		if(payload.get("alarmArn") != null) {
	   			String alarmArn = payload.get("alarmArn");
	   			MetricAlarm newAlarm = metricAlarmRepo.getMetricAlarm(alarmArn);
	   			if(newAlarm == null) {
	   				metricAlarmRepo.insertNewMetricAlarm(alarmArn);
	   				newAlarm = metricAlarmRepo.getMetricAlarm(alarmArn);
	   			}
	   			metricAlarmRepo.insertNewXRefUserMetricAlarm(target.toString(), newAlarm.getMetricAlarmId().toString());
	   			Message.put("message","Successfully subscribed to " + alarmArn);
		   		response.put("Success", Message);
	   		}else {
		    	Message.put("message","Missing Parameter.");
		    	response.put("Error", Message);
	   		}
	   	}else {
	    	Message.put("message","Invalid Token.");
	    	response.put("Error", Message); 
	   	}
	   	return response;
	}
	
	
	@RequestMapping(path = "/unsubscribeToMetricAlarm" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object unsubscribeToMeticAlarm (@RequestHeader("Authorization") String token, @RequestBody Map<String,String> payload) {
		HashMap<String,Object> response = new HashMap<>();
		HashMap<String,String> Message = new HashMap<>();
	    User user = jwtutils.toUser(token);
	   	if(user != null) {
	   		Long target = userRepository.findById(user.getUserId()).get().getUserId();
	   		if(payload.get("alarmArn") != null) {
	   			String alarmArn = payload.get("alarmArn");
	   			MetricAlarm newAlarm = metricAlarmRepo.getMetricAlarm(alarmArn);
	   			metricAlarmRepo.deleteXRefUserMetricAlarm(target.toString(), newAlarm.getMetricAlarmId().toString());
	   			Message.put("message","Successfully unsubscribed to "+alarmArn);
		   		response.put("Success", Message);
	   		}else {
		    	Message.put("message","Missing Parameter.");
		    	response.put("Error", Message);
	   		}
	   	}else {
	    	Message.put("message","Invalid Token.");
	    	response.put("Error", Message);
	   	}
	   	return response;
	}

}
