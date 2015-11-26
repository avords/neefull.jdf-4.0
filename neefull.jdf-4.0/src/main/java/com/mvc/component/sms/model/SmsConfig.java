package com.mvc.component.sms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;
/**
 * Short messsage 
 */
@Entity(name="S_SMS_CONFIG")
@Audited
public class SmsConfig extends BaseEntity {
	/**
	 * Wait send
	 */
	public static final int SMS_STATUS_WAIT_SEND = 1;
	/**
	 * Sending
	 */
	public static final int SMS_STATUS_SENDING = 2;
	/**
	 * Send and wait result
	 */
	public static final int SMS_STATUS_SENDED_WAIT_RESULT = 3;
	/**
	 * Send with error
	 */
	public static final int SMS_STATUS_SENDED_WITH_ERROR = 4;
	/**
	 * Successfully send
	 */
	public static final int SMS_STATUS_SENDED_SUCCESS =  5;
	/**
	 * Paused
	 */
	public static final int SMS_STATUS_PAUSE = 6;

	/**
	 * Business ID
	 */
	private Long businessId;
	/**
	 * Task name
	 */
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String name;
	/**
	 * Content
	 */
	@NotNull
	@Column(length=Constants.ModelDefine.LARGER_LENGTH)
	private String content;
	
	private Date beginTime;
	
	private Date endTime;
	/**
	 * Total phone number
	 */
	private Integer total;
	private Integer status;
	@Transient
	private String userNumbers;
	@Transient
	public String getUserNumbers() {
    	return userNumbers;
    }
	public void setUserNumbers(String userNumbers) {
    	this.userNumbers = userNumbers;
    }
	public Long getBusinessId() {
    	return businessId;
    }
	public void setBusinessId(Long businessId) {
    	this.businessId = businessId;
    }
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public String getContent() {
    	return content;
    }
	public void setContent(String content) {
    	this.content = content;
    }
	public java.util.Date getBeginTime() {
    	return beginTime;
    }
	public void setBeginTime(java.util.Date beginTime) {
    	this.beginTime = beginTime;
    }
	public java.util.Date getEndTime() {
    	return endTime;
    }
	public void setEndTime(java.util.Date endTime) {
    	this.endTime = endTime;
    }
	public Integer getStatus() {
    	return status;
    }
	public void setStatus(Integer status) {
    	this.status = status;
    }
	public Integer getTotal() {
    	return total;
    }
	public void setTotal(Integer total) {
    	this.total = total;
    }
}
