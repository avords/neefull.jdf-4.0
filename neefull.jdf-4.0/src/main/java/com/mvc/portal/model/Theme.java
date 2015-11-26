package com.mvc.portal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.envers.Audited;

@Entity(name = "F_THEME")
@Audited
public class Theme implements Serializable {
	@Column(length=8)
	private String skin;
	@Id
	private Long userId;

	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
