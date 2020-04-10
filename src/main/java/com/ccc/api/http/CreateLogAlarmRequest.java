package com.ccc.api.http;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateLogAlarmRequest {
	@JsonProperty(value="AlarmName")
	private String alarmName;
	
	@JsonProperty(value="KeywordRelationship")
	private String keywordRelationship;
	
	@JsonProperty(value="LogLevel")
	private String logLevel;
	
	@JsonProperty(value="Comparison")
	private String comparison;
	
	@JsonProperty(value="LogGroups")
	private String logGroups;
	
	@JsonProperty(value="Keywords")
	private String keywords;
	
	@JsonProperty(value="SNSTopicName")
	private String snsTopicName;
	
	public CreateLogAlarmRequest() {
	}
	
	public CreateLogAlarmRequest(
			String alarmName,
			String keywordRelationship,
			String logLevel,
			String comparison,
			String logGroups,
			String keywords,
			String snsTopicName
	) {
		this.alarmName = alarmName;
		this.keywordRelationship = keywordRelationship;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logGroups = logGroups;
		this.keywords = keywords;
		this.snsTopicName = snsTopicName;
	}
	
	public String getAlarmName() {
		return this.alarmName;
	}
	
	public String getKeywordRelationship() {
		return this.keywordRelationship;
	}
	
	public String getLogLevel() {
		return this.logLevel;
	}
	
	public String getComparison() {
		return this.comparison;
	}
	
	public String getLogGroups() {
		return this.logGroups;
	}
	
	public String getKeywords() {
		return this.keywords;
	}
	
	public String getSNSTopicName() {
		return this.snsTopicName;
	}
}
