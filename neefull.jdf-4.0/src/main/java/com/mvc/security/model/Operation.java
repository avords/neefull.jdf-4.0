package com.mvc.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;
@Entity(name = "F_OPERATION")
@Audited
public class Operation extends BaseEntity {
	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String name;
	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String code;
	
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public String getCode() {
    	return code;
    }
	public void setCode(String code) {
    	this.code = code;
    }

}
