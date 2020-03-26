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
	private int keywordId;
	
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
	
	public Keyword(int keywordId, String word, List<LogAlarm> logAlarmList) {
		this.keywordId = keywordId;
		this.word = word;
		this.logAlarmList = logAlarmList;
	}
	
	public int getKeywordId() {
		return this.keywordId;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
}
