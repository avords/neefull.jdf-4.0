package com.mvc.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.envers.Audited;

@Entity(name = "F_USER_DEPARTMENT")
@IdClass(UserDepartmentPK.class)
@Audited
public class UserDepartment implements Serializable {
	@Id
	private Long userId;
	@Id
	private Long departmentId;
	public Long getUserId() {
    	return userId;
    }
	public void setUserId(Long userId) {
    	this.userId = userId;
    }
	public Long getDepartmentId() {
    	return departmentId;
    }
	public void setDepartmentId(Long departmentId) {
    	this.departmentId = departmentId;
    }
}

class UserDepartmentPK implements Serializable {
	private Long userId;
	private Long departmentId;
	public Long getUserId() {
    	return userId;
    }
	public void setUserId(Long userId) {
    	this.userId = userId;
    }
	public Long getDepartmentId() {
    	return departmentId;
    }
	public void setDepartmentId(Long departmentId) {
    	this.departmentId = departmentId;
    }
	
}
