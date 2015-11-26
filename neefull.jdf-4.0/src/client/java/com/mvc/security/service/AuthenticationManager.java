package com.mvc.security.service;

import com.mvc.security.model.AuthenticationResult;

public interface AuthenticationManager {
	AuthenticationResult authenticationLogin(String loginid, String password);
	AuthenticationResult authenticationSystemToken(String userToken);
}
