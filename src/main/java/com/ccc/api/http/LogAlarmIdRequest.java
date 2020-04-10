package com.ccc.api.http;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogAlarmIdRequest {
	@JsonProperty(value="LogAlarmId")
	private Long logAlarmId;
	
	public LogAlarmIdRequest() {
	}
	
	public LogAlarmIdRequest(Long logAlarmId) {
		this.logAlarmId = logAlarmId;
	}
	
	public Long getLogAlarmId() {
		return this.logAlarmId;
	}
}
