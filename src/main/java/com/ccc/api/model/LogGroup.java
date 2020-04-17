package com.ccc.api.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(mappedBy="logGroupSet", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<LogAlarm> logAlarmSet;
	
	public LogGroup() {
	}
	
	public LogGroup(String name, Set<LogAlarm> logAlarmSet) {
		this.name = name;
		this.logAlarmSet = logAlarmSet;
	}
	
	public LogGroup(Long logGroupId, String name, Set<LogAlarm> logAlarmSet) {
		this.logGroupId = logGroupId;
		this.name = name;
		this.logAlarmSet = logAlarmSet;
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
	
	public Set<LogAlarm> getLogAlarmSet() {
		return this.logAlarmSet;
	}
	
	public void setLogAlarmSet(Set<LogAlarm> logAlarmSet) {
		this.logAlarmSet.clear();
		this.logAlarmSet.addAll(logAlarmSet);
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
		String[] logAlarmNames = new String[this.logAlarmSet.size()];
		
		int index = 0;
		for (LogAlarm logAlarm : this.logAlarmSet) {
			logAlarmNames[index] = logAlarm.getAlarmName();
			++index;
		}
		
		return '[' + String.join(",", logAlarmNames) + ']';
	}
}
