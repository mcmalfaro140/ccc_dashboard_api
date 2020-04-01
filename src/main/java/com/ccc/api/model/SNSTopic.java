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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="SNSTopics")
@Table(name="SNSTopics")
public class SNSTopic implements Serializable {
	private static final long serialVersionUID = 3077399178896844362L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SNSTopicId")
	private Long snsTopicId;
	
	@Column(name="TopicName", nullable=false, unique=true)
	private String topicName;
	
	@Column(name="TopicArn", nullable=false, unique=true)
	private String topicArn;
	
	@ManyToMany(mappedBy="snsTopicList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<LogAlarm> logAlarmList;
	
	public SNSTopic() {
	}
	
	public SNSTopic(String topicName, String topicArn, List<LogAlarm> logAlarmList) {
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.logAlarmList = logAlarmList;
	}
	
	public SNSTopic(Long snsTopicId, String topicName, String topicArn, List<LogAlarm> logAlarmList) {
		this.snsTopicId = snsTopicId;
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.logAlarmList = logAlarmList;
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
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
}
