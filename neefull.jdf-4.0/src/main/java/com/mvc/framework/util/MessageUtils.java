package com.mvc.framework.util;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mvc.framework.i18n.model.Language;
import com.mvc.framework.i18n.model.MessageProperty;
import com.mvc.framework.i18n.service.LanguageManager;
import com.mvc.framework.i18n.service.MessagePropertyManager;

public class MessageUtils {
	private static final Logger LOGGER = Logger.getLogger(MessageUtils.class);
	private static LanguageManager languageManager;
	private static MessagePropertyManager messagePropertyManager;

	private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<String, Map<String, String>>();

	private MessageUtils() {
	}

	private static final String DEFAULT_RESOURCE_FILE_BASE_NAME = "messages";

	private static final Map<String, ResourceBundle> BINDS = new ConcurrentHashMap<String, ResourceBundle>();

	private static ResourceBundle getBind(String locale) {
		if (locale == null || locale.equals("")) {
			locale = LocaleUtils.getDefaultLocale().toString();
		}
		ResourceBundle bind = BINDS.get(locale);
		if (bind == null) {
			bind = ResourceBundle.getBundle(DEFAULT_RESOURCE_FILE_BASE_NAME + "_" + locale);
			BINDS.put(locale, bind);
		}
		return bind;
	}

	public final static String getMessage(String key, Locale locale) {
		if (locale == null) {
			locale = LocaleUtils.getDefaultLocale();
		}
		String message = null;
		message = getMessageFromDB(key, locale, message);
		if (StringUtils.isEmpty(message)) {
			message = getMessageFromBundle(key, locale, message);
		}
		if (StringUtils.isEmpty(message)) {
			return key;
		} else {
			return message;
		}
	}

	protected static String getMessageFromDB(String key, Locale locale, String message) {
		try {
			Map<String, String> messageMap = getMessageMap(locale);
			message = messageMap.get(key);
		} catch (Exception e) {
			LOGGER.error("getMessage", e);
		}
		return message;
	}

	protected static String getMessageFromBundle(String key, Locale locale, String message) {
		try {
			ResourceBundle resourceBundle = getBind(locale.toString());
			message = resourceBundle.getString(key);
		} catch (Exception e) {
			LOGGER.warn("Resource not fuound:" + key + " of " + locale);
		}
		return message;
	}

	public final static String getMessage(String key, Object[] values, Locale locale) {
		String message = getMessage(key, locale);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				message = StringUtils.replace(message, "{" + i + "}", values[i].toString());
			}
		}
		return message;
	}

	private static Map<String, String> getMessageMap(Locale locale) {
		String languageName = locale.toString();
		Map<String, String> meaageMap = LOCAL_CACHE.get(languageName);
		if (meaageMap == null) {
			Language language = languageManager.getLanguageByName(languageName);
			if (language != null) {
				List<MessageProperty> messageProperties = messagePropertyManager
				        .getMessagePropertyByLanguageId(language.getObjectId());
				meaageMap = new ConcurrentHashMap<String, String>(messageProperties.size());
				for (MessageProperty property : messageProperties) {
					meaageMap.put(property.getMessageKey(), property.getMessageValue());
				}
			} else {
				meaageMap = new HashedMap();
			}
			LOCAL_CACHE.put(languageName, meaageMap);
		}
		return meaageMap;
	}

	public final static void clear() {
		LOCAL_CACHE.clear();
	}

	public final static String getMessage(String key, HttpServletRequest request) {
		Locale locale = LocaleUtils.getLocale(request);
		return getMessage(key, locale);
	}

	public final static String getMessage(String key, String[] values, HttpServletRequest request) {
		Locale locale = LocaleUtils.getLocale(request);
		return getMessage(key, values, locale);
	}

	@Deprecated
	public static String getMessage(String key) {
		String message = null;
		try {
			message = getBind(null).getString(key);
		} catch (Exception e) {
		}
		if (StringUtils.isEmpty(message)) {
			return key;
		} else {
			return message;
		}
	}

	public static String getMessage(String key, String[] values) {
		String message = getMessage(key);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				message = StringUtils.replace(message, "{" + i + "}", values[i]);
			}
		}
		return message;
	}
	
	public static String getMessage(String key, HttpServletRequest request, String[] values) {
		String message = getMessage(key,request);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				message = StringUtils.replace(message, "{" + i + "}", values[i]);
			}
		}
		return message;
	}

	public static void main(String[] args) {
		// AppBaseException a = new AppBaseException("errors.required",new
		System.out.println(getMessage("ok", new String[] { "HELLO" }));
	}

	public void setLanguageManager(LanguageManager languageManager) {
		MessageUtils.languageManager = languageManager;
	}

	public void setMessagePropertyManager(MessagePropertyManager messagePropertyManager) {
		MessageUtils.messagePropertyManager = messagePropertyManager;
	}
}
