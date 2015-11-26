package com.mvc.component.sms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;
@Entity(name="S_SMS_SEND_LOG")
public class SmsSendLog extends BaseEntity {
	@NotNull
	private Long businessId;
	@NotNull
	private Long mobile;
	@NotNull
	private Date sendDate;
	@Length(max=70)
	@NotNull
	@Column(length=Constants.ModelDefine.LARGER_LENGTH)
	private String content;
	public Long getMobile() {
    	return mobile;
    }
	public void setMobile(Long mobile) {
    	this.mobile = mobile;
    }
	public Date getSendDate() {
    	return sendDate;
    }
	public void setSendDate(Date sendDate) {
    	this.sendDate = sendDate;
    }
	public String getContent() {
    	return content;
    }
	public void setContent(String content) {
    	this.content = content;
    }
	public Long getBusinessId() {
    	return businessId;
    }
	public void setBusinessId(Long businessId) {
    	this.businessId = businessId;
    }
}
