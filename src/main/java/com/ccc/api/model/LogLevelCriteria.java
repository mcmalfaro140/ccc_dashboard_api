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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="LogLevelCriteria")
@Table(name="LogLevelCriteria")
public class LogLevelCriteria implements Serializable {
	private static final long serialVersionUID = 3401552680372591857L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LogLevelCriteriaId")
	private Long logLevelCriteriaId;
	
	@Column(name="LogLevel", nullable=false)
	private String logLevel;
	
	@Column(name="Comparison", nullable=false)
	private String comparison;
	
	@OneToMany(
		mappedBy="logLevelCriteria",
		fetch=FetchType.LAZY,
		cascade=CascadeType.ALL,
		orphanRemoval=true
	)
	private List<LogAlarm> logAlarmList;
	
	public LogLevelCriteria() {
	}
	
	public LogLevelCriteria(String logLevel, String comparison, List<LogAlarm> logAlarmList) {
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logAlarmList = logAlarmList;
	}
	
	public LogLevelCriteria(Long logLevelCriteriaId, String logLevel, String comparison, List<LogAlarm> logAlarmList) {
		this.logLevelCriteriaId = logLevelCriteriaId;
		this.logLevel = logLevel;
		this.comparison = comparison;
		this.logAlarmList = logAlarmList;
	}
	
	public Long getLogLevelCriteriaId() {
		return this.logLevelCriteriaId;
	}
	
	public String getLogLevel() {
		return this.logLevel;
	}
	
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	public String getComparison() {
		return this.comparison;
	}
	
	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
}
