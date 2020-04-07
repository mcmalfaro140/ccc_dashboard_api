package com.ccc.api.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.api.model.Keyword;
import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.LogGroup;
import com.ccc.api.model.SNSTopic;
import com.ccc.api.model.User;

public class GetLogAlarmResponse {
	private List<Map<String, Object>> allLogAlarms;
	private List<Map<String, Object>> userLogAlarms;
	
	
	public GetLogAlarmResponse(List<LogAlarm> logAlarmsForAll, List<LogAlarm> logAlarmsForUser) {
		this.allLogAlarms = new ArrayList<Map<String, Object>>();
		this.addToList(this.allLogAlarms, logAlarmsForAll);
		
		this.userLogAlarms = new ArrayList<Map<String, Object>>();
		this.addToList(this.userLogAlarms, logAlarmsForUser);
	}
	
	private void addToList(List<Map<String, Object>> logAlarmsMapList, List<LogAlarm> logAlarmsList) {		
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
			
			logAlarmsMapList.add(entry);
		}
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
	
	public List<Map<String, Object>> getAllLogAlarms() {
		return this.allLogAlarms;
	}
	
	public List<Map<String, Object>> getUserLogAlarms() {
		return this.userLogAlarms;
	}
}
