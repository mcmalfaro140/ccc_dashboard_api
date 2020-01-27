package com.ccc.api.model;

import java.io.Serializable;

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
@Table(name = "Users")
public class Users implements Serializable {
	 private static final long serialVersionUID = 1L;

	    @Id
	    @NotNull
		@Column(name="userid")  
	    private Integer userid;

	    @NotNull
		@Column(name="Username")
	    private String username;
	    
	    @NotNull
	    @Column(name="Password")
	    private String password;

	    @NotNull
		@Column(name="Email")
	    private String email;

	    private String dashboard;
	    
	    public Users() {
		}
		
		public Users(String username, String email) {
			this.username = username;
			this.email = email;
		}
		
		public Users(int id, String username, String email) {
			this.userid = id;
			this.username = username;
			this.email = email;
		}
		
		public Users(int id, String username, String password, String email)
		{
			this.userid=id;
			this.username=username;
			this.password=password;
			this.email=email;
		}

	    
	    public Integer getUserId() {
	        return userid;
	    }

	    public void setUserId(Integer userid) {
	        this.userid = userid;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setName(String username) {
	        this.username = username;
	    }
	    
	    public String getPassword(){
	    	return this.password;
	    }
	    
	    public void setPassword() {
	    	this.password = password;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getDashboard() {
	        return dashboard;
	    }

	    public void setDashboard(String dashboard) {
	    	this.dashboard = dashboard;
	    }
	    
	    @Override
		public String toString() {
			return String.format("User{Id='%d', Username='%s', Email='%s'}", this.userid, this.username, this.email);
		}
}
