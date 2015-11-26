/*
 * (#)loginError.java 1.0 2010-12-2
 */
package com.mvc.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;

/**
 *
 * @author bangxiong.pu
 *
 */
@Entity(name="F_LOGIN_ERROR")
public class LoginError extends BaseEntity{
	/**
	 * Expired
	 */
	public static final int REC_STATUS_INVALID = 0;
	/**
	 * Valid
	 */
	public static final int REC_STATUS_VALID = 1;

	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String loginName;

	private Date loginDate;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String proxyIp;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String localIp;

	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public String getProxyIp() {
		return proxyIp;
	}
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
