package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Keywords")
public class Keyword implements Serializable {
	private static final long serialVersionUID = 2313514433783049935L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="KeywordId")
	private Integer keywordId;
	
	@Column(name="Word", unique=true)
	private String word;
	
	@ManyToMany
	private List<LogAlarm> logAlarmList;
	
	public Keyword() {
	}
	
	public Keyword(String word, List<LogAlarm> logAlarmList) {
		this.word = word;
		this.logAlarmList = logAlarmList;
	}
	
	public Keyword(Integer keywordId, String word, List<LogAlarm> logAlarmList) {
		this.keywordId = keywordId;
		this.word = word;
		this.logAlarmList = logAlarmList;
	}
	
	public Integer getKeywordId() {
		return this.keywordId;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
}
