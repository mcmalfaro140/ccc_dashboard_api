package com.ccc.api.http;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

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
	
	public GetLogAlarmResponse(Collection<LogAlarm> logAlarmsForAll, Collection<LogAlarm> logAlarmsForUser) {
		this.all = new ArrayList<Map<String, Object>>(logAlarmsForAll.size());
		this.add(this.all, logAlarmsForAll);
		
		this.user = new ArrayList<Map<String, Object>>(logAlarmsForUser.size());
		this.add(this.user, logAlarmsForUser);
	}
	
	private void add(List<Map<String, Object>> logAlarmsMapSet, Collection<LogAlarm> logAlarmCollection) {				
		for (LogAlarm alarm : logAlarmCollection) {			
			Map<String, Object> entry = new HashMap<String, Object>();
			entry.put("LogAlarmId", alarm.getLogAlarmId());
			entry.put("AlarmName", alarm.getAlarmName());
			entry.put("LogLevel", alarm.getLogLevel());
			entry.put("KeywordRelationship", alarm.getKeywordRelationship());
			entry.put("Comparison", alarm.getComparison());
			
			Set<String> logGroupNames = this.getLogGroupNames(alarm.getLogGroupSet());
			Set<String> keywordNames = this.getKeywords(alarm.getKeywordSet());
			Set<String> usernames = this.getUsernames(alarm.getUserSet());
			Set<String> snsTopicNames = this.getSNSTopicNames(alarm.getSNSTopicSet());
			Set<Data> assigners = this.getAssigners(alarm.getXRefLogAlarmSNSTopicSet());
			
			entry.put("LogGroups", logGroupNames);
			entry.put("Keywords", keywordNames);
			entry.put("Users", usernames);
			entry.put("SNSTopicNames", snsTopicNames);
			entry.put("XRefUserSNSTopic", assigners);
			
			logAlarmsMapSet.add(entry);
		}
	}
	
	private Set<String> getLogGroupNames(Collection<LogGroup> logGroupSet) {
		Set<String> logGroupNameSet = new HashSet<String>(logGroupSet.size());
		
		for (LogGroup logGroup : logGroupSet) {
			logGroupNameSet.add(logGroup.getName());
		}
		
		return logGroupNameSet;
	}
	
	private Set<String> getKeywords(Collection<Keyword> keywordCollection) {
		Set<String> keywordNameSet = new HashSet<String>(keywordCollection.size());
		
		if (keywordCollection.size() > 1 || (keywordCollection.size() == 1 && keywordCollection.iterator().next().getWord().isPresent())) {
			for (Keyword keyword : keywordCollection) {
				keywordNameSet.add(keyword.getWord().get());
			}
		}
		
		return keywordNameSet;
	}
	
	private Set<String> getUsernames(Collection<User> userCollection) {
		HashSet<String> usernames = new HashSet<String>(userCollection.size());
		
		for (User user : userCollection) {
			usernames.add(user.getUsername());
		}
		
		return usernames;
	}
	
	private Set<String> getSNSTopicNames(Collection<SNSTopic> snsTopicCollection) {
		HashSet<String> snsTopicNames = new HashSet<String>(snsTopicCollection.size());
		
		for (SNSTopic snsTopic : snsTopicCollection) {
			snsTopicNames.add(snsTopic.getTopicName());
		}
		
		return snsTopicNames;
	}
	
	private Set<Data> getAssigners(Collection<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet) {
		HashSet<Data> xrefUserSNSTopic = new HashSet<Data>(xrefLogAlarmSNSTopicSet.size());
		
		for (XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic : xrefLogAlarmSNSTopicSet) {
			User user = xrefLogAlarmSNSTopic.getUser();
			xrefUserSNSTopic.add(new Data(user.getUsername(), xrefLogAlarmSNSTopic.getSNSTopic().getTopicName()));
		}
		
		return xrefUserSNSTopic;
	}
	
	public static class Data {
		@JsonProperty(value="Username")
		private String username;
		
		@JsonProperty(value="SNSTopicName")
		private String snsTopicName;
		
		public Data(String username, String snsTopicName) {
			this.snsTopicName = snsTopicName;
			this.username = username;
		}
		
		@JsonIgnore
		public String getUsername() {
			return this.username;
		}
		
		@JsonIgnore
		public String getSNSTopicName() {
			return this.snsTopicName;
		}
	}
}