package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="LogGroups")
public class LogGroup implements Serializable {
	private static final long serialVersionUID = 6840950844769274284L;

	@Id
	@GeneratedValue
	@Column(name="LogGroupId")
	private int logGroupId;
	
	@Column(name="Name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany
	private List<LogAlarm> logAlarmList;
	
	public LogGroup() {
	}
	
	public LogGroup(String name, List<LogAlarm> logAlarmList) {
		this.name = name;
		this.logAlarmList = logAlarmList;
	}
	
	public LogGroup(int logGroupId, String name, List<LogAlarm> logAlarmList) {
		this.logGroupId = logGroupId;
		this.name = name;
		this.logAlarmList = logAlarmList;
	}
	
	public int getLogGroupId() {
		return this.logGroupId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
}
