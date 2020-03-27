package com.ccc.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MetricAlarms")
public class MetricAlarm implements Serializable {
	private static final long serialVersionUID = 5302804181895613828L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MetricAlarmId")
	private int metricAlarmId;
	
	@Column(name="AlarmArn", nullable=false, unique=true)
	private String alarmArn;
	
	public MetricAlarm() {
	}
	
	public MetricAlarm(String alarmArn) {
		this.alarmArn = alarmArn;
	}
	
	public MetricAlarm(int metricAlarmId, String alarmArn) {
		this.metricAlarmId = metricAlarmId;
		this.alarmArn = alarmArn;
	}
	
	public int getMetricAlarmId() {
		return this.metricAlarmId;
	}
	
	public String getAlarmArn() {
		return this.alarmArn;
	}
}
