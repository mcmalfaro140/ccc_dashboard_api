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
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(mappedBy="snsTopicSet", fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private Set<User> userSet;
	
	@ManyToMany(mappedBy="snsTopicSet", fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private Set<LogAlarm> logAlarmSet;
	
	@OneToMany(mappedBy="snsTopic", fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, orphanRemoval=true)
	private Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet;
	
	public SNSTopic() {
	}
	
	public SNSTopic(
			String topicName,
			String topicArn,
			Set<User> userSet,
			Set<LogAlarm> logAlarmSet,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.userSet = userSet;
		this.logAlarmSet = logAlarmSet;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
	}
	
	public SNSTopic(
			Long snsTopicId,
			String topicName,
			String topicArn,
			Set<User> userSet,
			Set<LogAlarm> logAlarmSet,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.snsTopicId = snsTopicId;
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.userSet = userSet;
		this.logAlarmSet = logAlarmSet;
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
	
	public Set<User> getUserSet() {
		return this.userSet;
	}
	
	public void setUserSet(Set<User> userSet) {
		this.userSet.clear();
		this.userSet.addAll(userSet);
	}
	
	public Set<LogAlarm> getLogAlarmSet() {
		return this.logAlarmSet;
	}
	
	public void setLogAlarmSet(Set<LogAlarm> logAlarmSet) {
		this.logAlarmSet.clear();
		this.logAlarmSet.addAll(logAlarmSet);
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
}
