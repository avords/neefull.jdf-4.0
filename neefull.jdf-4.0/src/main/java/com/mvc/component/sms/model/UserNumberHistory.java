package com.mvc.component.sms.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "S_USER_NUMBER_HISTORY")
@IdClass(UserNumberPK.class)
public class UserNumberHistory{
	@Id
	private Long businessId;
	@Id
	private Long userNumber;
	private Integer status;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}

	public Integer getStatus() {
    	return status;
    }

	public void setStatus(Integer status) {
    	this.status = status;
    }
}
