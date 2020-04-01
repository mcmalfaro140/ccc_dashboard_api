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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Entity(name="Users")
@Table(name="Users")
public class User implements Serializable {
	private static final long serialVersionUID = 4066752461555608563L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserId")
	private Long userId;
	
	@Column(name="Username", nullable=false, unique=true)
	private String username;
	
	@Column(name="Password", nullable=false)
	private String password;
	
	@Column(name="Email", nullable=false, unique=true)
	private String email;
	
	@Column(name="Dashboard", nullable=false)
	private String dashboard;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefUserLogAlarm",
		joinColumns={
			@JoinColumn(
				name="UserId",
				referencedColumnName="UserId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="LogAlarmId",
				referencedColumnName="LogAlarmId",
				nullable=false
			)
		}
	)
	private List<LogAlarm> logAlarmList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="XRefUserMetricAlarm",
		joinColumns={
			@JoinColumn(
				name="UserId",
				referencedColumnName="UserId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="MetricAlarmId",
				referencedColumnName="MetricAlarmId",
				nullable=false
			)
		}
	)
	private List<MetricAlarm> metricAlarmList;
	
	public User() {
	}
	
	public User(
			String username,
			String password,
			String email,
			String dashboard,
			List<LogAlarm> logAlarmList,
			List<MetricAlarm> metricAlarmList
	) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.dashboard = dashboard;
		this.logAlarmList = logAlarmList;
		this.metricAlarmList = metricAlarmList;
	}
	
	public User(
			Long userId,
			String username,
			String password,
			String email,
			String dashboard,
			List<LogAlarm> logAlarmList,
			List<MetricAlarm> metricAlarmList
	) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dashboard = dashboard;
		this.logAlarmList = logAlarmList;
		this.metricAlarmList = metricAlarmList;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDashboard() {
		return this.dashboard;
	}
	
	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}
	
	public List<LogAlarm> getLogAlarmList() {
		return this.logAlarmList;
	}
	
	public void setLogAlarmList(List<LogAlarm> logAlarmList) {
		this.logAlarmList = logAlarmList;
	}
	
	public List<MetricAlarm> getMetricAlarmList() {
		return this.metricAlarmList;
	}
	
	public void setMetricAlarmList(List<MetricAlarm> metricAlarmList) {
		this.metricAlarmList = metricAlarmList;
	}
	
	public Claims toClaims() {
		Claims claims = Jwts.claims();
		
		claims.put("UserId", this.userId);
		claims.put("Username", this.username);
		claims.put("Email", this.email);
		    
		return claims;
	}

	public static User fromClaims(Claims claims) {
		User user = new User();
		
		user.userId = claims.get("UserId", Long.class);
		user.username = claims.get("Username", String.class);
		user.email = claims.get("Email", String.class);
		
		return user;
	}
}
