package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="Keywords")
@Table(name="Keywords")
public class Keyword implements Serializable {
	private static final long serialVersionUID = 2313514433783049935L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="KeywordId")
	private Long keywordId;
	
	@Column(name="Word", unique=true)
	private String word;
	
	@ManyToMany(mappedBy="keywordList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<LogAlarm> logAlarmList;
	
	public Keyword() {
	}
	
	public Keyword(String word, List<LogAlarm> logAlarmList) {
		this.word = word;
		this.logAlarmList = logAlarmList;
	}
	
	public Keyword(Long keywordId, String word, List<LogAlarm> logAlarmList) {
		this.keywordId = keywordId;
		this.word = word;
		this.logAlarmList = logAlarmList;
	}
	
	public Long getKeywordId() {
		return this.keywordId;
	}
	
	public Optional<String> getWord() {
		return Optional.ofNullable(this.word);
	}
	
	public void setWord(Optional<String> word) {
		if (word.isPresent()) {
			this.word = word.get();
		}
		else {
			this.word = null;
		}
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
	
	@Override
	public int hashCode() {
		
		return Math.abs(31 * (this.keywordId.hashCode() + this.word.hashCode()));
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Keyword) ? this.keywordId == ((Keyword)obj).keywordId : false;
	}
}
