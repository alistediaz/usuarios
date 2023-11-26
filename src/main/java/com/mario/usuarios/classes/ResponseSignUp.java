package com.mario.usuarios.classes;

import java.util.Date;

public class ResponseSignUp {
	long id;
	Date created;
	Date lastLogin;
	String token;
	boolean isActive;
	
	public ResponseSignUp(){}
	
	public ResponseSignUp(long id, Date created, Date lastLogin, String token, boolean isActive) {
		this.id = id;
		this.created = created;
		this.lastLogin = lastLogin;
		this.token = token;
		this.isActive = isActive;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}	
}
