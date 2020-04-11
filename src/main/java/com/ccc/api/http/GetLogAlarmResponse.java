package com.ccc.api.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.api.model.Assigner;
import com.ccc.api.model.Keyword;
import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.LogGroup;
import com.ccc.api.model.SNSTopic;
import com.ccc.api.model.User;
import com.ccc.api.model.XRefLogAlarmSNSTopic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetLogAlarmResponse {
	@JsonProperty(value="All")
	private List<Map<String, Object>> all;
	
	@JsonProperty(value="User")
	private List<Map<String, Object>> user;
	
	public GetLogAlarmResponse() {
	}
	
	public GetLogAlarmResponse(List<LogAlarm> logAlarmsForAll, List<LogAlarm> logAlarmsForUser) {
		this.all = new ArrayList<Map<String, Object>>();
		this.add(this.all, logAlarmsForAll);
		
		this.user = new ArrayList<Map<String, Object>>();
		this.add(this.user, logAlarmsForUser);
	}
	
	private void add(List<Map<String, Object>> logAlarmsMapList, List<LogAlarm> logAlarmList) {				
		for (LogAlarm alarm : logAlarmList) {			
			Map<String, Object> entry = new HashMap<String, Object>();
			entry.put("LogAlarmId", alarm.getLogAlarmId());
			entry.put("AlarmName", alarm.getAlarmName());
			entry.put("LogLevel", alarm.getLogLevel());
			entry.put("KeywordRelationship", alarm.getKeywordRelationship());
			entry.put("Comparison", alarm.getComparison());
			
			List<String> logGroupNames = this.getLogGroupNames(alarm.getLogGroupList());
			List<String> keywordNames = this.getKeywords(alarm.getKeywordList());
			List<String> usernames = this.getUsernames(alarm.getUserList());
			List<String> snsTopicNames = this.getSNSTopicNames(alarm.getSNSTopicList());
			List<Data> assigners = this.getAssigners(alarm.getXRefLogAlarmSNSTopicList());
			
			entry.put("LogGroups", logGroupNames);
			entry.put("Keywords", keywordNames);
			entry.put("Users", usernames);
			entry.put("SNSTopicNames", snsTopicNames);
			entry.put("XRefUserSNSTopic", assigners);
			
			logAlarmsMapList.add(entry);
		}
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
		
		if (keywordList.size() > 1 || (keywordList.size() == 1 && keywordList.get(0).getWord().isPresent())) {
			for (Keyword keyword : keywordList) {
				keywordNameList.add(keyword.getWord().get());
			}
		}
		
		return keywordNameList;
	}
	
	private List<String> getUsernames(List<User> userList) {
		ArrayList<String> usernames = new ArrayList<String>(userList.size());
		
		for (User user : userList) {
			usernames.add(user.getUsername());
		}
		
		return usernames;
	}
	
	private List<String> getSNSTopicNames(List<SNSTopic> snsTopicList) {
		ArrayList<String> snsTopicNames = new ArrayList<String>(snsTopicList.size());
		
		for (SNSTopic snsTopic : snsTopicList) {
			snsTopicNames.add(snsTopic.getTopicName());
		}
		
		return snsTopicNames;
	}
	
	private List<Data> getAssigners(List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList) {
		ArrayList<Data> xrefUserSNSTopic = new ArrayList<Data>(xrefLogAlarmSNSTopicList.size());
		
		for (XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic : xrefLogAlarmSNSTopicList) {
			Assigner assigner = xrefLogAlarmSNSTopic.getAssigner();
			
			xrefUserSNSTopic.add(new Data(assigner.getUser().getUsername(), xrefLogAlarmSNSTopic.getSNSTopic().getTopicName()));
		}
		
		return xrefUserSNSTopic;
	}
	
	public static class Data {
		@JsonProperty(value="Username")
		private String username;
		
		@JsonProperty(value="SNSTopicName")
		private String snsTopicName;
		
		public Data(String username, String snsTopicName) {
			this.username = username;
			this.snsTopicName = snsTopicName;
		}
		
		public String getUsername() {
			return this.username;
		}
		
		@JsonIgnore
		public String getSNSTopicName() {
			return this.snsTopicName;
		}
	}
}