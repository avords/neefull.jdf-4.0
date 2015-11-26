package com.mvc.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.envers.Audited;
@Entity(name="F_ROLE_LOCATION")
@IdClass(RoleLocationPK.class)
@Audited
public class RoleLocation implements Serializable {
	@Id
	private Long roleId;
	@Id
	private Long locationId;
	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	public Long getLocationId() {
    	return locationId;
    }
	public void setLocationId(Long locationId) {
    	this.locationId = locationId;
    }
}

class RoleLocationPK implements Serializable {
	private Long roleId;
	private Long locationId;
	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	public Long getLocationId() {
    	return locationId;
    }
	public void setLocationId(Long locationId) {
    	this.locationId = locationId;
    }
}