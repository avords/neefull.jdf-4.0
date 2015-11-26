package com.mvc.framework.model;

import org.hibernate.envers.RevisionListener;

import com.mvc.framework.util.FrameworkContextUtils;

public class MyRevisionListener implements  RevisionListener {
	public void newRevision(Object revisionEntity) {
		MyRevisionEntity myRevisionEntity = (MyRevisionEntity) revisionEntity;
		myRevisionEntity.setUserId(FrameworkContextUtils.getCurrentUserId());
    }

}
