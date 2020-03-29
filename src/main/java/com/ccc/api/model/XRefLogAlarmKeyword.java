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
@Table(name="XRefLogAlarmKeyword")
public class XRefLogAlarmKeyword implements Serializable {
	private static final long serialVersionUID = -1903506241543241301L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="logAlarmKeywordId")
	private Integer logAlarmKeywordId;
	
	@ManyToOne
	private LogAlarm logAlarm;
	
	@ManyToOne
	private Keyword keyword;
	
	public XRefLogAlarmKeyword() {
	}
	
	public XRefLogAlarmKeyword(LogAlarm logAlarm, Keyword keyword) {
		this.logAlarm = logAlarm;
		this.keyword = keyword;
	}
	
	public XRefLogAlarmKeyword(Integer logAlarmKeywordId, LogAlarm logAlarm, Keyword keyword) {
		this.logAlarmKeywordId = logAlarmKeywordId;
		this.logAlarm = logAlarm;
		this.keyword = keyword;
	}
	
	public Integer getLogAlarmKeywordId() {
		return this.logAlarmKeywordId;
	}
	
	public LogAlarm getLogAlarm() {
		return this.logAlarm;
	}
	
	public void setLogAlarm(LogAlarm logAlarm) {
		this.logAlarm = logAlarm;
	}
	
	public Keyword getKeyword() {
		return this.keyword;
	}
	
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
}
