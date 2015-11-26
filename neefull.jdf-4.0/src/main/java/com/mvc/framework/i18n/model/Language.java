package com.mvc.framework.i18n.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.mvc.framework.model.BaseEntity;

@Entity(name="F_LANGUAGE")
public class Language extends BaseEntity {
	public static final int STATUS_NORMAL = 1;
	public static final int STATUS_INVALID = 2;
	@NotNull
	private String name;
	@NotNull
	private String displayName;
	@NotNull
	private Integer status;
	@Transient
	private List<MessageProperty> messageProperties;
	
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public Integer getStatus() {
    	return status;
    }
	public void setStatus(Integer status) {
    	this.status = status;
    }
	public List<MessageProperty> getMessageProperties() {
    	return messageProperties;
    }
	public void setMessageProperties(List<MessageProperty> messageProperties) {
    	this.messageProperties = messageProperties;
    }
	public String getDisplayName() {
    	return displayName;
    }
	public void setDisplayName(String displayName) {
    	this.displayName = displayName;
    }
}
