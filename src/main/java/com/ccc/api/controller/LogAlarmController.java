package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.User;
import com.ccc.api.repository.LogAlarmRepository;
import com.ccc.api.util.JwtUtils;

@RestController
@Transactional
public class LogAlarmController {
	@Autowired
	private LogAlarmRepository logAlarmRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(path="/getLogAlarms", produces="application/json; charset=UTF-8")
	public List<List<LogAlarm>> getLogAlarms() {
		List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
		List<LogAlarm> userLogAlarms = new ArrayList<LogAlarm>();
		
		Collections.copy(allLogAlarms, userLogAlarms);
		userLogAlarms.removeIf((elem) -> {
			for (User user : elem.getUserList()) {
				if (1 == user.getUserId()) {
					return false;
				}
			}
			
			return true;
		});
		
		List<List<LogAlarm>> logAlarms = new ArrayList<List<LogAlarm>>();
		logAlarms.add(allLogAlarms);
		logAlarms.add(userLogAlarms);
		
		return logAlarms;
	}
}
