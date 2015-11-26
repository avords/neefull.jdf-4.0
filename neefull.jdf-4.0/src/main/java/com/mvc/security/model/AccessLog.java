package com.mvc.security.model;

import java.util.Date;

import javax.persistence.Entity;

import com.mvc.framework.model.BaseEntity;

@Entity(name="F_ACCESS_LOG")
public class AccessLog  extends BaseEntity {
	private Integer visitDate;
	private Long userId;
	private String userName;
	private String department;
	private Date beginDate;
	private Long spendTime;
	private Long menuId;
	private String menuName;
	private String menuPath;
	private String errorMessage;

	public Integer getVisitDate() {
    	return visitDate;
    }
	public void setVisitDate(Integer visitDate) {
    	this.visitDate = visitDate;
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

	public Long getSpendTime() {
    	return spendTime;
    }
	public void setSpendTime(Long spendTime) {
    	this.spendTime = spendTime;
    }

	public Long getMenuId() {
    	return menuId;
    }
	public void setMenuId(Long menuId) {
    	this.menuId = menuId;
    }

	public String getMenuName() {
    	return menuName;
    }
	public void setMenuName(String menuName) {
    	this.menuName = menuName;
    }

	public String getMenuPath() {
    	return menuPath;
    }
	public void setMenuPath(String menuPath) {
    	this.menuPath = menuPath;
    }

	public String getErrorMessage() {
    	return errorMessage;
    }
	public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }


}
