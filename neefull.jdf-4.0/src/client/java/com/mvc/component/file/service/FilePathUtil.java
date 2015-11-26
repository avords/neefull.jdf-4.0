/*
 * FilePathUtil.java 2009-12-4
 * Copyright(c) 2007-2010 by broadtext
 * ALL Rights Reserved.
 */
package com.mvc.component.file.service;

import java.io.File;

public final class FilePathUtil {

	private FilePathUtil() {

	}

	public static String getYearPath(Long fileId) {
		return String.valueOf(fileId).substring(0, 4);
	}

	public static String getMonthPath(Long fileId) {
		return getYearPath(fileId) + File.separator + String.valueOf(fileId).substring(4, 6);
	}

	public static String getDayPath(Long fileId) {
		return getMonthPath(fileId) + File.separator + String.valueOf(fileId).substring(6, 8);
	}

	public static String getYMDPath(Long fileId) {
		return String.valueOf(fileId).substring(0, 8);
	}

	public static String checkFilePath(String path) {
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + File.separator;
		}
		return path;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

}