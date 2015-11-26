package com.mvc.security.model;

import java.io.Serializable;

public class AuthenticationResult implements Serializable {
	private int result;

	private String description;

	public AuthenticationResult(int result,String description){
		this.result = result;
		this.description = description;
	}
	public final static AuthenticationResult AUTHENTICATION_RESULT_OK = new AuthenticationResult(0,"");

	public final static AuthenticationResult AUTHENTICATION_RESULT_PASSWORD_OVERDUE = new AuthenticationResult(1,"login.passwordExpired"); //预留

	public final static AuthenticationResult AUTHENTICATION_RESULT_PASSWORD_ERROR = new AuthenticationResult(2,"login.wrongPassword");  //预留

	public final static AuthenticationResult AUTHENTICATION_RESULT_USER_NOT_EXISTS = new AuthenticationResult(3,"login.accountNotExists"); //预留

	public final static AuthenticationResult AUTHENTICATION_RESULT_NOT_LOGIN = new AuthenticationResult(4,"login.pleaseLogin");

	public final static AuthenticationResult AUTHENTICATION_RESULT_FAIL = new AuthenticationResult(6,"login.authenticateFailed");

	public final static AuthenticationResult AUTHENTICATION_RESULT_IP_LOCK = new AuthenticationResult(8,"login.runOutOfAttempts");

	public final static AuthenticationResult AUTHENTICATION_RESULT_ACCOUNT_LOCKED = new AuthenticationResult(10,"login.accountLocked");

	public final static AuthenticationResult AUTHENTICATION_RESULT_ACCOUNT_LOGOUT = new AuthenticationResult(11,"login.accountCanceld");

	public final static AuthenticationResult AUTHENTICATION_RESULT_ACCOUNT_UNUSED = new AuthenticationResult(12,"login.accountUnable");

	public String getDescription() {
		return description;
	}

	public int getResult() {
		return result;
	}

	public String toString(){
		return result + "-" + description;
	}
}
