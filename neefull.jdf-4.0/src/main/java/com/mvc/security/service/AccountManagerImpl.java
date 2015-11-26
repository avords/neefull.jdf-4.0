package com.mvc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.security.model.User;
@Service("accountManager")
public class AccountManagerImpl implements AccountManager {
	@Autowired
	private UserManager userManager;

	public User getUserByLoginName(String loginName) {
		return userManager.getUserByLoginName(loginName);
	}

	public User getUserByMobilePhone(String mobilePhone) {
		return null;
	}

	public User getUserByUserId(Long userId) {
		return userManager.getByObjectId(userId);
	}

}
