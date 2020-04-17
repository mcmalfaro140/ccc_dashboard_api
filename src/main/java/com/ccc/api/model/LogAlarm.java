package com.ccc.api.model;

import java.io.Serializable;
import java.util.Set;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Basic;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="LogAlarms")
@Table(name="LogAlarms")
@DynamicInsert
@DynamicUpdate
public class LogAlarm implements Serializable {
	private static final long serialVersionUID = 4198681733980071621L;

	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="LogAlarmId", nullable=false, unique=true)
	private Long logAlarmId;
	
	@Size(max=255)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="AlarmName", nullable=false, unique=true)
	private String alarmName;
	
	@Pattern(regexp="ANY|ALL")
	@Basic(fetch=FetchType.LAZY)
	@Column(name="KeywordRelationship")
	private String keywordRelationship;
	
	@Pattern(regexp="TRACE|DEBUG|INFO|WARN|ERROR")
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="LogLevel", nullable=false)
	private String logLevel;
	
	@Pattern(regexp="==|<|>|<=|>=")
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="Comparison", nullable=false)
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
	private Set<LogGroup> logGroupSet;
	
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
	private Set<Keyword> keywordSet;
	
	@ManyToMany(mappedBy="logAlarmSet", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<User> userSet;
	
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
	private Set<SNSTopic> snsTopicSet;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="LogAlarmId", referencedColumnName="LogAlarmId")
	private Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet;
	
	public LogAlarm() {
	}
	
	public LogAlarm(
			String alarmName,
			Optional<String> keywordRelationship,
			String logLevel,
			String comparison,
			Set<LogGroup> logGroupSet,
			Set<Keyword> keywordSet,
			Set<User> userSet,
			Set<SNSTopic> snsTopicSet,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.alarmName = alarmName;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logGroupSet = logGroupSet;
		this.keywordSet = keywordSet;
		this.userSet = userSet;
		this.snsTopicSet = snsTopicSet;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
		
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
			Optional<String> keywordRelationship,
			String logLevel,
			String comparison,
			Set<LogGroup> logGroupSet,
			Set<Keyword> keywordSet,
			Set<User> userSet,
			Set<SNSTopic> snsTopicSet,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.logAlarmId = logAlarmId;
		this.alarmName = alarmName;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logGroupSet = logGroupSet;
		this.keywordSet = keywordSet;
		this.userSet = userSet;
		this.snsTopicSet = snsTopicSet;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
		
		if (keywordRelationship.isPresent()) {
			this.keywordRelationship = keywordRelationship.get();
		}
		else {
			this.keywordRelationship = null;
		}
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
	
	public Set<LogGroup> getLogGroupSet() {
		return this.logGroupSet;
	}
	
	public void setLogGroupSet(Set<LogGroup> logGroupSet) {
		this.logGroupSet.clear();
		this.logGroupSet.addAll(logGroupSet);
	}
	
	public Set<Keyword> getKeywordSet() {
		return this.keywordSet;
	}
	
	public void setKeywordSet(Set<Keyword> keywordSet) {
		this.keywordSet.clear();
		this.keywordSet.addAll(keywordSet);
	}
	
	public Set<User> getUserSet() {
		return this.userSet;
	}
	
	public void setUserSet(Set<User> userSet) {
		this.userSet.clear();
		this.userSet.addAll(userSet);
	}
	
	public Set<SNSTopic> getSNSTopicSet() {
		return this.snsTopicSet;
	}
	
	public void setSNTopicSet(Set<SNSTopic> snsTopicSet) {
		this.snsTopicSet.clear();
		this.snsTopicSet.addAll(snsTopicSet);
	}
	
	public Set<XRefLogAlarmSNSTopic> getXRefLogAlarmSNSTopicSet() {
		return this.xrefLogAlarmSNSTopicSet;
	}
	
	public void setXRefLogAlarmSNSTopicSet(Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet) {
		this.xrefLogAlarmSNSTopicSet.clear();
		this.xrefLogAlarmSNSTopicSet.addAll(xrefLogAlarmSNSTopicSet);
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
		String[] usernames = new String[this.userSet.size()];
		
		int index = 0;
		for (User user : this.userSet) {
			usernames[index] = user.getUsername();
		}
		
		return '[' + String.join(",", usernames) + ']';
	}
	
	private String getLogGroupNames() {
		String[] logGroupNames = new String[this.logGroupSet.size()];
		
		int index = 0;
		for (LogGroup logGroup : this.logGroupSet) {
			logGroupNames[index] = logGroup.getName();
			++index;
		}
		
		return '[' + String.join(",", logGroupNames) + ']';
	}
	
	private String getKeywordNames() {
		String[] keywordNames = new String[this.keywordSet.size()];
		
		int index = 0;
		for (Keyword keyword : this.keywordSet) {
			keywordNames[index] = keyword.getWord().toString();
			++index;
		}
		
		return '[' + String.join(",", keywordNames) + ']';
	}
	
	private String getSNSTopicNames() {
		String[] snsTopicNames = new String[this.snsTopicSet.size()];
		
		int index = 0;
		for (SNSTopic snsTopic : this.snsTopicSet) {
			snsTopicNames[index] = snsTopic.getTopicName();
			++index;
		}
		
		return '[' + String.join(",", snsTopicNames) + ']';
	}
	
	private String getXRefLogAlarmSNSTopicNames() {
		String[] xrefLogAlarmSNSTopicNames = new String[this.xrefLogAlarmSNSTopicSet.size()];
		
		int index = 0;
		for (XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic : this.xrefLogAlarmSNSTopicSet) {
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
