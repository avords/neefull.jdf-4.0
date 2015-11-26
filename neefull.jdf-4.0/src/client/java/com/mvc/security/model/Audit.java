package com.mvc.security.model;

import java.io.Serializable;
import java.util.Date;

public class Audit implements Serializable {
	private Long userId;

	private Long menuId;

	private String ip;

	private Date beginDate;

	private int spentTime;

	private String exceptionMsg;

	public Long getMenuId() {
    	return menuId;
    }

	public void setMenuId(Long menuId) {
    	this.menuId = menuId;
    }

	public String getIp() {
    	return ip;
    }

	public void setIp(String ip) {
    	this.ip = ip;
    }

	public Date getBeginDate() {
    	return beginDate;
    }

	public void setBeginDate(Date beginDate) {
    	this.beginDate = beginDate;
    }

	public int getSpentTime() {
    	return spentTime;
    }

	public void setSpentTime(int spentTime) {
    	this.spentTime = spentTime;
    }

	public String getExceptionMsg() {
    	return exceptionMsg;
    }

	public void setExceptionMsg(String exceptionMsg) {
    	this.exceptionMsg = exceptionMsg;
    }

	public Long getUserId() {
    	return userId;
    }

	public void setUserId(Long userId) {
    	this.userId = userId;
    }
}
