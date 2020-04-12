package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="LogAlarms")
@Table(name="LogAlarms")
public class LogAlarm implements Serializable {
	private static final long serialVersionUID = 4198681733980071621L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	@ManyToMany(mappedBy="logAlarmList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<User> userList;
	
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
	
	@OneToMany(mappedBy="logAlarm", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList;
	
	public LogAlarm() {
	}
	
	public LogAlarm(
			String alarmName,
			Optional<String> keywordRelationship,
			String logLevel,
			String comparison,
			List<LogGroup> logGroupList,
			List<Keyword> keywordList,
			List<User> userList,
			List<SNSTopic> snsTopicList,
			List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList
	) {
		this.alarmName = alarmName;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logGroupList = logGroupList;
		this.keywordList = keywordList;
		this.userList = userList;
		this.snsTopicList = snsTopicList;
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
		
		if (keywordRelationship.isPresent()) {
			this.keywordRelationship = keywordRelationship.get();
		}
		else {
			this.keywordRelationship = null;
		}
	}
	
	public LogAlarm(
			Long logAlarmId,
			String alarmName,
			String keywordRelationship,
			String logLevel,
			String comparison,
			List<LogGroup> logGroupList,
			List<Keyword> keywordList,
			List<User> userList,
			List<SNSTopic> snsTopicList,
			List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList
	) {
		this.logAlarmId = logAlarmId;
		this.alarmName = alarmName;
		this.keywordRelationship = keywordRelationship;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logGroupList = logGroupList;
		this.keywordList = keywordList;
		this.userList = userList;
		this.snsTopicList = snsTopicList;
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
	}
	
	public Long getLogAlarmId() {
		return this.logAlarmId;
	}
	
	public void setLogAlarmId(Long logAlarmId) {
		this.logAlarmId = logAlarmId;
	}
	
	public String getAlarmName() {
		return this.alarmName;
	}
	
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	
	public Optional<String> getKeywordRelationship() {
		return Optional.ofNullable(this.keywordRelationship);
	}
	
	public void setKeywordRelationship(Optional<String> keywordRelationship) {
		if (keywordRelationship.isPresent()) {
			this.keywordRelationship = keywordRelationship.get();
		}
		else {
			this.keywordRelationship = null;
		}
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
	
	public List<User> getUserList() {
		return this.userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<SNSTopic> getSNSTopicList() {
		return this.snsTopicList;
	}
	
	public void setSNTopicList(List<SNSTopic> snsTopicList) {
		this.snsTopicList = snsTopicList;
	}
	
	public List<XRefLogAlarmSNSTopic> getXRefLogAlarmSNSTopicList() {
		return this.xrefLogAlarmSNSTopicList;
	}
	
	public void setXRefLogAlarmSNSTopicList(List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList) {
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
	}
	
	@Override
	public int hashCode() {		
		return Math.abs(
				31 * 
				(Objects.hashCode(this.logAlarmId) +
				Objects.hashCode(this.alarmName) +
				Objects.hashCode(this.keywordRelationship) +
				Objects.hashCode(this.logLevel) +
				Objects.hashCode(this.comparison))
		);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof LogAlarm) ? this.logAlarmId == ((LogAlarm)obj).logAlarmId : false;
	}
	
	@Override
	public String toString() {
		String usernames = this.getUsernames();
		String logGroupNames = this.getLogGroupNames();
		String keywordNames = this.getKeywordNames();
		String snsTopicNames = this.getSNSTopicNames();
		String xrefLogAlarmSNSTopicNames = this.getXRefLogAlarmSNSTopicNames();
		
		return String.format(
			"LogAlarm{LogAlarmId=%d, AlarmName=%s, KeywordRelationship=%s, LogLevel=%s, Comparison=%s," +
			"Users=%s, LogGroups=%s, Keywords=%s, SNSTopics=%s, XRefLogAlarmSNSTopics=%s}",
			this.logAlarmId, this.alarmName, this.keywordRelationship, this.logLevel, this.comparison,
			usernames, logGroupNames, keywordNames, snsTopicNames, xrefLogAlarmSNSTopicNames
		);
	}
	
	private String getUsernames() {
		String[] usernames = new String[this.userList.size()];
		
		for (int index = 0; index < usernames.length; ++index) {
			usernames[index] = this.userList.get(index).getUsername();
		}
		
		return '[' + String.join(",", usernames) + ']';
	}
	
	private String getLogGroupNames() {
		String[] logGroupNames = new String[this.logGroupList.size()];
		
		for (int index = 0; index < logGroupNames.length; ++index) {
			logGroupNames[index] = this.logGroupList.get(index).getName();
		}
		
		return '[' + String.join(",", logGroupNames) + ']';
	}
	
	private String getKeywordNames() {
		String[] keywordNames = new String[this.keywordList.size()];
		
		for (int index = 0; index < keywordNames.length; ++index) {
			keywordNames[index] = this.keywordList.get(index).getWord().toString();
		}
		
		return '[' + String.join(",", keywordNames) + ']';
	}
	
	private String getSNSTopicNames() {
		String[] snsTopicNames = new String[this.snsTopicList.size()];
		
		for (int index = 0; index < snsTopicNames.length; ++index) {
			snsTopicNames[index] = this.snsTopicList.get(index).getTopicName();
		}
		
		return '[' + String.join(",", snsTopicNames) + ']';
	}
	
	private String getXRefLogAlarmSNSTopicNames() {
		String[] xrefLogAlarmSNSTopicNames = new String[this.xrefLogAlarmSNSTopicList.size()];
		
		for (int index = 0; index < xrefLogAlarmSNSTopicNames.length; ++index) {
			XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = this.xrefLogAlarmSNSTopicList.get(index);
			Optional<User> user = xrefLogAlarmSNSTopic.getUser();
			
			String username = (user.isPresent()) ? user.get().getUsername() : "null";
			String snsTopicName = xrefLogAlarmSNSTopic.getSNSTopic().getTopicName();
			
			xrefLogAlarmSNSTopicNames[index] = String.format(
					"(User=%s, SNSTopic=%s)",
					username, snsTopicName
			);
		}
		
		return '[' + String.join(",", xrefLogAlarmSNSTopicNames) + ']';
	}
}
