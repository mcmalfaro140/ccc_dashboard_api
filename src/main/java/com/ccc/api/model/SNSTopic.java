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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="SNSTopics")
@Table(name="SNSTopics")
public class SNSTopic implements Serializable {
	private static final long serialVersionUID = 3077399178896844362L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SNSTopicId")
	private Long snsTopicId;
	
	@Column(name="TopicName", nullable=false, unique=true)
	private String topicName;
	
	@Column(name="TopicArn", nullable=false, unique=true)
	private String topicArn;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefUserLogAlarmSNSTopic",
		joinColumns={
			@JoinColumn(
				name="SNSTopicId",
				referencedColumnName="SNSTopicId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="UserId",
				referencedColumnName="UserId",
				nullable=false
			)
		}
	)
	private List<User> userList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefUserLogAlarmSNSTopic",
		joinColumns={
			@JoinColumn(
				name="SNSTopicId",
				referencedColumnName="SNSTopicId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="LogAlarmId",
				referencedColumnName="LogAlarmId",
				nullable=false
			)
		}
	)
	private List<LogAlarm> logAlarmList;
	
	@OneToMany(mappedBy="snsTopic", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList;
	
	public SNSTopic() {
	}
	
	public SNSTopic(
			String topicName,
			String topicArn,
			List<User> userList,
			List<LogAlarm> logAlarmList,
			List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList
	) {
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.userList = userList;
		this.logAlarmList = logAlarmList;
		this.xrefUserLogAlarmSNSTopicList = xrefUserLogAlarmSNSTopicList;
	}
	
	public SNSTopic(
			Long snsTopicId,
			String topicName,
			String topicArn,
			List<User> userList,
			List<LogAlarm> logAlarmList,
			List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList
	) {
		this.snsTopicId = snsTopicId;
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.userList = userList;
		this.logAlarmList = logAlarmList;
		this.xrefUserLogAlarmSNSTopicList = xrefUserLogAlarmSNSTopicList;
	}
	
	public Long getSNSTopicId() {
		return this.snsTopicId;
	}
	
	public String getTopicName() {
		return this.topicName;
	}
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	public String getTopicArn() {
		return this.topicArn;
	}
	
	public void setTopicArn(String topicArn) {
		this.topicArn = topicArn;
	}
	
	public List<User> getUserList() {
		return this.userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
	
	public List<XRefUserLogAlarmSNSTopic> getXRefUserLogAlarmSNSTopicList() {
		return this.xrefUserLogAlarmSNSTopicList;
	}
	
	public void setXRefUserLogAlarmSNSTopicList(List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		this.xrefUserLogAlarmSNSTopicList = xrefUserLogAlarmSNSTopicList;
	}
	
	@Override
	public int hashCode() {
		int modifier = 31;
		
		return Math.abs(
				modifier * this.snsTopicId.hashCode() +
				modifier * this.topicName.hashCode() +
				modifier * this.topicArn.hashCode()
		);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof SNSTopic) ? this.snsTopicId == ((SNSTopic)obj).snsTopicId : false;
	}
}
