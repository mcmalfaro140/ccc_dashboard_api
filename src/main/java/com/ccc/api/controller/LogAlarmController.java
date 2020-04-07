  
package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.ccc.api.http.GetLogAlarmResponse;
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
	Logger logger = LoggerFactory.getLogger(LogAlarmController.class);
	
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
	
	public Map<String, Object> getLogAlarms(@RequestHeader(name="Authorization") String token) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "User Not Found.");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			List<LogAlarm> userLogAlarms = user.getLogAlarmList();
			
			response.put("Result", new GetLogAlarmResponse(allLogAlarms, userLogAlarms));
		}
		
		return response;
	}
	
	@PostMapping(path="/createLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> createLogAlarm(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "User not found.");
		}
		else {
			String alarmName = body.get("AlarmName");
			String keywordRelationship = body.get("KeywordRelationship");
			String logLevel = body.get("LogLevel");
			String comparison = body.get("Comparison");
			String[] logGroupNames = body.get("LogGroups").split(",");
			
			String keywordNamesAsString = body.get("Keywords");
			Optional<String[]> keywordNames = (null == keywordNamesAsString) ? Optional.empty() : Optional.of(keywordNamesAsString.split(","));
					
			String[] snsTopicNames = body.get("SNSTopicNames").split(",");
			List<User> userList = this.getUser(user);
			List<LogGroup> logGroupList = this.getLogGroupList(logGroupNames);
			List<Keyword> keywordList = this.getKeywordList(keywordNames);
			List<SNSTopic> snsTopicList = this.getSNSTopicList(snsTopicNames);
			
			
			LogAlarm logAlarm = new LogAlarm(alarmName, keywordRelationship, logLevel, comparison, userList, logGroupList, keywordList, snsTopicList);
			userList.get(0).getLogAlarmList().add(logAlarm);
			logGroupList.forEach(value -> { value.getLogAlarmList().add(logAlarm); });
			keywordList.forEach(value -> { value.getLogAlarmList().add(logAlarm); });
			snsTopicList.forEach(value -> { value.getLogAlarmList().add(logAlarm); });
			
			this.logAlarmRepo.save(logAlarm);
			
			response.put("Result", "Alarm successfully created.");
		}
		
		return response;
	}
	
	private List<User> getUser(User user) {
		user = this.userRepo.findById(user.getUserId()).get();
		
		ArrayList<User> userList = new ArrayList<User>(1);
		userList.add(user);
		
		return userList;
	}
	
	private List<LogGroup> getLogGroupList(String[] logGroupNameList) {
		ArrayList<LogGroup> logGroupList = new ArrayList<LogGroup>(logGroupNameList.length);
		ArrayList<LogGroup> newLogGroupsToSave = new ArrayList<LogGroup>(logGroupNameList.length);
		
		for (String logGroupName : logGroupNameList) {
			Optional<LogGroup> logGroup = this.logGroupRepo.findByName(logGroupName);
			
			if (logGroup.isPresent()) {
				logGroupList.add(logGroup.get());
			}
			else {
				LogGroup newLogGroup = new LogGroup(logGroupName, new ArrayList<LogAlarm>());
				
				newLogGroupsToSave.add(newLogGroup);
				logGroupList.add(newLogGroup);
			}
		}
		
		this.logGroupRepo.saveAll(newLogGroupsToSave);
		
		return logGroupList;
	}
	
	private List<Keyword> getKeywordList(Optional<String[]> keywordNameList) {
		ArrayList<Keyword> keywordList = new ArrayList<Keyword>();
		
		if (keywordNameList.isPresent()) {
			String[] nonullKeywordNameList = keywordNameList.get();
			List<Keyword> newKeywordsToSave = new ArrayList<Keyword>(nonullKeywordNameList.length);
			keywordList.ensureCapacity(nonullKeywordNameList.length);
			
			for (String keywordName : nonullKeywordNameList) {			
				Optional<Keyword> keyword = this.keywordRepo.findByWord(keywordName);
				
				if (keyword.isPresent()) {
					keywordList.add(keyword.get());
				}
				else {
					Keyword newKeyword = new Keyword(keywordName, new ArrayList<LogAlarm>());
					
					newKeywordsToSave.add(newKeyword);
					keywordList.add(newKeyword);
				}
			}
			
			this.keywordRepo.saveAll(newKeywordsToSave);
		}
		
		return keywordList;
	}
	
	private List<SNSTopic> getSNSTopicList(String[] snsTopicNameList) {
		ArrayList<SNSTopic> snsTopicList = new ArrayList<SNSTopic>(snsTopicNameList.length);
		ArrayList<SNSTopic> newSNSTopicsToSave = new ArrayList<SNSTopic>(snsTopicNameList.length);
		AmazonSNS snsClient = AmazonSNSClientBuilder
				.standard()
				.withCredentials(GlobalVariables.AWS_CREDENTIALS)
				.withRegion(GlobalVariables.AWS_REGION)
				.build();
		
		for (String snsTopicName : snsTopicNameList) {
			Optional<SNSTopic> snsTopic = this.snsTopicRepo.findByTopicName(snsTopicName);
			
			if (snsTopic.isPresent()) {
				snsTopicList.add(snsTopic.get());
			}
			else {
				CreateTopicRequest request = new CreateTopicRequest(snsTopicName);
				CreateTopicResult result = snsClient.createTopic(request);
				SNSTopic newTopic = new SNSTopic(snsTopicName, result.getTopicArn(), new ArrayList<LogAlarm>());
				
				newSNSTopicsToSave.add(newTopic);
				snsTopicList.add(newTopic);
			}
		}
		
		this.snsTopicRepo.saveAll(newSNSTopicsToSave);
		
		return snsTopicList;
	}
	
	@PostMapping(path="/subscribeToLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> subscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, Long> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			
			LogAlarm logAlarm = this.logAlarmRepo.findById(body.get("LogAlarmId")).get();
			user.getLogAlarmList().add(logAlarm);
			this.userRepo.save(user);
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	@PostMapping(path="/unsubscribeToLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> unsubscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, Long> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("E", "ERROR: User not found");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			Long logAlarmId = body.get("LogAlarmId");
			
			this.removeLogAlarm(user, logAlarmId);
			this.userRepo.save(user);
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	private void removeLogAlarm(User user, Long logAlarmId) {
		Iterator<LogAlarm> iter = user.getLogAlarmList().iterator();
		
		while (iter.hasNext()) {
			LogAlarm logAlarm = iter.next();
			
			if (logAlarm.getLogAlarmId().equals(logAlarmId)) {
				iter.remove();
				break;
			}
		}
	}
	
	@PostMapping(path="/deleteLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> deleteLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, Long> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: Invalid JWT token");
		}
		else {
			if (!this.userRepo.existsById(user.getUserId())) {
				response.put("Result", "ERROR: User does not exist");
			}
			else {
				this.logAlarmRepo.deleteByLogAlarmId(body.get("LogAlarmId"));
				response.put("Result", "Success");
			}
		}
		
		return response;
	}
}