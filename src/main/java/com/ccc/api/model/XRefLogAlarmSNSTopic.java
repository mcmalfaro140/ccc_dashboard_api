package com.ccc.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XRefLogAlarmSNSTopic")
public class XRefLogAlarmSNSTopic implements Serializable {
	private static final long serialVersionUID = 775372894103276546L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="logAlarmSNSTopicId")
	private Integer logAlarmSNSTopicId;
	
	@ManyToOne
	private LogAlarm logAlarm;
	
	@ManyToOne
	private SNSTopic snsTopic;
	
	public XRefLogAlarmSNSTopic() {
	}
	
	public XRefLogAlarmSNSTopic(LogAlarm logAlarm, SNSTopic snsTopic) {
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
	}
	
	public XRefLogAlarmSNSTopic(Integer logAlarmSNSTopicId, LogAlarm logAlarm, SNSTopic snsTopic) {
		this.logAlarmSNSTopicId = logAlarmSNSTopicId;
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
	}
	
	public Integer getLogAlarmSNSTopicId() {
		return this.logAlarmSNSTopicId;
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
}
