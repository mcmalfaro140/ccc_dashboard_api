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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity(name="SNSTopics")
@Table(name="SNSTopics")
@DynamicUpdate
public class SNSTopic implements Serializable {
	private static final long serialVersionUID = 3077399178896844362L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SNSTopicId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long snsTopicId;
	
	@NotNull
	@Size(max=50)
	@Column(name="TopicName", nullable=false, unique=true)
	private String topicName;
	
	@NotNull
	@Size(max=255)
	@Column(name="TopicArn", nullable=false, unique=true)
	private String topicArn;
	
	@NotNull
	@ManyToMany(mappedBy="snsTopicList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<LogAlarm> logAlarmList;
	
	@NotNull
	@OneToMany(mappedBy="snsTopic", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList;
	
	public SNSTopic() {
	}
	
	public SNSTopic(
			String topicName,
			String topicArn,
			List<LogAlarm> logAlarmList,
			List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList
	) {
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.logAlarmList = logAlarmList;
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
	}
	
	public SNSTopic(
			Long snsTopicId,
			String topicName,
			String topicArn,
			List<LogAlarm> logAlarmList,
			List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList
	) {
		this.snsTopicId = snsTopicId;
		this.topicName = topicName;
		this.topicArn = topicArn;
		this.logAlarmList = logAlarmList;
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
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
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList.clear();
		this.logAlarmList.addAll(logAlarmList);
	}
	
	public List<XRefLogAlarmSNSTopic> getXRefLogAlarmSNSTopicList() {
		return this.xrefLogAlarmSNSTopicList;
	}
	
	public void setXRefLogAlarmSNSTopicList(List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList) {
		this.xrefLogAlarmSNSTopicList.clear();
		this.xrefLogAlarmSNSTopicList.addAll(xrefLogAlarmSNSTopicList);
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
		String[] logAlarmNames = new String[this.logAlarmList.size()];
		
		for (int index = 0; index < logAlarmNames.length; ++index) {
			logAlarmNames[index] = this.logAlarmList.get(index).getAlarmName();
		}
		
		return '[' + String.join(",", logAlarmNames) + ']';
	}
	
	private String getXRefLogAlarmSNSTopicNames() {
		String[] xrefLogAlarmSNSTopicNames = new String[this.xrefLogAlarmSNSTopicList.size()];
		
		for (int index = 0; index < xrefLogAlarmSNSTopicNames.length; ++index) {
			XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = this.xrefLogAlarmSNSTopicList.get(index);
			Optional<User> user = xrefLogAlarmSNSTopic.getUser();
			
			String username = (user.isPresent()) ? user.get().getUsername() : "null";
			String logAlarmName = xrefLogAlarmSNSTopic.getLogAlarm().getAlarmName();
			
			xrefLogAlarmSNSTopicNames[index] = String.format(
					"(User=%s, LogAlarm=%s)",
					username, logAlarmName
			);
		}
		
		return '[' + String.join(",", xrefLogAlarmSNSTopicNames) + ']';
	}
}
