package com.mvc.framework.util;

import java.util.ArrayList;
import java.util.List;

import com.mvc.ProjectConstants;
import com.mvc.framework.config.GlobalConfig;

public final class DomainUtils {
	private DomainUtils() {
	}

	private static final int DOMAIN_NUMBER;
	private static final List<String> STATIC_DOMAINS = new ArrayList<String>();
	private static final String DYNAMIC_DOMAIN;
	public static final String STATIC_ROOT = "static";
	static {
		if(ProjectConstants.ROOT_PROJECT.equalsIgnoreCase(ProjectConstants.PROJECT_NAME)){
			DYNAMIC_DOMAIN = GlobalConfig.getRootUrl();
		}else{
			DYNAMIC_DOMAIN = GlobalConfig.getRootUrl() + "/" + ProjectConstants.PROJECT_NAME;
		}
		STATIC_DOMAINS.add(DYNAMIC_DOMAIN + "/" + STATIC_ROOT + "/");
		DOMAIN_NUMBER = STATIC_DOMAINS.size();
	}
	private static int currentDomain = 0;

	public static String getStaticDomain() {
		currentDomain++;
		return STATIC_DOMAINS.get(currentDomain % DOMAIN_NUMBER);
	}

	public static String getMainStaticDomain() {
		return STATIC_DOMAINS.get(0);
	}

	public static String getDynamicDomain() {
		return DYNAMIC_DOMAIN;
	}
}
