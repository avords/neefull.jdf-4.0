package com.mvc.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.envers.Audited;

@Entity(name="F_ROLE_MENU")
@IdClass(RoleMenuPK.class)
@Audited
public class RoleMenu  implements Serializable {
	@Id
	private Long roleId;
	@Id
	private Long menuId;
	
	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	public Long getMenuId() {
    	return menuId;
    }
	public void setMenuId(Long menuId) {
    	this.menuId = menuId;
    }
}

class RoleMenuPK implements Serializable {
	private Long roleId;
	private Long menuId;
	public Long getRoleId() {
    	return roleId;
    }
	public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }
	public Long getMenuId() {
    	return menuId;
    }
	public void setMenuId(Long menuId) {
    	this.menuId = menuId;
    }
}