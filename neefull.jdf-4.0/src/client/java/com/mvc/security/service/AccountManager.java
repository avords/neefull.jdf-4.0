package com.mvc.security.service;

import com.mvc.security.model.User;

public interface AccountManager {
	User getUserByUserId(Long userId);
	User getUserByLoginName(String loginName);
	User getUserByMobilePhone(String mobilePhone);

}
