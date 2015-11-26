package com.mvc.framework.context;

import java.io.Serializable;

import com.mvc.security.model.User;

/**
 * Framework Context
 * @author pubx 2010-3-29 上午09:27:40
 */
public interface FrameworkContext extends Serializable {
	User getCurrentUser();

	void setCurrentUser(User user);
}
