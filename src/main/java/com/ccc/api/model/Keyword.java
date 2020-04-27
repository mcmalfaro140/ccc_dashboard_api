package com.ccc.api.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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

@Entity(name="Keywords")
@Table(name="Keywords")
@DynamicInsert
@DynamicUpdate
public class Keyword implements Serializable {
	private static final long serialVersionUID = 2313514433783049935L;

	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="KeywordId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long keywordId;
	
	@Size(max=70)
	@Basic(fetch=FetchType.LAZY)
	@Column(name="Word", unique=true, nullable=false)
	private String word;
	
	@ManyToMany(mappedBy="keywordSet", fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private Set<LogAlarm> logAlarmSet;
	
	public Keyword() {
	}
	
	public Keyword(String word, Set<LogAlarm> logAlarmSet) {
		this.word = word;
		this.logAlarmSet = logAlarmSet;
	}
	
	public Keyword(Long keywordId, Optional<String> word, Set<LogAlarm> logAlarmSet) {
		this.keywordId = keywordId;
		this.logAlarmSet = logAlarmSet;
		
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
	
	public Set<LogAlarm> getLogAlarmSet() {
		return this.logAlarmSet;
	}
	
	public void setLogAlarmSet(Set<LogAlarm> logAlarmSet) {
		this.logAlarmSet.clear();
		this.logAlarmSet.addAll(logAlarmSet);
	}
	
	@Override
	public int hashCode() {
		
		return Math.abs(31 * (Objects.hashCode(this.keywordId) + Objects.hashCode(this.word)));
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Keyword) ? this.keywordId == ((Keyword)obj).keywordId : false;
	}
}
