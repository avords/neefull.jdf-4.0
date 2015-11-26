package com.mvc.portal.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mvc.framework.util.FrameworkContextUtils;
import com.mvc.portal.service.OnlineUserManager;
import com.mvc.security.model.LoginLog;

public class OnlineUserListener implements HttpSessionListener {

	private OnlineUserManager onlineUserManager = null;

	public void sessionCreated(HttpSessionEvent event) {
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		setOnlineUserManager(session);
		Long userId = FrameworkContextUtils.getCurrentUserId();
		if(userId!=null){
			onlineUserManager.deleteOnlineUser(userId ,LoginLog.LOGOUT_BY_SERVER);
		}
	}

	private void setOnlineUserManager(HttpSession session) {
		if(onlineUserManager==null){
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
			BeanFactory beanFactory = (BeanFactory)context;
			onlineUserManager = (OnlineUserManager)beanFactory.getBean("onlineUserManager");
		}
	}

}