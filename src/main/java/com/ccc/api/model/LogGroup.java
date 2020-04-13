package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="LogGroups")
@Table(name="LogGroups")
@DynamicInsert
@DynamicUpdate
public class LogGroup implements Serializable {
	private static final long serialVersionUID = 6840950844769274284L;

	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="LogGroupId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long logGroupId;
	
	@Size(max=255)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="Name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(mappedBy="logGroupList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OrderBy
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
	
	public void setLogGroupId(Long logGroupId) {
		this.logGroupId = logGroupId;
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
		this.logAlarmList.clear();
		this.logAlarmList.addAll(logAlarmList);
	}
	
	@Override
	public int hashCode() {		
		return Math.abs(31 * (Objects.hashCode(this.logGroupId) + Objects.hashCode(this.name)));
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof LogGroup) ? this.logGroupId == ((LogGroup)obj).logGroupId : false;
	}
	
	@Override
	public String toString() {
		String logAlarmNames = this.getLogAlarmNames();
		
		return String.format(
			"LogGroup{LogGroupId=%d, Name=%s, LogAlarm=%s}",
			this.logGroupId, this.name, logAlarmNames
		);
	}
	
	private String getLogAlarmNames() {
		String[] logAlarmNames = new String[this.logAlarmList.size()];
		
		for (int index = 0; index < logAlarmNames.length; ++index) {
			logAlarmNames[index] = this.logAlarmList.get(index).getAlarmName();
		}
		
		return '[' + String.join(",", logAlarmNames) + ']';
	}
}
