package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="LogAlarms")
public class LogAlarm implements Serializable {
	private static final long serialVersionUID = 4198681733980071621L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LogAlarmId")
	private Integer logAlarmId;
	
	@Column(name="AlarmName", nullable=false, unique=true)
	private String alarmName;
	
	@Column(name="KeywordRelationship")
	private String keywordRelationship;
	
	@ManyToOne
	private LogLevelCriteria logLevelCriteria;
	
	@ManyToMany
	private List<User> userList;
	
	@ManyToMany
	private List<LogGroup> logGroupList;
	
	@ManyToMany
	private List<Keyword> keywordList;
	
	@ManyToMany
	private List<SNSTopic> snsTopicList;
	
	public LogAlarm() {
	}
	
	public LogAlarm(
			String alarmName,
			String keywordRelationship,
			LogLevelCriteria logLevelCriteria,
			List<User> userList,
			List<LogGroup> logGroupList,
			List<Keyword> keywordList,
			List<SNSTopic> snsTopicList
	) {
		this.alarmName = alarmName;
		this.keywordRelationship = keywordRelationship;
		this.logLevelCriteria = logLevelCriteria;
		this.userList = userList;
		this.logGroupList = logGroupList;
		this.keywordList = keywordList;
		this.snsTopicList = snsTopicList;
	}
	
	public LogAlarm(
			Integer logAlarmId,
			String alarmName,
			String keywordRelationship,
			LogLevelCriteria logLevelCriteria,
			List<User> userList,
			List<LogGroup> logGroupList,
			List<Keyword> keywordList,
			List<SNSTopic> snsTopicList
	) {
		this.logAlarmId = logAlarmId;
		this.alarmName = alarmName;
		this.keywordRelationship = keywordRelationship;
		this.logLevelCriteria = logLevelCriteria;
		this.userList = userList;
		this.logGroupList = logGroupList;
		this.keywordList = keywordList;
		this.snsTopicList = snsTopicList;
	}
	
	public Integer getLogAlarmId() {
		return this.logAlarmId;
	}
	
	public String getAlarmName() {
		return this.alarmName;
	}
	
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	
	public String getKeywordRelationship() {
		return this.keywordRelationship;
	}
	
	public void setKeywordRelationship(String keywordRelationship) {
		this.keywordRelationship = keywordRelationship;
	}
	
	public LogLevelCriteria getLogLevelCriteria() {
		return this.logLevelCriteria;
	}
	
	public void setLogLevelCriteria(LogLevelCriteria logLevelCriteria) {
		this.logLevelCriteria = logLevelCriteria;
	}
	
	public List<User> getUserList() {
		return this.userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<LogGroup> getLogGroupList() {
		return this.logGroupList;
	}
	
	public void setLogGroupList(List<LogGroup> logGroupList) {
		this.logGroupList = logGroupList;
	}
	
	public List<Keyword> getKeywordList() {
		return this.keywordList;
	}
	
	public void setKeywordList(List<Keyword> keywordList) {
		this.keywordList = keywordList;
	}
	
	public List<SNSTopic> getSNSTopicList() {
		return this.snsTopicList;
	}
	
	public void setSNSTopicList(List<SNSTopic> snsTopicList) {
		this.snsTopicList = snsTopicList;
	}
}
