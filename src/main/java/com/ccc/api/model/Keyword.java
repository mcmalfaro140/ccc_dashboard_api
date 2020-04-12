package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity(name="Keywords")
@Table(name="Keywords")
@DynamicUpdate
public class Keyword implements Serializable {
	private static final long serialVersionUID = 2313514433783049935L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="KeywordId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long keywordId;
	
	@Size(max=70)
	@Column(name="Word", unique=true)
	private String word;
	
	@NotNull
	@ManyToMany(mappedBy="keywordList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<LogAlarm> logAlarmList;
	
	public Keyword() {
	}
	
	public Keyword(String word, List<LogAlarm> logAlarmList) {
		this.word = word;
		this.logAlarmList = logAlarmList;
	}
	
	public Keyword(Long keywordId, Optional<String> word, List<LogAlarm> logAlarmList) {
		this.keywordId = keywordId;
		this.logAlarmList = logAlarmList;
		
		if (word.isPresent()) {
			this.word = word.get();
		}
		else {
			this.word = null;
		}
	}
	
	public Long getKeywordId() {
		return this.keywordId;
	}
	
	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
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
		
		return Math.abs(31 * (Objects.hashCode(this.keywordId) + Objects.hashCode(this.word)));
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Keyword) ? this.keywordId == ((Keyword)obj).keywordId : false;
	}
	
	@Override
	public String toString() {
		String logAlarmNames = this.getLogAlarmNames();
		
		return String.format(
			"Keyword{KeywordId=%d, Word=%s, LogAlarms=%s}",
			this.keywordId, this.word, logAlarmNames
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
