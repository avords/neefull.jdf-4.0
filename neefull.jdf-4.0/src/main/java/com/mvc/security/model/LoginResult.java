package com.mvc.security.model;
public enum LoginResult{
	SUCCESS("login.success"),
	NOT_LOGIN("login.please"),
	WRONG_RANDOM_CODE("login.wrongCode"),
	FAIL("login.failed"),
	NO_PRIVILEGE("login.noPermission"),
	LOGIN_LOCKED("login.limitPeriod"),
	ACCOUNT_LOCKED("login.accountLocked"),
	ACCOUNT_LOGOUT("login.accountCanceld");

	public String message;
	LoginResult(String message){
		this.message = message;
	}
}