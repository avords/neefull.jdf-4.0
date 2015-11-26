package com.mvc.component.sms.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * User phobe number
 */
@Entity(name = "S_USER_NUMBER")
@IdClass(UserNumberPK.class)
public class UserNumber implements Serializable {
	/**
	 * Not send
	 */
	public static final int STATUS_NOT_SEND = 1;
	/**
	 * Send
	 */
	public static final int STATUS_SEND_SUCCESS = 2;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessId == null) ? 0 : businessId.hashCode());
		result = prime * result + ((userNumber == null) ? 0 : userNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserNumber other = (UserNumber) obj;
		if (businessId == null) {
			if (other.businessId != null)
				return false;
		} else if (!businessId.equals(other.businessId))
			return false;
		if (userNumber == null) {
			if (other.userNumber != null)
				return false;
		} else if (!userNumber.equals(other.userNumber))
			return false;
		return true;
	}
   
}

class UserNumberPK implements Serializable {
	private Long businessId;
	private Long userNumber;
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
}