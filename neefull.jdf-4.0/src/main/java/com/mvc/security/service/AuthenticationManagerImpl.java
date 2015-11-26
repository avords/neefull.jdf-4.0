package com.mvc.security.service;

import org.springframework.stereotype.Service;

import com.mvc.security.model.AuthenticationResult;

@Service("authenticationManager")
public class AuthenticationManagerImpl implements AuthenticationManager {

	public AuthenticationResult authenticationLogin(String loginid, String password) {
		return null;
	}

	public AuthenticationResult authenticationSystemToken(String userToken) {
		return null;
	}

}
