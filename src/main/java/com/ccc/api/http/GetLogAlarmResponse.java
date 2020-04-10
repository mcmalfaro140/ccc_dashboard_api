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
	
	public GetLogAlarmResponse(List<XRefUserLogAlarmSNSTopic> logAlarmsForAll, List<XRefUserLogAlarmSNSTopic> logAlarmsForUser) {
		this.all = new ArrayList<Map<String, Object>>();
		this.add(this.all, logAlarmsForAll);
		
		this.user = new ArrayList<Map<String, Object>>();
		this.add(this.user, logAlarmsForUser);
	}
	
	private void add(List<Map<String, Object>> logAlarmsMapList, List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {				
		for (XRefUserLogAlarmSNSTopic xrefUserLogAlarmSNSTopic : xrefUserLogAlarmSNSTopicList) {
			LogAlarm alarm = xrefUserLogAlarmSNSTopic.getLogAlarm();
			
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
			entry.put("XRefUserSNSTopic", new AllData(xrefUserLogAlarmSNSTopic.getUser().getUsername(), xrefUserLogAlarmSNSTopic.getSNSTopic().getTopicName()));
			
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
	
	public static class AllData {
		@JsonProperty(value="Username")
		private String username;
		
		@JsonProperty(value="SNSTopicName")
		private String snsTopicName;
		
		public AllData(String username, String snsTopicName) {
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
