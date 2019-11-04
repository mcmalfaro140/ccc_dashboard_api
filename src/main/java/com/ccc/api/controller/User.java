package com.ccc.api.controller;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Users")
public class User implements Serializable {
	private static final long serialVersionUID = 5982399613450687207L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	@Column(name="Id")
	private int id;
	@NotNull
	@Column(name="Username")
	private String username;
	@NotNull
	@Column(name="Email")
	private String email;
	//@Column(name="Dashboard", columnDefinition="json")
	//@Convert(attributeName="Dashboard", converter=JsonConverter.class)
	//private Map<String, Object> dashboard;
	
	public User() {
	}
	
	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public User(int id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("User{Id='%d', Username='%s', Email='%s'}", this.id, this.username, this.email);
	}
}
