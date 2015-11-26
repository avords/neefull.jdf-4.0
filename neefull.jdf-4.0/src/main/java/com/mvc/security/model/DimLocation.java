package com.mvc.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;
@Entity(name="F_DIM_LOCATION")
@Audited
public class DimLocation extends BaseEntity {
	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.LARGE_LENGTH)
	private String fullCode;
	@NotNull
	@Column(length=Constants.ModelDefine.LARGE_LENGTH)
	private String fullName;
	/**
	 * For example:market-08
	 */
	private Integer category;

	public String getFullCode() {
    	return fullCode;
    }
	public void setFullCode(String fullCode) {
    	this.fullCode = fullCode;
    }
	public String getFullName() {
    	return fullName;
    }
	public void setFullName(String fullName) {
    	this.fullName = fullName;
    }
	public Integer getCategory() {
    	return category;
    }
	public void setCategory(Integer category) {
    	this.category = category;
    }
	@Transient
	public String getFullLocation() {
		if(null==category){
			return fullCode;
		}
    	return category + Department.PATH_SEPARATOR + fullCode;
    }
}
