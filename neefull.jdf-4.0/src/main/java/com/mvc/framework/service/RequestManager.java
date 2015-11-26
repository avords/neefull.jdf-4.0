package com.mvc.framework.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Request manager
 */
public interface RequestManager {
	long getUserNameFromRequest(HttpServletRequest request);
}
