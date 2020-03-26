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
@Table(name="Users")
public class User implements Serializable {
	private static final long serialVersionUID = 4066752461555608563L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserId")
	private int userId;
	
	@Column(name="Username", nullable=false, unique=true)
	private String username;
	
	@Column(name="Password", nullable=false)
	private String password;
	
	@Column(name="Email", nullable=false, unique=true)
	private String email;
	
	@Column(name="Dashboard", nullable=false)
	private String dashboard;
	
	@ManyToMany
	private List<LogAlarm> logAlarmList;
	
	public User() {
	}
	
	public User(
			String username,
			String password,
			String email,
			String dashboard,
			List<LogAlarm> logAlarmList
	) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.dashboard = dashboard;
		this.logAlarmList = logAlarmList;
	}
	
	public User(
			int userId,
			String username,
			String password,
			String email,
			String dashboard,
			List<LogAlarm> logAlarmList
	) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dashboard = dashboard;
		this.logAlarmList = logAlarmList;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getDashboard() {
		return this.dashboard;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
}
