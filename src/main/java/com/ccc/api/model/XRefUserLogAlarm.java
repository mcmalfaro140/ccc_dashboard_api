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
@Table(name="XRefUserLogAlarm")
public class XRefUserLogAlarm implements Serializable {
	private static final long serialVersionUID = -215305898102448546L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserLogAlarmId")
	private Integer userLogAlarmId;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private LogAlarm logAlarm;
	
	public XRefUserLogAlarm() {
	}
	
	public XRefUserLogAlarm(User user, LogAlarm logAlarm) {
		this.user = user;
		this.logAlarm = logAlarm;
	}
	
	public XRefUserLogAlarm(Integer userLogAlarmId, User user, LogAlarm logAlarm) {
		this.userLogAlarmId = userLogAlarmId;
		this.user = user;
		this.logAlarm = logAlarm;
	}
	
	public Integer getUserLogAlarmId() {
		return  this.userLogAlarmId;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public LogAlarm getLogAlarm() {
		return this.logAlarm;
	}
	
	public void setLogAlarm(LogAlarm logAlarm) {
		this.logAlarm = logAlarm;
	}
}
