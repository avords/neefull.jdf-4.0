package com.mvc.framework.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mvc.framework.util.PropertiesUtils;

/**
 * Error message utils
 *
 * @time: 2009-7-27 05:55:38
 * @author pubx
 */
public final class ErrorMessage {

	private ErrorMessage() {

	}

	private static final Map<String, Integer> ERROR_MAP = new HashMap<String, Integer>(100);

	private static final int UNKNOWN_ERROR = 10000;

	private static final Logger LOGGER = Logger.getLogger(ErrorMessage.class);

	private static final String ERROR_MESSAGE_PROPERTIES = "errorMessage.properties";

	static {
		try{
			ERROR_MAP.put("java.lang.NullPointerException", 0);
			Properties properties = PropertiesUtils.getProperties(ERROR_MESSAGE_PROPERTIES);
			for (Enumeration e = properties.keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				ERROR_MAP.put(key, Integer.valueOf(properties.get(key).toString()));
			}
		}catch (Exception e){
			LOGGER.error(e);
		}
		
	}

	/**
	 * If not found,then <code>UNKNOWN_ERROR</code>
	 * @param exceptionClass
	 * @return error code
	 */
	public static int getErrorCode(Throwable exceptionClass) {
		String causeClassName = exceptionClass.getClass().getName();
		return getErrorCode(causeClassName);
	}

	public static int getErrorCode(String causeClassName) {
		Integer errorCode = ERROR_MAP.get(causeClassName);
		if (errorCode == null) {
			errorCode = UNKNOWN_ERROR;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(causeClassName);
			}
		}
		return errorCode;
	}

}
