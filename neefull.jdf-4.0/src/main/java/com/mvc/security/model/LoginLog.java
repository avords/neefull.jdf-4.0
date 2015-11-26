package com.mvc.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

@Entity(name="F_LOGIN_LOG")
public class LoginLog extends BaseEntity {
	
	public static final int LOGIN_RESULT_SUCCESS = 1;
	
	public static final int LOGIN_RESULT_FAIL = 2;
	//Login out by hand
	public static final int LOGOUT_BY_MANUAL = 1;
	//Login out automatically
	public static final int LOGOUT_BY_AUTO = 2;
	//Login out by client(IE)
	public static final int LOGOUT_BY_CLIENT = 3;
	//Login out by Server(Session timeout)
	public static final int LOGOUT_BY_SERVER = 4;
	//Login out by force
	public static final int LOGOUT_BY_FORCED = 5;
	
	public static final int STATUS_ONLINE = 1;
	public static final int STATUS_LOGOUT = 2;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String loginName;
	private Long userId;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String userName;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String department;
	private Date beginDate;
	private Integer spendTime;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String ip;
	private Integer result;
	private Date endDate;
	private Integer loginFrom;
	private Integer logoutForm;
	@Column(length=Constants.ModelDefine.LARGE_LENGTH)
	private String message;
	private Long onlineTime;
	@NotNull
	private Integer status;

	public String getLoginName() {
    	return loginName;
    }
	public void setLoginName(String loginName) {
    	this.loginName = loginName;
    }

	public Long getUserId() {
    	return userId;
    }
	public void setUserId(Long userId) {
    	this.userId = userId;
    }

	public String getUserName() {
    	return userName;
    }
	public void setUserName(String userName) {
    	this.userName = userName;
    }
	public String getDepartment() {
    	return department;
    }
	public void setDepartment(String department) {
    	this.department = department;
    }

	public Date getBeginDate() {
    	return beginDate;
    }
	public void setBeginDate(Date beginDate) {
    	this.beginDate = beginDate;
    }

	public Integer getSpendTime() {
    	return spendTime;
    }
	public void setSpendTime(Integer spendTime) {
    	this.spendTime = spendTime;
    }
	public String getIp() {
    	return ip;
    }
	public void setIp(String ip) {
    	this.ip = ip;
    }
	public Integer getResult() {
    	return result;
    }
	public void setResult(Integer result) {
    	this.result = result;
    }

	public Date getEndDate() {
    	return endDate;
    }
	public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }

	public Integer getLoginFrom() {
    	return loginFrom;
    }
	public void setLoginFrom(Integer loginFrom) {
    	this.loginFrom = loginFrom;
    }

	public Integer getLogoutForm() {
    	return logoutForm;
    }
	public void setLogoutForm(Integer logoutForm) {
    	this.logoutForm = logoutForm;
    }
	public String getMessage() {
    	return message;
    }
	public void setMessage(String message) {
    	this.message = message;
    }

	public Long getOnlineTime() {
    	return onlineTime;
    }
	public void setOnlineTime(Long onlineTime) {
    	this.onlineTime = onlineTime;
    }
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
