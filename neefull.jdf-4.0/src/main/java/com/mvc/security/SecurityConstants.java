package com.mvc.security;

/**
 * Security Constants
 */
public final class SecurityConstants {
	private SecurityConstants() {
	}
	/**
	 * 用户登录名
	 */
	public static final String LOGIN_NAME = "s_loginName";
	/**
	 * 用户中文名
	 */
	public static final String FULL_NAME = "s_fullName";
	/**
	 * 用户ID
	 */
	public static final String USER_ID = "s_userId";
	/**
	 * 令牌
	 */
	public static final String SECURITY_TOKEN = "s_token";
	/**
	 * 是否登录
	 */
	public static final String IS_LOGIN = "isLogin";
	/**
	 * 登录链接
	 */
	public static final String LOGIN_URL = "loginUrl";

	/**
	 * IP
	 */
	public static final String IP = "ip";
	/**
	 * 最后请求的链接
	 */
	public static final String LAST_REQUEST_URL = "lastRequestUrl";
	/**
	 * 不需要登录的链接
	 */
	public static final String NOT_NEED_LOGIN_URLS = "notNeedLoginUrls";
	/**
	 * SESSION中用户对象KEY
	 */
	public static final String SESSION_USER = "s_user";
	/**
	 * 随机验证码
	 */
	public static final String RANDOM_CODE = "s_randomCode";
	/**
	 * 保持登录
	 */
	public static final String KEEP_LOGIN = "s_keepLogin";
	/**
	 * 超级管理员
	 */
	public static final String SUPER_MANAGER = "admin";
	/**
	 * 菜单权限
	 */
	public static final String MENU_PERMISSION = "menuPermission";
	/**
	 * 操作权限
	 */
	public static final String OPERATION_PERMISSION = "operationPermission";

}
