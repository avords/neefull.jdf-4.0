package com.mvc.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.mvc.framework.config.GlobalConfig;

public final class SecurityUtils {

	private static final int DEFAULT_URL_LENGTH = 50;

	private static final int DEFAULT_HTTP_PORT = 80;

	private static final int ONE_HOUR = 60 * 60 * 1000;

	private static final int EXPIRED_INDEX = 3;

	private static final int MAX_ASCII = 256;

	private static final int HEX_LENGTH = 16;

	private static final String HTTP_URL_PREFIX = "http://";

	private static final Logger LOGGER = Logger.getLogger(SecurityUtils.class);

	private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
	        "e", "f" };

	private SecurityUtils() {

	}

	public static String generatePassword(String plainText) {
		return md5Encode(plainText);
	}


	public static String generateSecurityToken(String loginName) {
		return generateSecurityToken(loginName, ONE_HOUR);
	}

	public static String generateSecurityToken(String loginName, int expiredInMill) {
		// begin + userId + expire + validateCode
		// md5
		// + userId + expire
		// hex
		long time = System.currentTimeMillis();
		String md5 = md5Encode(time + loginName + expiredInMill + GlobalConfig.getValidateCode());
		md5 = md5 + "|" + time + "|" + loginName + "|" + expiredInMill + "|";
		return String.valueOf(Hex.encodeHex(md5.getBytes()));
	}

	public static String getLoginNameFromSecurityToke(String token){
		String result = null;
		try {
			if(validateSecurityToken(token)){
				byte[] b = Hex.decodeHex(token.toCharArray());
				String[] arr = new String(b).split("\\|");
				result = arr[2];
			}
		}catch (DecoderException e) {
			LOGGER.error(e);
		}
		return result;
	}

	public static boolean validateSecurityToken(String token) {
		boolean result = false;
		if (token != null) {
			try {
				byte[] b = Hex.decodeHex(token.toCharArray());
				String[] arr = new String(b).split("\\|");
				if (arr.length == EXPIRED_INDEX + 1) {
					String newMd5 = md5Encode(arr[1] + arr[2] + arr[EXPIRED_INDEX] + GlobalConfig.getValidateCode());
					return arr[0].equals(newMd5);
				}
			} catch (DecoderException e) {
				LOGGER.debug("validateSecurityToken",e);
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String result = generatePassword("123abc");
		System.out.println(result);
//		System.out.println(getLoginNameFromSecurityToke(result));
//		System.out.println(validateSecurityToken(result));
//		System.out.println(generatePassword("1"));

//		MessageDigest md = MessageDigest.getInstance("MD5");
//		System.out.println(new String(md.digest("1".getBytes())));
//		System.out.println(generatePassword("1"));
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = MAX_ASCII + n;
		}
		int d1 = n / HEX_LENGTH;
		int d2 = n % HEX_LENGTH;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	private static String md5Encode(String origin) {
		String resultString = origin;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error(ex);
		}
		return resultString;
	}

	public static String getHttpRequestUrl(HttpServletRequest request) {
		StringBuilder result = new StringBuilder(DEFAULT_URL_LENGTH);
		result.append(HTTP_URL_PREFIX);
		result.append(request.getServerName());
		result.append(DEFAULT_HTTP_PORT == request.getServerPort() ? "" : ":" + request.getServerPort());
		result.append(request.getRequestURI());
		return result.toString();
	}

	public static boolean isIp(String serverName) {
		Pattern pattern = Pattern.compile("[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}");
		Matcher matcher = pattern.matcher(serverName);
		return matcher.find();
	}
}
