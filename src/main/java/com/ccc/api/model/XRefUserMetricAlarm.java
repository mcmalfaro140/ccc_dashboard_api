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
@Table(name="XRefUserMetricAlarm")
public class XRefUserMetricAlarm implements Serializable {
	private static final long serialVersionUID = -1967948358806217695L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userMetricAlarmId")
	private Integer userMetricAlarmId;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private MetricAlarm metricAlarm;
	
	public XRefUserMetricAlarm() {
	}
	
	public XRefUserMetricAlarm(User user, MetricAlarm metricAlarm) {
		this.user = user;
		this.metricAlarm = metricAlarm;
	}
	
	public Integer getUserMetricAlarmId() {
		return this.userMetricAlarmId;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public MetricAlarm getMetricAlarm() {
		return this.metricAlarm;
	}
	
	public void setMetricAlarm(MetricAlarm metricAlarm) {
		this.metricAlarm = metricAlarm;
	}
}
