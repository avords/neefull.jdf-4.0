package com.mvc.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.envers.Audited;
@Entity(name="F_ROLE_OPERATION")
@IdClass(RoleOperationPK.class)
@Audited
public class RoleOperation implements Serializable {
	@Id
	private Long roleId;
	@Id
	private Long operationId;

	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	public Long getOperationId() {
    	return operationId;
    }
	public void setOperationId(Long operationId) {
    	this.operationId = operationId;
    }
}

class RoleOperationPK implements Serializable {
	private Long roleId;
	private Long operationId;

	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	public Long getOperationId() {
    	return operationId;
    }
	public void setOperationId(Long operationId) {
    	this.operationId = operationId;
    }
}