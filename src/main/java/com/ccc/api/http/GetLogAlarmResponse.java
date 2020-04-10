package com.ccc.api.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccc.api.model.Keyword;
import com.ccc.api.model.LogAlarm;
import com.ccc.api.model.LogGroup;
import com.ccc.api.model.XRefUserLogAlarmSNSTopic;
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
			List<String> logGroupNames = this.getLogGroupNames(alarm.getLogGroupList());
			List<String> keywordNames = this.getKeywords(alarm.getKeywordList());
			
			Map<String, Object> entry = new HashMap<String, Object>();
			entry.put("LogAlarmId", alarm.getLogAlarmId());
			entry.put("AlarmName", alarm.getAlarmName());
			entry.put("LogLevel", alarm.getLogLevel());
			entry.put("KeywordRelationship", alarm.getKeywordRelationship());
			entry.put("Comparison", alarm.getComparison());
			entry.put("LogGroups", logGroupNames);
			entry.put("Keywords", keywordNames);
			
			List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList = this.getXRefUserLogAlarmSNSTopic(alarm, alarm.getXRefUserLogAlarmSNSTopicList());
			List<String> userList = this.getUserList(xrefUserLogAlarmSNSTopicList);
			List<String> snsTopicList = this.getSNSTopicList(xrefUserLogAlarmSNSTopicList);
			List<Data> xrefUserSNSTopicList = this.getXRefUserSNSTopicList(xrefUserLogAlarmSNSTopicList);
			
			entry.put("Users", userList);
			entry.put("SNSTopics", snsTopicList);
			entry.put("XRefUserSNSTopic", xrefUserSNSTopicList);
			
			
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
		
		for (Keyword keyword : keywordList) {
			keywordNameList.add(keyword.getWord());
		}
		
		return keywordNameList;
	}
	
	private List<XRefUserLogAlarmSNSTopic> getXRefUserLogAlarmSNSTopic(LogAlarm alarm, List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		List<XRefUserLogAlarmSNSTopic> result = new ArrayList<XRefUserLogAlarmSNSTopic>(xrefUserLogAlarmSNSTopicList.size());
		
		for (XRefUserLogAlarmSNSTopic xrefUserLogAlarmSNSTopic :  xrefUserLogAlarmSNSTopicList) {
			if (xrefUserLogAlarmSNSTopic.getLogAlarm().equals(alarm)) {
				result.add(xrefUserLogAlarmSNSTopic);
			}
		}
		
		return result;
	}
	
	private List<String> getUserList(List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		List<String> userList = new ArrayList<String>(xrefUserLogAlarmSNSTopicList.size());
		
		for (XRefUserLogAlarmSNSTopic xrefUserLogAlarmSNSTopic : xrefUserLogAlarmSNSTopicList) {
			userList.add(xrefUserLogAlarmSNSTopic.getUser().getUsername());
		}
		
		return userList;
	}
	
	private List<String> getSNSTopicList(List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		List<String> snsTopicList = new ArrayList<String>(xrefUserLogAlarmSNSTopicList.size());
		
		for (XRefUserLogAlarmSNSTopic xrefUserLogAlarmSNSTopic : xrefUserLogAlarmSNSTopicList) {
			snsTopicList.add(xrefUserLogAlarmSNSTopic.getSNSTopic().getTopicName());
		}
		
		return snsTopicList;
	}
	
	private List<Data> getXRefUserSNSTopicList(List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		List<Data> xrefUserSNSTopicList = new ArrayList<Data>(xrefUserLogAlarmSNSTopicList.size());
		
		for (XRefUserLogAlarmSNSTopic xrefUserLogAlarmSNSTopic : xrefUserLogAlarmSNSTopicList) {
			xrefUserSNSTopicList.add(new Data(xrefUserLogAlarmSNSTopic.getUser().getUsername(), xrefUserLogAlarmSNSTopic.getSNSTopic().getTopicName()));
		}
		
		return xrefUserSNSTopicList;
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
