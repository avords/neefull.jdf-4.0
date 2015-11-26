package com.mvc.framework.util;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.mvc.framework.FrameworkConstants;
import com.mvc.framework.config.ProjectConfig;

public final class ThemeUtils {
	private ThemeUtils() {
	}

	public static String getFullCssThemePath(HttpSession session){
		String pathColor = (String) session.getAttribute(FrameworkConstants.SKIN);
		if (StringUtils.isEmpty(pathColor)) {
			pathColor = ProjectConfig.getDefaultSkin();
			session.setAttribute(FrameworkConstants.SKIN, pathColor);
		}
		return DomainUtils.getStaticDomain() + FrameworkConstants.STYLE_ROOT + pathColor + "/";
	}
}
