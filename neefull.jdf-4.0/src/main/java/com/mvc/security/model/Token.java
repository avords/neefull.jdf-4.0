package com.mvc.security.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="F_TOKEN")
public class Token implements Serializable{
	@Id
	private String token;
	private Date endDate;
	public String getToken() {
    	return token;
    }
	public void setToken(String token) {
    	this.token = token;
    }
	public Date getEndDate() {
    	return endDate;
    }
	public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }

}
