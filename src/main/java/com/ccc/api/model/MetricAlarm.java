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
	
	public void setMetricAlarmId(Long metricAlarmId) {
		this.metricAlarmId = metricAlarmId;
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
		return Math.abs(31 * (Objects.hashCode(this.metricAlarmId) + Objects.hashCode(this.metricAlarmArn)));
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof MetricAlarm) ? this.metricAlarmId == ((MetricAlarm)obj).metricAlarmId : false;
	}
	
	@Override
	public String toString() {
		String usernames = this.getUsernames();
		
		return String.format(
			"MetricAlarm{MetricAlarmId=%d, AlarmArn=%s, Users=%s}",
			this.metricAlarmId, this.metricAlarmArn, usernames
		);
	}
	
	private String getUsernames() {
		String[] usernames = new String[this.userList.size()];
		
		for (int index = 0; index < usernames.length; ++index) {
			usernames[index] = this.userList.get(index).getUsername();
		}
		
		return '[' + String.join(",", usernames) + ']';
	}
}
