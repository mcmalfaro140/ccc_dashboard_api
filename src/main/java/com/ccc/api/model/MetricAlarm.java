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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="MetricAlarms")
@Table(name="MetricAlarms")
public class MetricAlarm implements Serializable {
	private static final long serialVersionUID = 5302804181895613828L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MetricAlarmId")
	private Long metricAlarmId;
	
	@Column(name="AlarmArn", nullable=false, unique=true)
	private String alarmArn;
	
	@ManyToMany(mappedBy="metricAlarmList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<User> userList;
	
	public MetricAlarm() {
	}
	
	public MetricAlarm(String alarmArn, List<User> userList) {
		this.alarmArn = alarmArn;
		this.userList = userList;
	}
	
	public MetricAlarm(Long metricAlarmId, String alarmArn, List<User> userList) {
		this.metricAlarmId = metricAlarmId;
		this.alarmArn = alarmArn;
		this.userList = userList;
	}
	
	public Long getMetricAlarmId() {
		return this.metricAlarmId;
	}
	
	public String getAlarmArn() {
		return this.alarmArn;
	}
	
	public void setAlarmArn(String alarmArn) {
		this.alarmArn = alarmArn;
	}
	
	public List<User> getUserList() {
		return this.userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
