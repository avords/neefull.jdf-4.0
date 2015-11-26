package com.mvc.framework.service;

import javax.servlet.http.HttpServletRequest;

import com.mvc.framework.util.CookieUtils;
import com.mvc.security.SecurityConstants;

public class StatelessRequestManagerImpl implements RequestManager {

	public long getUserNameFromRequest(HttpServletRequest request) {
		String userId = CookieUtils.getEncodedCookieByName(request.getCookies(), SecurityConstants.USER_ID);
		return Long.parseLong(userId);
	}

}
