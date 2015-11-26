package com.mvc.framework.taglib;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.mvc.framework.util.LocaleUtils;
import com.mvc.framework.util.MessageUtils;

public class MessageTag extends TagSupport {
	
	private static final Logger LOGGER = Logger.getLogger(MessageTag.class);
	private String code;

	public int doStartTag() throws JspException {
		code = (String) ExpressionEvaluatorManager.evaluate("code", code, String.class, this, pageContext);
		if(StringUtils.isNotBlank(code)){
			Locale locale = LocaleUtils.getLocale(pageContext.getSession());
			try {
				pageContext.getOut().write(MessageUtils.getMessage(code,locale));
			} catch (Exception e) {
				LOGGER.error("doStartTag()", e);
			}
		}
		return EVAL_PAGE;
	}

	public String getCode() {
    	return code;
    }

	public void setCode(String code) {
    	this.code = code;
    }
}