package com.mvc.framework.service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.mvc.framework.config.GlobalConfig;

@Service
public class CookieService {
	private static final int DEFAULT_MAX_AGE = -1;
	private String domain;
	private String path;
	private int maxAge = DEFAULT_MAX_AGE;
	private boolean secure = false;

	@PostConstruct
	public void init() {
		domain = GlobalConfig.getCookieDomain();
		path = GlobalConfig.getCookiePath();
	}

	public void addCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setSecure(secure);
		addCookie(response, cookie);
	}

	public void addCookie(HttpServletResponse response, Cookie cookie) {
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

}
