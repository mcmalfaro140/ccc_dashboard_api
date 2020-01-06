package com.ccc.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Users implements Serializable {
	 private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue
	    private Integer UserId;

	    private String Username;

	    private String Email;

	    private String Dashboard;

	    
	    public Integer getUserId() {
	        return UserId;
	    }

	    public void setUserId(Integer userId) {
	        this.UserId = userId;
	    }

	    public String getUsername() {
	        return Username;
	    }

	    public void setName(String username) {
	        this.Username = username;
	    }

	    public String getEmail() {
	        return Email;
	    }

	    public void setEmail(String email) {
	        this.Email = email;
	    }

	    public String getDashboard() {
	        return Dashboard;
	    }

	    public void setDashboard(String dashboard) {
	    	this.Dashboard = dashboard;
	    }
}
