package com.ccc.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetLogAlarmResponse {
	private Map<String, List<Map<String, Object>>> allLogAlarms;
	private Map<String, List<Map<String, Object>>> userLogAlarms;
	
	
	public GetLogAlarmResponse(List<LogAlarm> logAlarmsForAll, List<LogAlarm> logAlarmsForUser) {
		this.allLogAlarms = new HashMap<String, List<Map<String, Object>>>();
		this.addToMap("all", this.allLogAlarms, logAlarmsForAll);
		
		this.userLogAlarms = new HashMap<String, List<Map<String, Object>>>();
		this.addToMap("user", this.userLogAlarms, logAlarmsForUser);
	}
	
	private void addToMap(String name, Map<String, List<Map<String, Object>>> logAlarmsMap, List<LogAlarm> logAlarmsList) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(logAlarmsList.size());
		
		for (LogAlarm alarm : logAlarmsList) {
			List<String> usernames = this.getUsernames(alarm.getUserList());
			List<String> logGroupNames = this.getLogGroupNames(alarm.getLogGroupList());
			List<String> keywordNames = this.getKeywords(alarm.getKeywordList());
			List<String> snsTopicNames = this.getSNSTopicNames(alarm.getSNSTopicList());
			
			Map<String, Object> entry = new HashMap<String, Object>();
			entry.put("LogAlarmId", alarm.getLogAlarmId());
			entry.put("AlarmName", alarm.getAlarmName());
			entry.put("LogLevel", alarm.getLogLevel());
			entry.put("KeywordRelationship", alarm.getKeywordRelationship());
			entry.put("Users", usernames);
			entry.put("LogGroups", logGroupNames);
			entry.put("Keywords", keywordNames);
			entry.put("SNSTopics", snsTopicNames);
			
			data.add(entry);
		}
		
		logAlarmsMap.put(name, data);
	}
	
	private List<String> getUsernames(List<User> userList) {
		List<String> usernameList = new ArrayList<String>(userList.size());
		
		for (User user : userList) {
			usernameList.add(user.getUsername());
		}
		
		return usernameList;
	}
	
	private List<String> getLogGroupNames(List<LogGroup> logGroupList) {
		List<String> logGroupNameList = new ArrayList<String>(logGroupList.size());
		
		for (LogGroup logGroup : logGroupList) {
			logGroupNameList.add(logGroup.getName());
		}
		
		return logGroupNameList;
	}
	
	private List<String> getKeywords(List<Keyword> keywordList) {
		List<String> keywordNameList = new ArrayList<String>(keywordList.size());
		
		for (Keyword keyword : keywordList) {
			keywordNameList.add(keyword.getWord());
		}
		
		return keywordNameList;
	}
	
	private List<String> getSNSTopicNames(List<SNSTopic> snsTopicList) {
		List<String> snsTopicNameList = new ArrayList<String>(snsTopicList.size());
		
		for (SNSTopic topic : snsTopicList) {
			snsTopicNameList.add(topic.getTopicName());
		}
		
		return snsTopicNameList;
	}
	
	public Map<String, List<Map<String, Object>>> getAllLogAlarms() {
		return this.allLogAlarms;
	}
	
	public Map<String, List<Map<String, Object>>> getUserLogAlarms() {
		return this.userLogAlarms;
	}
}
