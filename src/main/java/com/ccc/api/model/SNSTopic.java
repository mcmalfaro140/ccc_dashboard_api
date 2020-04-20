package com.ccc.api.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="SNSTopics")
@Table(name="SNSTopics")
@DynamicInsert
@DynamicUpdate
public class SNSTopic implements Serializable {
	private static final long serialVersionUID = 3077399178896844362L;

	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="SNSTopicId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long snsTopicId;
	
	@Size(max=50)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="TopicName", nullable=false, unique=true)
	private String topicName;
	
	@Size(max=255)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="TopicArn", nullable=false, unique=true)
	private String topicArn;
	
	@OneToMany(mappedBy="snsTopic", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet;
	
	public SNSTopic() {
	}
	
	public SNSTopic(
			String topicName,
			String topicArn,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
	}
	
	public SNSTopic(
			Long snsTopicId,
			String topicName,
			String topicArn,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.snsTopicId = snsTopicId;
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
	}
	
	public Long getSNSTopicId() {
		return this.snsTopicId;
	}
	
	public void setSNSTopicId(Long snsTopicId) {
		this.snsTopicId = snsTopicId;
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
				(Objects.hashCode(this.snsTopicId) +
				Objects.hashCode(this.topicName) +
				Objects.hashCode(this.topicArn))
		);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof SNSTopic) ? this.snsTopicId == ((SNSTopic)obj).snsTopicId : false;
	}
	
	@Override
	public String toString() {
		String logAlarmNames = this.getLogAlarmNames();
		String xrefLogAlarmSNSTopicNames = this.getXRefLogAlarmSNSTopicNames();
		
		return String.format(
			"SNSTopic{SNSTopicId=%d, TopicName=%s, TopicArn=%s, LogAlarms=%s, XRefLogAlarmSNSTopics=%s}",
			this.snsTopicId, this.topicName, this.topicArn, logAlarmNames, xrefLogAlarmSNSTopicNames
		);
	}
	
	private String getLogAlarmNames() {
		String[] logAlarmNames = new String[this.xrefLogAlarmSNSTopicSet.size()];
		
		int index = 0;
		for (XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic : this.xrefLogAlarmSNSTopicSet) {
			logAlarmNames[index] = xrefLogAlarmSNSTopic.getLogAlarm().getAlarmName();
			++index;
		}
		
		return '[' + String.join(",", logAlarmNames) + ']';
	}
	
	private String getXRefLogAlarmSNSTopicNames() {
		String[] xrefLogAlarmSNSTopicNames = new String[this.xrefLogAlarmSNSTopicSet.size()];
		
		int index = 0;
		for (XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic : this.xrefLogAlarmSNSTopicSet) {
			User user = xrefLogAlarmSNSTopic.getUser();
			
			String username = user.getUsername();
			String logAlarmName = xrefLogAlarmSNSTopic.getLogAlarm().getAlarmName();
			
			xrefLogAlarmSNSTopicNames[index] = String.format(
					"(User=%s, LogAlarm=%s)",
					username, logAlarmName
			);
		}
		
		return '[' + String.join(",", xrefLogAlarmSNSTopicNames) + ']';
	}
}
