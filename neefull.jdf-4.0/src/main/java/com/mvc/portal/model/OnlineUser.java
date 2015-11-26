/*
 * (#)OnlineUser.java 1.0 2010-12-2
 */
package com.mvc.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

/**
 * 
 * @author bangxiong.pu
 *
 */
@Entity(name="F_ONLINE_USER")
public class OnlineUser extends BaseEntity{
	public static final int STATUS_OFFLINE = 0;
	public static final int STATUS_ONLINE = 1;
	@NotNull
	private Long userId;
	
	private Date visiteDate;
	@NotNull
	private Date beginDate;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String userName;
	@NotNull
	@Column(length=Constants.ModelDefine.LARGE_LENGTH)
	private String sessionId;
	/**
	 *  0 :need login out
	 *  1 :normal online
	 */
	private Integer status;

	public Date getVisiteDate() {
		return visiteDate;
	}

	public void setVisiteDate(Date visiteDate) {
		this.visiteDate = visiteDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Long getUserId() {
    	return userId;
    }

	public void setUserId(Long userId) {
    	this.userId = userId;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
