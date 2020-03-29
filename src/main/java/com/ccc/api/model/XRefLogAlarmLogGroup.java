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
@Table(name="XRefLogAlarmLogGroup")
public class XRefLogAlarmLogGroup implements Serializable {
	private static final long serialVersionUID = 8545979389231621020L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="logAlarmLogGroupId")
	private Integer logAlarmLogGroupId;
	
	@ManyToOne
	private LogAlarm logAlarm;
	
	@ManyToOne
	private LogGroup logGroup;
	
	public XRefLogAlarmLogGroup() {
	}
	
	public XRefLogAlarmLogGroup(LogAlarm logAlarm, LogGroup logGroup) {
		this.logAlarm = logAlarm;
		this.logGroup = logGroup;
	}
	
	public XRefLogAlarmLogGroup(Integer logAlarmLogGroupId, LogAlarm logAlarm, LogGroup logGroup) {
		this.logAlarmLogGroupId = logAlarmLogGroupId;
		this.logAlarm = logAlarm;
		this.logGroup = logGroup;
	}
	
	public Integer getLogAlarmLogGroupId() {
		return this.logAlarmLogGroupId;
	}
	
	public LogAlarm getLogAlarm() {
		return this.logAlarm;
	}
	
	public void setLogAlarm(LogAlarm logAlarm) {
		this.logAlarm = logAlarm;
	}
	
	public LogGroup getLogGroup() {
		return this.logGroup;
	}
	
	public void setLogGroup(LogGroup logGroup) {
		this.logGroup = logGroup;
	}
}
