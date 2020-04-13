package com.ccc.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.ccc.api.http.CreateLogAlarmRequest;
import com.ccc.api.http.GetLogAlarmResponse;
import com.ccc.api.http.LogAlarmIdRequest;
import com.ccc.api.http.LogAlarmSubscriptionRequest;
import com.ccc.api.model.Keyword;
import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.LogGroup;
import com.ccc.api.model.SNSTopic;
import com.ccc.api.model.User;
import com.ccc.api.model.XRefLogAlarmSNSTopic;
import com.ccc.api.repository.KeywordRepository;
import com.ccc.api.repository.LogAlarmRepository;
import com.ccc.api.repository.LogGroupRepository;
import com.ccc.api.repository.SNSTopicRepository;
import com.ccc.api.repository.UserRepository;
import com.ccc.api.repository.XRefLogAlarmSNSTopicRepository;
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
	private XRefLogAlarmSNSTopicRepository xrefLogAlarmSNSTopicRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(path="/getLogAlarms", produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getLogAlarms(@RequestHeader(name="Authorization") String token) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User Not Found");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			
			List<LogAlarm> allLogAlarms = this.logAlarmRepo.findAll();
			List<LogAlarm> userLogAlarms = user.getLogAlarmList();
			
			response.put("Result", new GetLogAlarmResponse(allLogAlarms, userLogAlarms));
		}
		
		return response;
	}
	
	@PostMapping(path="/createLogAlarm", produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> createLogAlarm(@RequestHeader("Authorization") String token, @RequestBody CreateLogAlarmRequest body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "User not found");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			
			Optional<String> keywordRelationship = Optional.ofNullable(body.getKeywordRelationship());
			Optional<String> keywordNamesAsString = Optional.ofNullable(body.getKeywords());
			Optional<String[]> keywordNames = (keywordNamesAsString.isPresent()) ? Optional.of(keywordNamesAsString.get().split(",")): Optional.empty();
			
			if (keywordNames.isPresent() != keywordRelationship.isPresent()) {
				response.put("Result", "Cannot specify keywords without a keyword relationship and vice versa");
				return response;
			}
			
			String alarmName = body.getAlarmName();
			String snsTopicName = body.getSNSTopicName();
			String logLevel = body.getLogLevel();
			String comparison = body.getComparison();
			String[] logGroupNames = body.getLogGroups().split(",");
			
			List<LogGroup> logGroupList = this.getLogGroupList(logGroupNames);
			List<Keyword> keywordList = this.getKeywordList(keywordNames);
			SNSTopic snsTopic = this.getSNSTopic(snsTopicName);
			XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = this.getXRefLogAlarmSNSTopic(user, snsTopic);
			
			LogAlarm logAlarm = new LogAlarm(
					alarmName,
					keywordRelationship,
					logLevel,
					comparison,
					new ArrayList<LogGroup>(logGroupList.size()),
					new ArrayList<Keyword>(keywordList.size()),
					new ArrayList<User>(1),
					new ArrayList<SNSTopic>(1),
					new ArrayList<XRefLogAlarmSNSTopic>(1)
			);
			
			xrefLogAlarmSNSTopic.setLogAlarm(logAlarm);
			user.getLogAlarmList().add(logAlarm);
			user.getXRefLogAlarmSNSTopicList().add(xrefLogAlarmSNSTopic);
			snsTopic.getLogAlarmList().add(logAlarm);
			snsTopic.getXRefLogAlarmSNSTopicList().add(xrefLogAlarmSNSTopic);
			logAlarm.getUserList().add(user);
			logAlarm.getLogGroupList().addAll(logGroupList);
			logAlarm.getKeywordList().addAll(keywordList);
			logAlarm.getSNSTopicList().add(snsTopic);
			logAlarm.getXRefLogAlarmSNSTopicList().add(xrefLogAlarmSNSTopic);
			
			this.logAlarmRepo.save(logAlarm);
			this.xrefLogAlarmSNSTopicRepo.deleteByMaxId();
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	private List<LogGroup> getLogGroupList(String[] logGroupNameList) {
		ArrayList<LogGroup> logGroupList = new ArrayList<LogGroup>(logGroupNameList.length);
		
		for (String logGroupName : logGroupNameList) {
			Optional<LogGroup> logGroup = this.logGroupRepo.findByName(logGroupName);
			logGroupList.add(logGroup.isPresent() ? logGroup.get() : new LogGroup(logGroupName, new ArrayList<LogAlarm>()));
		}
		
		return logGroupList;
	}
	
	private List<Keyword> getKeywordList(Optional<String[]> nullableKeywordNameList) {
		ArrayList<Keyword> keywordList = new ArrayList<Keyword>();
		
		if (nullableKeywordNameList.isPresent()) {
			String[] keywordNameList = nullableKeywordNameList.get();
			
			for (String keywordName : keywordNameList) {			
				Optional<Keyword> keyword = this.keywordRepo.findByWord(keywordName);
				keywordList.add(keyword.isPresent() ? keyword.get() : new Keyword(keywordName, new ArrayList<LogAlarm>()));
			}
		}
		
		return keywordList;
	}
	
	private SNSTopic getSNSTopic(String snsTopicName) {
		AmazonSNS snsClient = AmazonSNSClientBuilder
				.standard()
				.withCredentials(GlobalVariables.AWS_CREDENTIALS)
				.withRegion(GlobalVariables.AWS_REGION)
				.build();
		
		Optional<SNSTopic> snsTopic = this.snsTopicRepo.findByTopicName(snsTopicName);
		
		if (snsTopic.isPresent()) {
			return snsTopic.get();
		}
		else {
			CreateTopicRequest request = new CreateTopicRequest(snsTopicName);
			CreateTopicResult result = snsClient.createTopic(request);
			SNSTopic newTopic = new SNSTopic(snsTopicName, result.getTopicArn(), new ArrayList<LogAlarm>(), new ArrayList<XRefLogAlarmSNSTopic>());
			
			return newTopic;
		}
	}
	
	private XRefLogAlarmSNSTopic getXRefLogAlarmSNSTopic(User user, SNSTopic snsTopic) {		
		XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = new XRefLogAlarmSNSTopic();
		xrefLogAlarmSNSTopic.setSNSTopic(snsTopic);
		xrefLogAlarmSNSTopic.setUser(Optional.of(user));
		
		return xrefLogAlarmSNSTopic;
	}
	
	@PostMapping(path="/subscribeToLogAlarm", produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> subscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody LogAlarmSubscriptionRequest body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			
			Long logAlarmId = body.getLogAlarmId();
			String snsTopicName = body.getSNSTopicName();
			
			LogAlarm logAlarm = this.logAlarmRepo.findById(logAlarmId).get();
			SNSTopic snsTopic = this.snsTopicRepo.findByTopicName(snsTopicName).get();
			XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = new XRefLogAlarmSNSTopic(logAlarm, snsTopic, Optional.of(user));
			
			this.xrefLogAlarmSNSTopicRepo.save(xrefLogAlarmSNSTopic);
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	@PostMapping(path="/unsubscribeToLogAlarm", produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> unsubscribeToLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody LogAlarmSubscriptionRequest body) {
		Map<String, String> response = new HashMap<String, String>();
		User user = this.jwtUtils.toUser(token);
		
		if (null == user) {
			response.put("Result", "ERROR: User not found");
		}
		else {
			user = this.userRepo.findById(user.getUserId()).get();
			
			Long logAlarmId = body.getLogAlarmId();
			String snsTopicName = body.getSNSTopicName();
			
			SNSTopic snsTopic = this.snsTopicRepo.findByTopicName(snsTopicName).get();
			
			this.xrefLogAlarmSNSTopicRepo.deleteByFields(user.getUserId(), logAlarmId, snsTopic.getSNSTopicId());
			
			response.put("Result", "Success");
		}
		
		return response;
	}
	
	@PostMapping(path="/deleteLogAlarm", produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> deleteLogAlarm(@RequestHeader(name="Authorization") String token, @RequestBody LogAlarmIdRequest body) {
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
				this.logAlarmRepo.deleteByLogAlarmId(body.getLogAlarmId());
				response.put("Result", "Success");
			}
		}
		
		return response;
	}
}