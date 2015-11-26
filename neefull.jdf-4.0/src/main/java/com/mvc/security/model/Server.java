package com.mvc.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.mvc.Constants;
import com.mvc.framework.model.BaseEntity;
@Entity(name="F_SERVER")
@Audited
public class Server extends BaseEntity {
	public static final int APP_TYPE_J2EE = 1;
	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String name;
	@NotNull
	@Column(unique=true,length=Constants.ModelDefine.SMALL_LENGTH)
	private String context;
	private Integer type = APP_TYPE_J2EE;
	@NotNull
	private Integer port;
	@NotNull
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String protocol;
	/**
	 * 域名
	 */
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String domain;
	private Integer status;
	@Column(length=Constants.ModelDefine.LARGE_LENGTH)
	private String message;
	@Column(length=Constants.ModelDefine.MEDIUM_LENGTH)
	private String ip;
	
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public Integer getPort() {
    	return port;
    }
	public void setPort(Integer port) {
    	this.port = port;
    }
	public String getDomain() {
    	return domain;
    }
	public void setDomain(String domain) {
    	this.domain = domain;
    }
	public Integer getStatus() {
    	return status;
    }
	public void setStatus(Integer status) {
    	this.status = status;
    }
	public String getMessage() {
    	return message;
    }
	public void setMessage(String message) {
    	this.message = message;
    }
	public String getIp() {
    	return ip;
    }
	public void setIp(String ip) {
    	this.ip = ip;
    }
	public String getContext() {
    	return context;
    }
	public void setContext(String context) {
    	this.context = context;
    }
	public Integer getType() {
    	return type;
    }
	public void setType(Integer type) {
    	this.type = type;
    }
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
