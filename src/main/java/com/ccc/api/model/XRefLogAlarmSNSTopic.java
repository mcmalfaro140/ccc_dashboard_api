package com.ccc.api.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="XRefLogAlarmSNSTopic")
@Table(name="XRefLogAlarmSNSTopic")
public class XRefLogAlarmSNSTopic implements Serializable {
	private static final long serialVersionUID = 4385868977900164012L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LogAlarmSNSTopicId")
	private Long logAlarmSNSTopicId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="LogAlarmId")
	private LogAlarm logAlarm;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="SNSTopicId")
	private SNSTopic snsTopic;
	
	@OneToOne(mappedBy="xrefLogAlarmSNSTopic", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Assigner assigner;
	
	public XRefLogAlarmSNSTopic() {
	}
	
	public XRefLogAlarmSNSTopic(LogAlarm logAlarm, SNSTopic snsTopic, Assigner assigner) {
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		this.assigner = assigner;
	}
	
	public XRefLogAlarmSNSTopic(Long logAlarmSNSTopicId, LogAlarm logAlarm, SNSTopic snsTopic, Assigner assigner) {
		this.logAlarmSNSTopicId = logAlarmSNSTopicId;
		this.logAlarm = logAlarm;
		this.snsTopic = snsTopic;
		this.assigner = assigner;
	}
	
	public Long getLogAlarmSNSTopicId() {
		return this.logAlarmSNSTopicId;
	}
	
	public LogAlarm getLogAlarm() {
		return this.logAlarm;
	}
	
	public void setLogAlarm(LogAlarm logAlarm) {
		this.logAlarm = logAlarm;
	}
	
	public SNSTopic getSNSTopic() {
		return this.snsTopic;
	}
	
	public void setSNSTopic(SNSTopic snsTopic) {
		this.snsTopic = snsTopic;
	}
	
	public Assigner getAssigner() {
		return this.assigner;
	}
	
	public void setAssigner(Assigner assigner) {
		this.assigner = assigner;
	}
	
	@Override
	public int hashCode() {
		return 31 * this.logAlarmSNSTopicId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof XRefLogAlarmSNSTopic) ? this.logAlarmSNSTopicId == ((XRefLogAlarmSNSTopic)obj).logAlarmSNSTopicId : false;
	}
}
