package com.mvc.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.Tree;

@Entity(name = "F_DEPARTMENT")
@Audited
public class Department extends Tree{

	public static final String PATH_SEPARATOR = Menu.PATH_SEPARATOR;
	public static final Department ROOT = new Department();
	static{
		ROOT.setObjectId(0L);
		ROOT.setParentId(-1L);
		ROOT.setName("ROOT");
		ROOT.setFullName(PATH_SEPARATOR + "ROOT");
		ROOT.setLayer(PATH_SEPARATOR + 0);
		ROOT.setParent(true);
	}
	private Long leaderId;
	private Long adminId;
	@Column(length = Constants.ModelDefine.LARGER_LENGTH)
	private String fullName;
	@Column(length = Constants.ModelDefine.MEDIUM_LENGTH)
	private String telephone;

	public String getFullName() {
    	return fullName;
    }

	public void setFullName(String fullName) {
    	this.fullName = fullName;
    }

	public Long getLeaderId() {
    	return leaderId;
    }

	public void setLeaderId(Long leaderId) {
    	this.leaderId = leaderId;
    }

	public String getTelephone() {
    	return telephone;
    }

	public void setTelephone(String telephone) {
    	this.telephone = telephone;
    }

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
}
