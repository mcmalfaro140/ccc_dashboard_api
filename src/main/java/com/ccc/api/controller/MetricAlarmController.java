package com.ccc.api.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.model.Users;
import com.ccc.api.repository.MetricAlarmRepository;
import com.ccc.api.model.MetricAlarm;
import com.ccc.api.controller.JwtUtils;
//import com.ccc.api.util.JwtUtils;

@RestController
@Transactional
public class MetricAlarmController {
	@Autowired
	private MetricAlarmRepository metricAlarmRepo;
	
	@Autowired
	private JwtUtils jwtutils;
	
	 @RequestMapping("/metric")
	public List<MetricAlarm> metricAlarm() {
		 List<MetricAlarm> allMetricAlarms = this.metricAlarmRepo.findAll();
	        return allMetricAlarms;
	}
	
	@RequestMapping(path = "/getMetricAlarms" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object getMetricAlarms (@RequestHeader("Authorization") String token) {
		HashMap<String,Object> response = new HashMap<>();
    	Users user = jwtutils.toUser(token);
    	if(user != null) {
    		List<MetricAlarm> allMetricAlarms = this.metricAlarmRepo.findAll();
    		response.put("Data", allMetricAlarms);
    	}else {
    		HashMap<String,String> Error = new HashMap<>();
    		Error.put("message","Invalid Token");
    		response.put("Error", Error);
    	}
    	return response;
    }

}
