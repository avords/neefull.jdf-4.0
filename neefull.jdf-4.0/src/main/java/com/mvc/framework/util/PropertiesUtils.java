package com.mvc.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mvc.framework.exception.FrameworkException;

/**
 * 
 * @time: 2009-7-28 01:36:22
 * @author pubx
 */
public final class PropertiesUtils {

	private PropertiesUtils() {

	}

	public static Properties getProperties(String fileName) throws FrameworkException {
		InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e1) {
			throw new FrameworkException(e1);
		}
		return properties;
	}
}
