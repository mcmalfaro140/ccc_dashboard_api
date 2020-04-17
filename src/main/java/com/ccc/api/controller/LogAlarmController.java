package com.ccc.api.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
			Set<LogAlarm> userLogAlarms = user.getLogAlarmSet();
			
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
				response.put("Result", "ERROR: Cannot specify keywords without a keyword relationship and vice versa");
			}
			else {
				String alarmName = body.getAlarmName();
				String snsTopicName = body.getSNSTopicName();
				String logLevel = body.getLogLevel();
				String comparison = body.getComparison();
				String[] logGroupNames = body.getLogGroups().split(",");
				
				Set<LogGroup> logGroupSet = this.getLogGroupSet(logGroupNames);
				Set<Keyword> keywordSet = this.getKeywordSet(keywordNames);
				SNSTopic snsTopic = this.getSNSTopic(snsTopicName);
				XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = this.getXRefLogAlarmSNSTopic(user, snsTopic);
				
				LogAlarm logAlarm = new LogAlarm(
						alarmName,
						keywordRelationship,
						logLevel,
						comparison,
						new HashSet<LogGroup>(logGroupSet.size()),
						new HashSet<Keyword>(keywordSet.size()),
						new HashSet<User>(1),
						new HashSet<SNSTopic>(1),
						new HashSet<XRefLogAlarmSNSTopic>(1)
				);
				
				xrefLogAlarmSNSTopic.setLogAlarm(logAlarm);
				user.getLogAlarmSet().add(logAlarm);
				user.getXRefLogAlarmSNSTopicSet().add(xrefLogAlarmSNSTopic);
				snsTopic.getLogAlarmSet().add(logAlarm);
				snsTopic.getXRefLogAlarmSNSTopicSet().add(xrefLogAlarmSNSTopic);
				logAlarm.getUserSet().add(user);
				logAlarm.getLogGroupSet().addAll(logGroupSet);
				logAlarm.getKeywordSet().addAll(keywordSet);
				logAlarm.getSNSTopicSet().add(snsTopic);
				logAlarm.getXRefLogAlarmSNSTopicSet().add(xrefLogAlarmSNSTopic);
				
				this.logAlarmRepo.save(logAlarm);
				this.xrefLogAlarmSNSTopicRepo.deleteByMaxId();
				
				response.put("Result", "Success");
			}
		}
		
		return response;
	}
	
	private Set<LogGroup> getLogGroupSet(String[] logGroupNameSet) {
		HashSet<LogGroup> logGroupSet = new HashSet<LogGroup>(logGroupNameSet.length);
		
		for (String logGroupName : logGroupNameSet) {
			Optional<LogGroup> logGroup = this.logGroupRepo.findByName(logGroupName);
			logGroupSet.add(logGroup.isPresent() ? logGroup.get() : new LogGroup(logGroupName, new HashSet<LogAlarm>()));
		}
		
		return logGroupSet;
	}
	
	private Set<Keyword> getKeywordSet(Optional<String[]> nullableKeywordNameSet) {
		HashSet<Keyword> keywordSet = new HashSet<Keyword>();
		
		if (nullableKeywordNameSet.isPresent()) {
			String[] keywordNameSet = nullableKeywordNameSet.get();
			
			for (String keywordName : keywordNameSet) {			
				Optional<Keyword> keyword = this.keywordRepo.findByWord(keywordName);
				keywordSet.add(keyword.isPresent() ? keyword.get() : new Keyword(keywordName, new HashSet<LogAlarm>()));
			}
		}
		
		return keywordSet;
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
			SNSTopic newTopic = new SNSTopic(snsTopicName, result.getTopicArn(), new HashSet<LogAlarm>(), new HashSet<XRefLogAlarmSNSTopic>());
			
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
			SNSTopic snsTopic = this.getSNSTopic(snsTopicName);
			XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = new XRefLogAlarmSNSTopic(logAlarm, snsTopic, Optional.of(user));
			
			logAlarm.getUserSet().add(user);
			logAlarm.getSNSTopicSet().add(snsTopic);
			logAlarm.getXRefLogAlarmSNSTopicSet().add(xrefLogAlarmSNSTopic);
			user.getLogAlarmSet().add(logAlarm);
			
			this.logAlarmRepo.save(logAlarm);
			this.xrefLogAlarmSNSTopicRepo.deleteByMaxId();
			
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