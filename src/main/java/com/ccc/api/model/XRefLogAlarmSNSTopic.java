package com.ccc.api.model;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="XRefLogAlarmSNSTopic")
@Table(
	name="XRefLogAlarmSNSTopic",
	uniqueConstraints={
		@UniqueConstraint(columnNames={ "UserId", "LogAlarmId", "SNSTopicId" }) 
	}
)
public class XRefLogAlarmSNSTopic implements Serializable {
	private static final long serialVersionUID = 4385868977900164012L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LogAlarmSNSTopicId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long logAlarmSNSTopicId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="LogAlarmId", referencedColumnName="LogAlarmId")
	private LogAlarm logAlarm;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="SNSTopicId", referencedColumnName="SNSTopicId")
	private SNSTopic snsTopic;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="UserId", referencedColumnName="UserId")
	private User user;
	
	public XRefLogAlarmSNSTopic() {
	}
	
	public XRefLogAlarmSNSTopic(LogAlarm logAlarm, SNSTopic snsTopic, Optional<User> user) {
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		
		if (user.isPresent()) {
			this.user = user.get();
		}
		else {
			this.user = null;
		}
	}
	
	public XRefLogAlarmSNSTopic(Long logAlarmSNSTopicId, LogAlarm logAlarm, SNSTopic snsTopic, Optional<User> user) {
		this.logAlarmSNSTopicId = logAlarmSNSTopicId;
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		
		if (user.isPresent()) {
			this.user = user.get();
		}
		else {
			this.user = null;
		}
	}
	
	public Long getLogAlarmSNSTopicId() {
		return this.logAlarmSNSTopicId;
	}
	
	public void setLogAlarmSNSTopicId(Long logAlarmSNSTopicId) {
		this.logAlarmSNSTopicId = logAlarmSNSTopicId;
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
	
	public Optional<User> getUser() {
		return Optional.ofNullable(this.user);
	}
	
	public void setUser(Optional<User> user) {
		if (user.isPresent()) {
			this.user = user.get();
		}
		else {
			this.user = null;
		}
	}
	
	@Override
	public int hashCode() {
		return 31 * Objects.hashCode(this.logAlarmSNSTopicId);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof XRefLogAlarmSNSTopic) ? this.logAlarmSNSTopicId == ((XRefLogAlarmSNSTopic)obj).logAlarmSNSTopicId : false;
	}
	
	@Override
	public String toString() {
		return String.format(
			"XRefLogAlarmSNSTopic{LogAlarmSNSTopicId=%d, LogAlarm=%s, SNSTopic=%s, User=%s}",
			this.logAlarmSNSTopicId, this.logAlarm.getAlarmName(), this.snsTopic.getTopicName(), this.user.getUsername()
		);
	}
}
