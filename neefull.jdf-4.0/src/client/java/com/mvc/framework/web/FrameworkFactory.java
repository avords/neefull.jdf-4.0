package com.mvc.framework.web;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mvc.component.file.BaseFileManager;
import com.mvc.component.file.ExtendFileManager;
import com.mvc.component.file.FileManager;
import com.mvc.component.mail.EmailManager;
import com.mvc.component.sms.SmsManager;
import com.mvc.component.sms.SmsSender;
import com.mvc.security.service.AccountManager;
import com.mvc.security.service.AuditManager;
import com.mvc.security.service.AuthenticationManager;
import com.mvc.security.service.AuthorizationManager;

/**
 * Framework factory
 * @author pubx
 *
 */
public final class FrameworkFactory {
	private static final Logger LOGGER = Logger.getLogger(FrameworkFactory.class);
	private FrameworkFactory() {
	}
	private static AccountManager accountManager;
	private static AuthenticationManager authenticationManager;
	private static AuthorizationManager authorizationManager;
	private static AuditManager auditManager;

	private static FileManager fileManager;
	private static SmsManager smsManager;
	private static SmsSender smsSender;
	private static EmailManager emailManager;
	private static BaseFileManager baseFileManager;
	private static ExtendFileManager extendFileManager;
	private static BeanFactory beanFactory = null;

	static void init(ServletContext servletContext){
		//get local access bean
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(null!=applicationContext){
			beanFactory = (BeanFactory) applicationContext;
			if(null!=getBean("accountManager")){
				accountManager = (AccountManager)beanFactory.getBean("accountManager");
			}
			if(null!=getBean("authenticationManager")){
				authenticationManager = (AuthenticationManager)beanFactory.getBean("authenticationManager");
			}
			if(null!=getBean("authorizationManager")){
				authorizationManager = (AuthorizationManager)beanFactory.getBean("authorizationManager");
			}
			if(null!=getBean("auditManager")){
				auditManager = (AuditManager)beanFactory.getBean("auditManager");
			}
			if(null!=getBean("fileManager")){
				fileManager = (FileManager)beanFactory.getBean("fileManager");
				extendFileManager = (ExtendFileManager)beanFactory.getBean("fileManager");
			}
			if(null!=getBean("smsManager")){
				smsManager = (SmsManager)beanFactory.getBean("smsManager");
			}
			if(null!=getBean("emailManager")){
				emailManager = (EmailManager)beanFactory.getBean("emailManager");
			}
			if(null!=getBean("smsSender")){
				smsSender = (SmsSender)beanFactory.getBean("smsSender");
			}
			if(null!=getBean("baseFileManager")){
				baseFileManager = (BaseFileManager)beanFactory.getBean("baseFileManager");
			}
		}
		//get remote access bean
		ApplicationContext clientContext = new ClassPathXmlApplicationContext(new String[] {"applicationContext-client.xml"});
		BeanFactory clientBeanFactory = (BeanFactory) clientContext;
		if(null==accountManager&&null!=getBean("accountManagerBurlap")){
			accountManager = (AccountManager)clientBeanFactory.getBean("accountManagerBurlap");
		}
		if(null==authenticationManager&&null!=getBean("authenticationManagerBurlap")){
			authenticationManager = (AuthenticationManager)clientBeanFactory.getBean("authenticationManagerBurlap");
		}
		if(null==authorizationManager&&null!=getBean("authorizationManagerBurlap")) {
			authorizationManager = (AuthorizationManager)clientBeanFactory.getBean("authorizationManagerBurlap");
		}
		if(null==auditManager&&null!=getBean("auditManagerBurlap")){
			auditManager = (AuditManager)clientBeanFactory.getBean("auditManagerBurlap");
		}
		if(null==fileManager&&null!=getBean("fileManagerBurlap")){
			fileManager = (FileManager)clientBeanFactory.getBean("fileManagerBurlap");
			extendFileManager = (ExtendFileManager)clientBeanFactory.getBean("fileManagerBurlap");
		}
		if(null==smsManager&&null!=getBean("smsManagerBurlap")){
			smsManager = (SmsManager)clientBeanFactory.getBean("smsManagerBurlap");
		}
		if(null==emailManager&&null!=getBean("emailManagerBurlap")){
			emailManager = (EmailManager)clientBeanFactory.getBean("emailManagerBurlap");
		}
		if(null==smsSender&&null!=getBean("smsSenderBurlap")){
			smsSender = (SmsSender)clientBeanFactory.getBean("smsSenderBurlap");
		}
		if(null==baseFileManager&&null!=getBean("baseFileManagerBurlap")){
			baseFileManager = (BaseFileManager)clientBeanFactory.getBean("baseFileManagerBurlap");
		}
	}

	public static Object getBean(String beanName){
		Object result = null;
		try{
			result = beanFactory.getBean(beanName);
		}catch (BeansException e) {
			LOGGER.warn("not fuound bean:" + beanName);
		}
		return result;
	}

	public static AccountManager getAccountManager() {
    	return accountManager;
    }

	public static AuthenticationManager getAuthenticationManager() {
    	return authenticationManager;
    }

	public static AuthorizationManager getAuthorizationManager() {
    	return authorizationManager;
    }

	public static AuditManager getAuditManager() {
    	return auditManager;
    }

	public static FileManager getFileManager() {
    	return fileManager;
    }

	public static SmsManager getSmsManager() {
    	return smsManager;
    }

	public static EmailManager getEmailManager() {
    	return emailManager;
    }

	public static SmsSender getSmsSender() {
    	return smsSender;
    }

	public static BaseFileManager getBaseFileManager() {
    	return baseFileManager;
    }
	public static ExtendFileManager getExtendFileManager(){
		return extendFileManager;
	}
}
