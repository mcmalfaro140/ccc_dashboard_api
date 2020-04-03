  
package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.ccc.api.model.Keyword;
import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.LogGroup;
import com.ccc.api.model.SNSTopic;
import com.ccc.api.model.User;
import com.ccc.api.repository.KeywordRepository;
import com.ccc.api.repository.LogAlarmRepository;
import com.ccc.api.repository.LogGroupRepository;
import com.ccc.api.repository.SNSTopicRepository;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.util.GlobalVariables;
import com.ccc.api.util.JwtUtils;

@RestController
@Transactional
public class LogAlarmController {
	@Autowired
	private LogAlarmRepository logAlarmRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private LogGroupRepository logGroupRepo;
	
	@Autowired
	private KeywordRepository keywordRepo;
	
	@Autowired
	private SNSTopicRepository snsTopicRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(path="/getLogAlarms", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	public List<List<LogAlarm>> getLogAlarms(@RequestHeader(name="Authroization") String token) {		
		List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
		List<LogAlarm> userLogAlarms = this.getUserLogAlarms(allLogAlarms, token);
		
		List<List<LogAlarm>> logAlarms = new ArrayList<List<LogAlarm>>();
		logAlarms.add(allLogAlarms);
		logAlarms.add(userLogAlarms);
		
		return logAlarms;
	}
	
	private List<LogAlarm> getUserLogAlarms(List<LogAlarm> allLogAlarms, String token) {
		User user = this.jwtUtils.toUser(token);
		List<LogAlarm> userLogAlarms = new ArrayList<LogAlarm>(allLogAlarms.size());
		
		for (LogAlarm alarm : allLogAlarms) {
			if (alarm.getUserList().contains(user)) {
				userLogAlarms.add(alarm);
			}
		}
		
		return userLogAlarms;
	}
	
	@PostMapping(path="/createLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	public Map<String, String> createLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> logAlarmInfo) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			String alarmName = logAlarmInfo.get("AlarmName");
			String keywordRelationship = logAlarmInfo.get("KeywordRelationship");
			String logLevel = logAlarmInfo.get("LogLevel");
			String comparison = logAlarmInfo.get("Comparison");
			String[] logGroupNames = logAlarmInfo.get("LogGroups").split(",");
			String[] keywordNames = logAlarmInfo.get("Keywords").split(",");
			String[] snsTopicNames = logAlarmInfo.get("SNSTopicNames").split(",");
			List<User> userList = this.getUser(user);
			List<LogGroup> logGroupList = this.getLogGroupList(logGroupNames);
			List<Keyword> keywordList = this.getKeywordList(keywordNames);
			List<SNSTopic> snsTopicList = this.getSNSTopicList(snsTopicNames);
			
			LogAlarm logAlarm = new LogAlarm(alarmName, keywordRelationship, logLevel, comparison, userList, logGroupList, keywordList, snsTopicList);
			
			this.logAlarmRepo.save(logAlarm);
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	private List<User> getUser(User user) {
		user = this.userRepo.findById(user.getUserId()).get();
		
		List<User> userList = new ArrayList<User>(1);
		userList.add(user);
		
		return userList;
	}
	
	private List<LogGroup> getLogGroupList(String[] logGroupNameList) {
		List<LogGroup> logGroupList = new ArrayList<LogGroup>(logGroupNameList.length);
		
		for (String logGroupName : logGroupNameList) {
			this.logGroupRepo.insertIgnore(logGroupName);
			LogGroup logGroup = this.logGroupRepo.findByName(logGroupName).get();
			
			logGroupList.add(logGroup);
		}
		
		return logGroupList;
	}
	
	private List<Keyword> getKeywordList(String[] keywordNameList) {
		List<Keyword> keywordList = new ArrayList<Keyword>(keywordNameList.length);
		
		for (String keywordName : keywordNameList) {
			this.keywordRepo.insertIgnore(keywordName);
			Keyword keyword = this.keywordRepo.findByWord(keywordName).get();
			
			keywordList.add(keyword);
		}
		
		return keywordList;
	}
	
	private List<SNSTopic> getSNSTopicList(String[] snsTopicNameList) {
		List<SNSTopic> snsTopicList = new ArrayList<SNSTopic>(snsTopicNameList.length);
		AmazonSNS snsClient = AmazonSNSClientBuilder
				.standard()
				.withCredentials(GlobalVariables.AWS_CREDENTIALS)
				.withRegion(GlobalVariables.AWS_REGION)
				.build();
		
		for (String snsTopicName : snsTopicNameList) {
			CreateTopicRequest request = new CreateTopicRequest(snsTopicName);
			CreateTopicResult result = snsClient.createTopic(request);
			
			this.snsTopicRepo.insertIgnore(snsTopicName, result.getTopicArn());
			SNSTopic snsTopic = this.snsTopicRepo.findByTopicNameAndTopicArn(snsTopicName, result.getTopicArn()).get();
			
			snsTopicList.add(snsTopic);
		}
		
		return snsTopicList;
	}
	
	@PostMapping(path="/subscribeToLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	public Map<String, String> subscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			Long logAlarmId = Long.parseLong(body.get("LogAlarmId"));
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			
			user.getLogAlarmList().add(logAlarm);
			logAlarm.getUserList().add(user);
			
			this.userRepo.save(user);
			this.logAlarmRepo.save(logAlarm);
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	@PostMapping(path="/unsubcribeToLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	public Map<String, String> unsubscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			Long logAlarmId = Long.parseLong(body.get("LogAlarmId"));
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			
			user.getLogAlarmList().remove(logAlarm);
			logAlarm.getUserList().remove(user);
			
			this.userRepo.save(user);
			this.logAlarmRepo.save(logAlarm);
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	@PostMapping(path="/deleteLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	public Map<String, String> deleteLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			Long logAlarmId = Long.parseLong(body.get("LogAlarmId"));
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			
			user.getLogAlarmList().remove(logAlarm);
			logAlarm.getUserList().remove(user);
			
			if (logAlarm.getUserList().isEmpty()) {
				this.logAlarmRepo.delete(logAlarm);
			}
			
			response.put("Result", "Success");
		}
		
		return response;
	}
}