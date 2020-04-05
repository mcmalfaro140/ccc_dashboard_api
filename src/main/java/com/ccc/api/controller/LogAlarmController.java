  
package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(path="/getLogAlarms", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Object getLogAlarms(@RequestHeader(name="Authorization") String token) {

		List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
		User user = this.jwtUtils.toUser(token);
		if(user != null) {
			List<LogAlarm> userLogAlarm = this.userRepo.findById(user.getUserId()).get().getLogAlarmList();
			return ResponseEntity.ok(new GetLogAlarmResponse(allLogAlarms, userLogAlarm));
		}else {
			Map<String, String> response = new HashMap<String, String>();
			response.put("Error", "User Not Found.");
			return response;
		}
	}
	
	@PostMapping(path="/createLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody

	public Map<String, String> createLogAlarm(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Error", "User not found.");
		}
		else {
			String alarmName = body.get("AlarmName");
			String keywordRelationship = body.get("KeywordRelationship");
			String logLevel = body.get("LogLevel");
			String comparison = body.get("Comparison");
			String[] logGroupNames = body.get("LogGroups").split(",");
			
			String keywordNamesAsString = body.get("Keywords");
			String[] keywordNames = (null == keywordNamesAsString) ? null : keywordNamesAsString.split(",");
					
			String[] snsTopicNames = body.get("SNSTopicNames").split(",");
			List<User> userList = this.getUser(user);
			List<LogGroup> logGroupList = this.getLogGroupList(logGroupNames);
			List<Keyword> keywordList = this.getKeywordList(keywordNames);
			List<SNSTopic> snsTopicList = this.getSNSTopicList(snsTopicNames);
			
			LogAlarm logAlarm = new LogAlarm(alarmName, keywordRelationship, logLevel, comparison, userList, logGroupList, keywordList, snsTopicList);		
			this.logAlarmRepo.save(logAlarm);
			
			response.put("Success", "Alarm successfully created.");
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
			Optional<LogGroup> logGroup = this.logGroupRepo.findByName(logGroupName);
			
			if (logGroup.isPresent()) {
				logGroupList.add(logGroup.get());
			}
			else {
				LogGroup newLogGroup = new LogGroup(logGroupName, new ArrayList<LogAlarm>());
				
				this.logGroupRepo.save(newLogGroup);
				logGroupList.add(newLogGroup);
			}
		}
		
		return logGroupList;
	}
	
	private List<Keyword> getKeywordList(String[] keywordNameList) {
		List<Keyword> keywordList = new ArrayList<Keyword>(keywordNameList.length);
		
		if (keywordNameList != null) {
			for (String keywordName : keywordNameList) {			
				Optional<Keyword> keyword = this.keywordRepo.findByWord(keywordName);
				
				if (keyword.isPresent()) {
					keywordList.add(keyword.get());
				}
				else {
					Keyword newKeyword = new Keyword(keywordName, new ArrayList<LogAlarm>());
					
					this.keywordRepo.save(newKeyword);
					keywordList.add(newKeyword);
				}
			}
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
			Optional<SNSTopic> snsTopic = this.snsTopicRepo.findByTopicName(snsTopicName);
			
			if (snsTopic.isPresent()) {
				snsTopicList.add(snsTopic.get());
			}
			else {
				CreateTopicRequest request = new CreateTopicRequest(snsTopicName);
				CreateTopicResult result = snsClient.createTopic(request);
				SNSTopic newTopic = new SNSTopic(snsTopicName, result.getTopicArn(), new ArrayList<LogAlarm>());
				
				this.snsTopicRepo.save(newTopic);
				snsTopicList.add(newTopic);
			}
		}
		
		return snsTopicList;
	}
	
	@PostMapping(path="/subscribeToLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> subscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			User target = userRepo.findByUsername(user.getUsername());
			Long logAlarmId = Long.parseLong(body.get("LogAlarmId"));
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			target.getLogAlarmList().add(logAlarm);
			this.userRepo.save(target);
			response.put("Result", "Success");
		}
		return response;
	}
	
	@PostMapping(path="/unsubscribeToLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> unsubscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("E", "ERROR: User not found");
		}
		else {
			User target = userRepo.findByUsername(user.getUsername());
			Long logAlarmId = Long.parseLong(body.get("LogAlarmId"));
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			target.getLogAlarmList().remove(logAlarm);
			this.userRepo.save(target);
			response.put("Result", "Success");
		}
		return response;
	}
	
	@PostMapping(path="/deleteLogAlarm", produces="application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> deleteLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody Map<String, String> body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			Long logAlarmId = Long.parseLong(body.get("LogAlarmId"));
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			
			logAlarm.getUserList().remove(user);
			
			if (logAlarm.getUserList().isEmpty()) {
				this.logAlarmRepo.delete(logAlarm);
			}
			else {
				this.logAlarmRepo.save(logAlarm);
			}
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	@GetMapping(path="/test", produces="application/json; charset=UTF-8")
	public ResponseEntity<GetLogAlarmResponse> test() {
		List<LogAlarm> logAlarmList = this.logAlarmRepo.findAll();
		User user = this.userRepo.findById(1L).get();
		
		List<LogAlarm> userLogAlarmList = new ArrayList<LogAlarm>(logAlarmList.size());
		
		for (LogAlarm alarm : logAlarmList) {
			if (alarm.getUserList().contains(user)) {
				userLogAlarmList.add(alarm);
			}
		}
		
		return ResponseEntity.ok(new GetLogAlarmResponse(logAlarmList, userLogAlarmList));
	}
}