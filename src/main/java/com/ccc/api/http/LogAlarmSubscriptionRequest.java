package com.ccc.api.http;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogAlarmSubscriptionRequest {
	@JsonProperty(value="LogAlarmId")
	private Long logAlarmId;
	
	@JsonProperty(value="SNSTopicName")
	private String snsTopicName;
	
	public LogAlarmSubscriptionRequest() {
	}
	
	public LogAlarmSubscriptionRequest(Long logAlarmId, String snsTopicName) {
		this.logAlarmId = logAlarmId;
		this.snsTopicName = snsTopicName;
	}
	
	public Long getLogAlarmId() {
		return this.logAlarmId;
	}
	
	public String getSNSTopicName() {
		return this.snsTopicName;
	}
}
