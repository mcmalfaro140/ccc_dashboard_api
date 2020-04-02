package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.User;
import com.ccc.api.repository.LogAlarmRepository;
import com.ccc.api.repository.LogLevelCriteriaRepository;
import com.ccc.api.util.JwtUtils;

@RestController
@Transactional
public class LogAlarmController {
	@Autowired
	private LogAlarmRepository logAlarmRepo;
	
	@Autowired
	private LogLevelCriteriaRepository logLevelCriteriaRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(path="/getLogAlarms", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	public List<List<LogAlarm>> getLogAlarms(@RequestHeader(name="Autorization") String token) {
		User user = this.jwtUtils.toUser(token);
		List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
		List<LogAlarm> userLogAlarms = this._getUserLogAlarms(allLogAlarms, user);
		
		List<List<LogAlarm>> logAlarms = new ArrayList<List<LogAlarm>>(2);
		logAlarms.add(allLogAlarms);
		logAlarms.add(userLogAlarms);
		
		return logAlarms;
	}
	
	private List<LogAlarm> _getUserLogAlarms(List<LogAlarm> allLogAlarms, User user) {
		List<LogAlarm> userLogAlarms = new ArrayList<LogAlarm>(allLogAlarms.size());
		
		for (LogAlarm alarm : allLogAlarms) {
			if (alarm.getUserList().contains(user)) {
				userLogAlarms.add(alarm);
			}
		}
		
		return userLogAlarms;
	}
	
	@PostMapping(path="/createLogAlarm", consumes="applcation/json; charset=UTF-8")
	public void createLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> logAlarmInfo) {
		String logLevel = logAlarmInfo.get("LogLevel");
		String comparison = logAlarmInfo.get("Comparison");
		String alarmName = logAlarmInfo.get("AlarmName");
		String keywordRelationship = logAlarmInfo.get("KeywordRelationship");
		
		this.logLevelCriteriaRepo.insertIgnore(logLevel, comparison);
		Long logLevelCriteriaId = this.logLevelCriteriaRepo.findId(logLevel, comparison);
		
		this.logAlarmRepo.insert(logLevelCriteriaId, alarmName, keywordRelationship);
	}
}
