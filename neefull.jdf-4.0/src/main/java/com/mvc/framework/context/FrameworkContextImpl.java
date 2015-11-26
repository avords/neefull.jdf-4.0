package com.mvc.framework.context;

import com.mvc.security.model.User;

public class FrameworkContextImpl implements FrameworkContext {
	private static final long	serialVersionUID	= 936374620512271312L;
	private User currentUser;
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
}
