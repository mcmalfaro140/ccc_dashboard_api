package com.ccc.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LogLevelCriteria")
public class LogLevelCriteria implements Serializable {
	private static final long serialVersionUID = 3401552680372591857L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LogLevelCriteriaId")
	private int logLevelCriteriaId;
	
	@Column(name="LogLevel", nullable=false)
	private String logLevel;
	
	@Column(name="Comparison", nullable=false)
	private String comparison;
	
	public LogLevelCriteria() {
	}
	
	public LogLevelCriteria(String logLevel, String comparison) {
		this.logLevel = logLevel;
		this.comparison = comparison;
	}
	
	public LogLevelCriteria(int logLevelCriteriaId, String logLevel, String comparison) {
		this.logLevelCriteriaId = logLevelCriteriaId;
		this.logLevel = logLevel;
		this.comparison = comparison;
	}
	
	public int getLogLevelCriteriaId() {
		return this.logLevelCriteriaId;
	}
	
	public String getLogLevel() {
		return this.logLevel;
	}
	
	public String getComparison() {
		return this.comparison;
	}
}
