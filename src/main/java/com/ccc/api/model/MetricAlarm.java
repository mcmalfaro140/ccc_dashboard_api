package com.ccc.api.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="MetricAlarms")
@Table(name="MetricAlarms")
@DynamicInsert
@DynamicUpdate
public class MetricAlarm implements Serializable {
	private static final long serialVersionUID = 4198681733980071621L;
	
	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="MetricAlarmId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long metricAlarmId;
	
	@Size(max=255)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="AlarmArn", nullable=false, unique=true)
	private String metricAlarmArn;
	
	
	
	public MetricAlarm() {
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
	
	@Override
	public int hashCode() {		
		return Math.abs(31 * (Objects.hashCode(this.metricAlarmId) + Objects.hashCode(this.metricAlarmArn)));
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof MetricAlarm) ? this.metricAlarmId == ((MetricAlarm)obj).metricAlarmId : false;
	}
}
