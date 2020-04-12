package com.ccc.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity(name="Users")
@Table(name="Users")
@DynamicUpdate
public class User implements Serializable {
	private static final long serialVersionUID = 4066752461555608563L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UserId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long userId;
	
	@NotNull
	@Size(max=50)
	@Column(name="Username", nullable=false, unique=true)
	private String username;
	
	@NotNull
	@Size(max=65535)
	@Column(name="Password", nullable=false)
	private String password;
	
	@NotNull
	@Column(name="Dashboard", nullable=false)
	private String dashboard;
	
	@NotNull
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
	
	@NotNull
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
	
	@NotNull
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList;
	
	public User() {
	}
	
	public User(
			String username,
			String password,
			String dashboard,
			List<LogAlarm> logAlarmList,
			List<MetricAlarm> metricAlarmList,
			List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList
	) {
		this.username = username;
		this.password = password;
		this.dashboard = dashboard;
		this.logAlarmList = logAlarmList;
		this.metricAlarmList = metricAlarmList;
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
	}
	
	public User(
			Long userId,
			String username,
			String password,
			String dashboard,
			List<LogAlarm> logAlarmList,
			List<MetricAlarm> metricAlarmList,
			List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList
	) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.dashboard = dashboard;
		this.logAlarmList = logAlarmList;
		this.metricAlarmList = metricAlarmList;
		this.xrefLogAlarmSNSTopicList = xrefLogAlarmSNSTopicList;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
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
		this.logAlarmList.clear();
		this.logAlarmList.addAll(logAlarmList);
	}
	
	public List<MetricAlarm> getMetricAlarmList() {
		return this.metricAlarmList;
	}
	
	public void setMetricAlarmList(List<MetricAlarm> metricAlarmList) {
		this.metricAlarmList.clear();
		this.metricAlarmList.addAll(metricAlarmList);
	}
	
	public List<XRefLogAlarmSNSTopic> getXRefLogAlarmSNSTopicList() {
		return this.xrefLogAlarmSNSTopicList;
	}
	
	public void setXRefLogAlarmSNSTopicList(List<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicList) {
		this.xrefLogAlarmSNSTopicList.clear();
		this.xrefLogAlarmSNSTopicList.addAll(xrefLogAlarmSNSTopicList);
	}
	
	@Override
	public int hashCode() {		
		return Math.abs(
				31 * 
				(Objects.hashCode(this.userId) +
				Objects.hashCode(this.username) +
				Objects.hashCode(this.password) +
				Objects.hashCode(this.dashboard))
		);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof User) ? this.userId == ((User)obj).userId : false;
	}
	
	
	public String toString() {
		String logAlarmNames = this.getLogAlarmNames();
		String metricAlarmNames = this.getMetricAlarmNames();
		String xrefLogAlarmSNSTopicNames = this.getXRefLogAlarmSNSTopicNames();
		
		return String.format(
			"User{UserId=%d, Username=%s, Password=%s, Dashboard=%s, LogAlarms=%s, MetricAlarms=%s, XRefLogAlarmSNSTopics=%s}",
			this.userId, this.username, this.password, this.dashboard, logAlarmNames, metricAlarmNames, xrefLogAlarmSNSTopicNames
		);
	}
	
	private String getLogAlarmNames() {
		String[] logAlarmNames = new String[this.logAlarmList.size()];
		
		for (int index = 0; index < logAlarmNames.length; ++index) {
			logAlarmNames[index] = this.logAlarmList.get(index).getAlarmName();
		}
		
		return '[' + String.join(",", logAlarmNames) + ']';
	}
	
	private String getMetricAlarmNames() {
		String[] metricAlarmNames = new String[this.metricAlarmList.size()];
		
		for (int index = 0; index < metricAlarmNames.length; ++index) {
			metricAlarmNames[index] = this.metricAlarmList.get(index).getAlarmArn();
		}
		
		return '[' + String.join(",", metricAlarmNames) + ']';
	}
	
	private String getXRefLogAlarmSNSTopicNames() {
		String[] xrefLogAlarmSNSTopicNames = new String[this.xrefLogAlarmSNSTopicList.size()];
		
		for (int index = 0; index < xrefLogAlarmSNSTopicNames.length; ++index) {
			XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic = this.xrefLogAlarmSNSTopicList.get(index);
			
			String logAlarmName = xrefLogAlarmSNSTopic.getLogAlarm().getAlarmName();
			String snsTopicName = xrefLogAlarmSNSTopic.getSNSTopic().getTopicName();
			
			xrefLogAlarmSNSTopicNames[index] = String.format(
					"(LogAlarm=%s, SNSTopic=%s)",
					logAlarmName, snsTopicName
			);
		}
		
		return '[' + String.join(",", xrefLogAlarmSNSTopicNames) + ']';
	}
}
