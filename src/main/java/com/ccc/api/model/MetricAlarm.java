package com.ccc.api.model;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
=======

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
>>>>>>> MetricAlarmController
import javax.persistence.Table;

@Entity(name="MetricAlarms")
@Table(name="MetricAlarms")
public class MetricAlarm implements Serializable {
	private static final long serialVersionUID = 4198681733980071621L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MetricAlarmId")
	private Long metricAlarmId;
	
	@Column(name="AlarmArn")
	private String metricAlarmArn;
	
	public MetricAlarm() {
	}
	
	public MetricAlarm(
			Long metricAlarmId,
			String metricAlarmArn
	) {
		this.metricAlarmId = metricAlarmId;
		this.metricAlarmArn = metricAlarmArn;
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

}
