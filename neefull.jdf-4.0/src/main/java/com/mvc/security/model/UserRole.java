package com.mvc.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.envers.Audited;
@IdClass(UserRolePK.class)
@Entity(name="f_user_role")
@Audited
public class UserRole implements Serializable {
	@Id
	private Long userId;
	@Id
	private Long roleId;
	public Long getUserId() {
    	return userId;
    }
	public void setUserId(Long userId) {
    	this.userId = userId;
    }
	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
}

class UserRolePK implements Serializable {
	private Long userId;
	private Long roleId;
	public Long getUserId() {
    	return userId;
    }
	public void setUserId(Long userId) {
    	this.userId = userId;
    }
	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
}