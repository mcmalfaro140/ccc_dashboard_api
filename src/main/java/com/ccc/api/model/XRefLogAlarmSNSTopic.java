package com.ccc.api.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="XRefLogAlarmSNSTopic")
@Table(
	name="XRefLogAlarmSNSTopic",
	uniqueConstraints={
		@UniqueConstraint(columnNames={ "UserId", "LogAlarmId", "SNSTopicId" }) 
	}
)
@DynamicInsert
@DynamicUpdate
public class XRefLogAlarmSNSTopic implements Serializable {
	private static final long serialVersionUID = 4385868977900164012L;
	
	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
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
	
	public XRefLogAlarmSNSTopic(LogAlarm logAlarm, SNSTopic snsTopic, User user) {
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		this.user = user;
	}
	
	public XRefLogAlarmSNSTopic(Long logAlarmSNSTopicId, LogAlarm logAlarm, SNSTopic snsTopic, User user) {
		this.logAlarmSNSTopicId = logAlarmSNSTopicId;
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		this.user = user;
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
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
