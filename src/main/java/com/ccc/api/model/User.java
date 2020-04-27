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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name="Users")
@Table(name="Users")
@DynamicInsert
@DynamicUpdate
public class User implements Serializable {
	private static final long serialVersionUID = 4066752461555608563L;

	@Id
	@Generated(value=GenerationTime.INSERT)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="UserId", nullable=false, unique=true, insertable=false, updatable=false)
	private Long userId;
	
	@Size(max=50)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="Username", nullable=false, unique=true)
	private String username;
	
	@Size(max=65535)
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="Password", nullable=false)
	private String password;
	
	@Basic(optional=false, fetch=FetchType.LAZY)
	@Column(name="Dashboard", nullable=false)
	private String dashboard;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
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
	private Set<MetricAlarm> metricAlarmSet;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(
		name="XRefLogAlarmSNSTopic",
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
	private Set<LogAlarm> logAlarmSet;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(
		name="XRefLogAlarmSNSTopic",
		joinColumns={
			@JoinColumn(
				name="UserId",
				referencedColumnName="UserId",
				nullable=false
			)
		},
		inverseJoinColumns={
			@JoinColumn(
				name="SNSTopicId",
				referencedColumnName="SNSTopicId",
				nullable=false
			)
		}
	)
	private Set<SNSTopic> snsTopicSet;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, orphanRemoval=true)
	private Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet;
	
	public User() {
	}
	
	public User(
			String username,
			String password,
			String dashboard,
			Set<MetricAlarm> metricAlarmSet,
			Set<LogAlarm> logAlarmSet,
			Set<SNSTopic> snsTopicSet,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.username = username;
		this.password = password;
		this.dashboard = dashboard;
		this.metricAlarmSet = metricAlarmSet;
		this.logAlarmSet = logAlarmSet;
		this.snsTopicSet = snsTopicSet;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
	}
	
	public User(
			Long userId,
			String username,
			String password,
			String dashboard,
			Set<MetricAlarm> metricAlarmSet,
			Set<LogAlarm> logAlarmSet,
			Set<SNSTopic> snsTopicSet,
			Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet
	) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.dashboard = dashboard;
		this.metricAlarmSet = metricAlarmSet;
		this.logAlarmSet = logAlarmSet;
		this.snsTopicSet = snsTopicSet;
		this.xrefLogAlarmSNSTopicSet = xrefLogAlarmSNSTopicSet;
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
	
	public Set<MetricAlarm> getMetricAlarmSet() {
		return this.metricAlarmSet;
	}
	
	public void setMetricAlarmSet(Set<MetricAlarm> metricAlarmSet) {
		this.metricAlarmSet.clear();
		this.metricAlarmSet.addAll(metricAlarmSet);
	}
	
	public Set<LogAlarm> getLogAlarmSet() {
		return this.logAlarmSet;
	}
	
	public void setLogAlarmSet(Set<LogAlarm> logAlarmSet) {
		this.logAlarmSet.clear();
		this.logAlarmSet.addAll(logAlarmSet);
	}
	
	public Set<SNSTopic> getSNSTopicSet() {
		return this.snsTopicSet;
	}
	
	public void setSNSTopicSet(Set<SNSTopic> snsTopicSet) {
		this.snsTopicSet.clear();
		this.snsTopicSet.addAll(snsTopicSet);
	}
	
	public Set<XRefLogAlarmSNSTopic> getXRefLogAlarmSNSTopicSet() {
		return this.xrefLogAlarmSNSTopicSet;
	}
	
	public void setXRefLogAlarmSNSTopicSet(Set<XRefLogAlarmSNSTopic> xrefLogAlarmSNSTopicSet) {
		this.xrefLogAlarmSNSTopicSet.clear();
		this.xrefLogAlarmSNSTopicSet.addAll(xrefLogAlarmSNSTopicSet);
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
}
