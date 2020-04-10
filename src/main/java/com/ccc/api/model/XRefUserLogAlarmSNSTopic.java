package com.ccc.api.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="XRefUserLogAlarmSNSTopic")
@Table(name="XRefUserLogAlarmSNSTopic")
public class XRefUserLogAlarmSNSTopic implements Serializable {
	private static final long serialVersionUID = 6688047311719451698L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UserLogAlarmSNSTopicId")
	private Long userLogAlarmSNSTopicId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="LogAlarmId")
	private LogAlarm logAlarm;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="SNSTopicId")
	private SNSTopic snsTopic;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="UserId")
	private User user;
	
	public XRefUserLogAlarmSNSTopic() {
	}
	
	public XRefUserLogAlarmSNSTopic(LogAlarm logAlarm, SNSTopic snsTopic, User user) {
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		this.user = user;
	}
	
	public XRefUserLogAlarmSNSTopic(Long userLogAlarmSNSTopicId, LogAlarm logAlarm, SNSTopic snsTopic, User user) {
		this.userLogAlarmSNSTopicId = userLogAlarmSNSTopicId;
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		this.user = user;
	}
	
	public Long getUserLogAlarmSNSTopicId() {
		return this.userLogAlarmSNSTopicId;
	}
	
	public LogAlarm getLogAlarm() {
		return this.logAlarm;
	}
	
	public void setLogAlarm(LogAlarm logAlarm) {
		this.logAlarm = logAlarm;
	}
	
	public SNSTopic getSNSTopic() {
		return this.snsTopic;
	}
	
	public void setSNSTopic(SNSTopic snsTopic) {
		this.snsTopic = snsTopic;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		return 31 * this.userLogAlarmSNSTopicId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof XRefUserLogAlarmSNSTopic) ? this.userLogAlarmSNSTopicId == ((XRefUserLogAlarmSNSTopic)obj).userLogAlarmSNSTopicId : false;
	}
}
