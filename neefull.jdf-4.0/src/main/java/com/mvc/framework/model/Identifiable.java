package com.mvc.framework.model;

import java.io.Serializable;

/**
 * Unique primary key interface
 * @author pubx 2010-4-21 11:12:46
 */
public interface Identifiable extends Serializable {
	Long getObjectId();

	void setObjectId(Long objectId);
}
