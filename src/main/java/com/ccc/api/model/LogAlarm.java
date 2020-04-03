package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="LogAlarms")
@Table(name="LogAlarms")
public class LogAlarm implements Serializable {
	private static final long serialVersionUID = 4198681733980071621L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LogAlarmId")
	private Long logAlarmId;
	
	@Column(name="AlarmName", nullable=false, unique=true)
	private String alarmName;
	
	@Column(name="KeywordRelationship")
	private String keywordRelationship;
	
	@Column(name="LogLevel")
	private String logLevel;
	
	@Column(name="Comparison")
	private String comparison;
	
	@ManyToMany(mappedBy="logAlarmList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<User> userList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefLogAlarmLogGroup",
		joinColumns={
			@JoinColumn(
				name="LogAlarmId",
				referencedColumnName="LogAlarmId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="LogGroupId",
				referencedColumnName="LogGroupId",
				nullable=false
			)
		}
	)
	private List<LogGroup> logGroupList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefLogAlarmKeyword",
		joinColumns={
			@JoinColumn(
				name="LogAlarmId",
				referencedColumnName="LogAlarmId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="KeywordId",
				referencedColumnName="KeywordId",
				nullable=false
			)
		}
	)
	private List<Keyword> keywordList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefLogAlarmSNSTopic",
		joinColumns={
			@JoinColumn(
				name="LogAlarmId",
				referencedColumnName="LogAlarmId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="SNSTopicId",
				referencedColumnName="SNSTopicId",
				nullable=false
			)
		}
	)
	private List<SNSTopic> snsTopicList;
	
	public LogAlarm() {
	}
	
	public LogAlarm(
			String alarmName,
			String keywordRelationship,
			String logLevel,
			String comparison,
			List<User> userList,
			List<LogGroup> logGroupList,
			List<Keyword> keywordList,
			List<SNSTopic> snsTopicList
	) {
		this.alarmName = alarmName;
		this.keywordRelationship = keywordRelationship;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.userList = userList;
		this.logGroupList = logGroupList;
		this.keywordList = keywordList;
		this.snsTopicList = snsTopicList;
	}
	
	public LogAlarm(
			Long logAlarmId,
			String alarmName,
			String keywordRelationship,
			String logLevel,
			String comparison,
			List<User> userList,
			List<LogGroup> logGroupList,
			List<Keyword> keywordList,
			List<SNSTopic> snsTopicList
	) {
		this.logAlarmId = logAlarmId;
		this.alarmName = alarmName;
		this.keywordRelationship = keywordRelationship;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.userList = userList;
		this.logGroupList = logGroupList;
		this.keywordList = keywordList;
		this.snsTopicList = snsTopicList;
	}
	
	public Long getLogAlarmId() {
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
	
	public String getLogLevel() {
		return this.logLevel;
	}
	
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	public String getComparison() {
		return this.comparison;
	}
	
	public void setComparison(String comparison) {
		this.comparison = comparison;
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
