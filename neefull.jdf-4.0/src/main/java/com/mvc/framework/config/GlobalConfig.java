package com.mvc.framework.config;

/**
 * Global configuration across whole domain
 * @author pubx
 *
 */
public class GlobalConfig {
	private static final String DEFAULT_COOKIE_PATH = "/";
	private static final String DEFAULT_SESSION_NAME = "sid";
	private static final String DEFAULT_COOKIE_DOMAIN = "localhost";
	private static final String DEFAULT_VALIDATE_CODE = "000";
	private static final String DEFAULT_LOGIN_URL = "/jdf/login";
	private static final int DEFAULT_REFRESH_PERMISSION_TIME  = 10;
	private static final int DEFAULT_LOGIN_ERROR_WAIT_TIME = 30;
	private static String sessionName = DEFAULT_SESSION_NAME;
	private static String cookieDomain = DEFAULT_COOKIE_DOMAIN;
	private static String cookiePath = DEFAULT_COOKIE_PATH;
	private static String validateCode = DEFAULT_VALIDATE_CODE;
	private static String portalServerName;
	private static int refreshPermissionTime = DEFAULT_REFRESH_PERMISSION_TIME;
	private static String rootUrl;
	private static String loginUrl = DEFAULT_LOGIN_URL;
	private static boolean singleLogin;
	private static int loginErrorWaitTime = DEFAULT_LOGIN_ERROR_WAIT_TIME;
	private static String accessControlAll = "allow";
	private static boolean accessAll = true;
	
	public static String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		GlobalConfig.sessionName = sessionName;
	}
	public static String getCookieDomain() {
		return cookieDomain;
	}
	public void setCookieDomain(String domain) {
		GlobalConfig.cookieDomain = domain;
	}
	public static String getCookiePath() {
		return cookiePath;
	}
	public void setCookiePath(String cookiePath) {
		GlobalConfig.cookiePath = cookiePath;
	}
	public static String getValidateCode() {
    	return validateCode;
    }
	public void setValidateCode(String validateCode) {
		GlobalConfig.validateCode = validateCode;
    }
	public static String getLoginUrl() {
    	return rootUrl + loginUrl;
    }
	public void setLoginUrl(String loginUrl) {
    	GlobalConfig.loginUrl = loginUrl;
    }
	public static String getPortalServerName() {
    	return portalServerName;
    }
	public void setPortalServerName(String portalServerName) {
    	GlobalConfig.portalServerName = portalServerName;
    }
	public static int getRefreshPermissionTime() {
    	return refreshPermissionTime;
    }
	public void setRefreshPermissionTime(int refreshPermissionTime) {
    	GlobalConfig.refreshPermissionTime = refreshPermissionTime;
    }
	public static String getRootUrl() {
		if(null==rootUrl){
			rootUrl = "";
			//throw new IllegalArgumentException("rootUrl can not be null");
		}
    	return rootUrl;
    }
	public void setRootUrl(String rootUrl) {
    	GlobalConfig.rootUrl = rootUrl;
    }
	public static boolean isSingleLogin() {
    	return singleLogin;
    }
	public void setSingleLogin(boolean singleLogin) {
    	GlobalConfig.singleLogin = singleLogin;
    }
	public static int getLoginErrorWaitTime() {
    	return loginErrorWaitTime;
    }
	public void setLoginErrorWaitTime(int loginErrorWaitTime) {
    	GlobalConfig.loginErrorWaitTime = loginErrorWaitTime;
    }
	public static String getAccessControlAll() {
		return accessControlAll;
	}
	public void setAccessControlAll(String accessControlAll) {
		if(accessControlAll!=null){
			GlobalConfig.accessControlAll = accessControlAll;
			if(accessControlAll.equals("allow")){
				accessAll = true;
			}else if(accessControlAll.equals("deny")){
				accessAll = false;
			}
		}
	}
	
	public static boolean isAccessAll() {
		return accessAll;
	}
}
