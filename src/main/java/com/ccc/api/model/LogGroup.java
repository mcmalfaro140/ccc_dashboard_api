package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="LogGroups")
@Table(name="LogGroups")
public class LogGroup implements Serializable {
	private static final long serialVersionUID = 6840950844769274284L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LogGroupId")
	private Long logGroupId;
	
	@Column(name="Name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(mappedBy="logGroupList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<LogAlarm> logAlarmList;
	
	public LogGroup() {
	}
	
	public LogGroup(String name, List<LogAlarm> logAlarmList) {
		this.name = name;
		this.logAlarmList = logAlarmList;
	}
	
	public LogGroup(Long logGroupId, String name, List<LogAlarm> logAlarmList) {
		this.logGroupId = logGroupId;
		this.name = name;
		this.logAlarmList = logAlarmList;
	}
	
	public Long getLogGroupId() {
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
	
	@Override
	public int hashCode() {
		int modifier = 31;
		
		return Math.abs(
				modifier * this.logGroupId.hashCode() +
				modifier * this.name.hashCode()
		);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof LogGroup) ? this.logGroupId == ((LogGroup)obj).logGroupId : false;
	}
}
