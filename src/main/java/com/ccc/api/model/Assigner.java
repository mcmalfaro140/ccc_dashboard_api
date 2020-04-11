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

@Entity(name="Assigners")
@Table(name="Assigners")
public class Assigner implements Serializable {
	private static final long serialVersionUID = 4954967087750929695L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AssignerId")
	private Long assignerId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="UserId")
	private User user;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="LogAlarmSNSTopicId")
	private XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic;
	
	public Assigner() {
	}
	
	public Assigner(User user, XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic) {
		this.user = user;
		this.xrefLogAlarmSNSTopic = xrefLogAlarmSNSTopic;
	}
	
	public Assigner(Long assignerId, User user, XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic) {
		this.assignerId = assignerId;
		this.user = user;
		this.xrefLogAlarmSNSTopic = xrefLogAlarmSNSTopic;
	}
	
	public Long getAssignerId() {
		return this.assignerId;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public XRefLogAlarmSNSTopic getXRefLogAlarmSNSTopic() {
		return this.xrefLogAlarmSNSTopic;
	}
	
	public void setXRefLogAlarmSNSTopic(XRefLogAlarmSNSTopic xrefLogAlarmSNSTopic) {
		this.xrefLogAlarmSNSTopic = xrefLogAlarmSNSTopic;
	}
	
	@Override
	public int hashCode() {
		return 31 * this.assignerId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Assigner) ? this.assignerId == ((Assigner)obj).assignerId : false;
	}
}
