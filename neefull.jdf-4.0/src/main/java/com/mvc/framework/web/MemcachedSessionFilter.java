package com.mvc.framework.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.framework.cache.CacheService;
import com.mvc.framework.config.GlobalConfig;
import com.mvc.framework.session.HttpServletRequestWrapper;
import com.mvc.framework.util.CookieUtils;

public class MemcachedSessionFilter implements Filter {

	private static final long serialVersionUID = 5113265991460154541L;
	private String sessionName;
	private String cookieDomain;
	private String cookiePath;
	private CacheService cacheService;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	        throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String seesionId = CookieUtils.getCookieByName(request.getCookies(), sessionName);
		if (seesionId == null || seesionId.length() == 0) {
			seesionId = java.util.UUID.randomUUID().toString();
			Cookie mycookies = new Cookie(sessionName, seesionId);
			mycookies.setMaxAge(-1);
			if (this.cookieDomain != null && this.cookieDomain.length() > 0) {
				mycookies.setDomain(this.cookieDomain);
			}
			mycookies.setPath(this.cookiePath);
			response.addCookie(mycookies);
		}
		filterChain.doFilter(new HttpServletRequestWrapper(seesionId, request, cacheService), servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.sessionName = GlobalConfig.getSessionName();
		this.cookieDomain = GlobalConfig.getCookieDomain();
		this.cookiePath = GlobalConfig.getCookiePath();
	}

	public void destroy() {
	}

	public CacheService getCacheService() {
    	return cacheService;
    }

	public void setCacheService(CacheService cacheService) {
    	this.cacheService = cacheService;
    }

}
