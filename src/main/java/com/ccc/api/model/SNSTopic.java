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
	
	@OneToMany(mappedBy="snsTopic", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList;
	
	public SNSTopic() {
	}
	
	public SNSTopic(String topicName, String topicArn, List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.xrefUserLogAlarmSNSTopicList = xrefUserLogAlarmSNSTopicList;
	}
	
	public SNSTopic(Long snsTopicId, String topicName, String topicArn, List<XRefUserLogAlarmSNSTopic> xrefUserLogAlarmSNSTopicList) {
		this.snsTopicId = snsTopicId;
		this.topicName = topicName;
		this.topicArn = topicArn;
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
