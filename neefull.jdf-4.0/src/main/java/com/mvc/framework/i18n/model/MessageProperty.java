package com.mvc.framework.i18n.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;
@Entity(name="F_MESSAGE_PROPERTY")
@IdClass(MessagePropertyPK.class)
public class MessageProperty {
	@Id
	private Long languageId;
	@Id
	private String messageKey;
	
	private String messageValue;
	@Transient
	private Language language;
	
	public Long getLanguageId() {
    	return languageId;
    }
	public void setLanguageId(Long languageId) {
    	this.languageId = languageId;
    }
	public Language getLanguage() {
    	return language;
    }
	public void setLanguage(Language language) {
    	this.language = language;
    }
	public String getMessageKey() {
    	return messageKey;
    }
	public void setMessageKey(String messageKey) {
    	this.messageKey = messageKey;
    }
	public String getMessageValue() {
    	return messageValue;
    }
	public void setMessageValue(String messageValue) {
    	this.messageValue = messageValue;
    }
}

class MessagePropertyPK implements Serializable {
	private Integer languageId;
	private String messageKey;
	
	public Integer getLanguageId() {
    	return languageId;
    }
	public void setLanguageId(Integer languageId) {
    	this.languageId = languageId;
    }
	public String getMessageKey() {
    	return messageKey;
    }
	public void setMessageKey(String messageKey) {
    	this.messageKey = messageKey;
    }
	
}
