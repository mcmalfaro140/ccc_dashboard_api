package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="LogGroups")
public class LogGroup implements Serializable {
	private static final long serialVersionUID = 6840950844769274284L;

	@Id
	@GeneratedValue
	@Column(name="LogGroupId")
	private Integer logGroupId;
	
	@Column(name="Name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefLogAlarmLogGroup",
		joinColumns={
			@JoinColumn(
				name="LogGroupId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="LogAlarmId",
				nullable=false
			)
		}
	)
	private List<LogAlarm> logAlarmList;
	
	public LogGroup() {
	}
	
	public LogGroup(String name, List<LogAlarm> logAlarmList) {
		this.name = name;
		this.logAlarmList = logAlarmList;
	}
	
	public LogGroup(Integer logGroupId, String name, List<LogAlarm> logAlarmList) {
		this.logGroupId = logGroupId;
		this.name = name;
		this.logAlarmList = logAlarmList;
	}
	
	public Integer getLogGroupId() {
		return this.logGroupId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
}
