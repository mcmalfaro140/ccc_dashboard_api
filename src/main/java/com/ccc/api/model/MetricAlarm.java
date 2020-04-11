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
	private static final long serialVersionUID = 4198681733980071621L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MetricAlarmId")
	private Long metricAlarmId;
	
	@Column(name="AlarmArn")
	private String metricAlarmArn;
	
	@ManyToMany(mappedBy="metricAlarmList", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<User> userList;
	
	
	public MetricAlarm() {
	}
	
	public MetricAlarm(String alarmArn, List<User> userList) {
		this.metricAlarmArn = alarmArn;
		this.userList = userList;
	}
	
	public MetricAlarm(Long metricAlarmId, String alarmArn, List<User> userList) {
		this.metricAlarmId = metricAlarmId;
		this.metricAlarmArn = alarmArn;
		this.userList = userList;
	}
	
	public Long getMetricAlarmId() {
		return this.metricAlarmId;
	}
	
	public String getAlarmArn() {
		return this.metricAlarmArn;
	}
	
	public void setAlarmArn(String alarmArn) {
		this.metricAlarmArn = alarmArn;
	}
	
	public List<User> getUserList() {
		return this.userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	@Override
	public int hashCode() {		
		return Math.abs(31 * (this.metricAlarmId.hashCode() + this.metricAlarmArn.hashCode()));
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof MetricAlarm) ? this.metricAlarmId == ((MetricAlarm)obj).metricAlarmId : false;
	}
}
