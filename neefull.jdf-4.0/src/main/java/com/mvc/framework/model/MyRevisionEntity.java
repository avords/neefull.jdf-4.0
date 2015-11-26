package com.mvc.framework.model;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
@Entity(name="revinfo")
@RevisionEntity(MyRevisionListener.class)
public class MyRevisionEntity  extends DefaultRevisionEntity {
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
