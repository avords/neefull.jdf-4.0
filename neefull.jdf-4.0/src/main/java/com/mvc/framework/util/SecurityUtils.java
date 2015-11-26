package com.mvc.framework.util;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mvc.security.SecurityConstants;

public final class SecurityUtils {
	private static final Logger LOGGER = Logger.getLogger(SecurityUtils.class);

	public static String genUserToken(String loginid, String validateCode) throws Exception {
		long time = System.currentTimeMillis();
		String md5Str = encodeWithHex(MD5.md5Encode(loginid + time + validateCode));
		String token = new String(org.apache.commons.codec.binary.Base64
		        .encodeBase64((loginid + "@" + time + "|" + md5Str).getBytes()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(loginid + "@" + time + "|" + md5Str + ",TOKEN:" + token);
		}
		return token;
	}

	public static void main(String[] args) throws Exception {
		String encode = encodeWithValidataCode("pubx","1234AAAA");
	    System.out.println(encode);
	    System.out.println(decodeWithTimeOut(encode, "1234AAAA", -1));
    }
	
	
	public static String encodeWithValidataCode(String plainText, String validateCode) throws Exception {
		long time = System.currentTimeMillis();
		String md5Str = encodeWithHex(MD5.md5Encode(plainText + time + validateCode));
		md5Str = plainText + "@" + time + "|" + md5Str;
		return new String(Hex.encodeHex(md5Str.getBytes()));
	}
	
	public static String decodeWithTimeOut(String encodedText, String validateCode, int timeOut) {
		try{
			encodedText =new String(Hex.decodeHex(encodedText.toCharArray()));
			String plainText = encodedText.split("@")[0];
			String[] arr = encodedText.split("@")[1].split("\\|");
			long time = Long.parseLong(arr[0]);
			String md5Str = arr[1];
			if(encodeWithHex(MD5.md5Encode(plainText + time + validateCode)).equals(md5Str)){
				if(timeOut == -1){
					return plainText;
				}else if(time+timeOut>=System.currentTimeMillis()){
					return plainText;
				}
			}
		}catch (Exception e) {
			LOGGER.error("decodeWithTimeOut",e);
		}
		return null;
	}

	public static String getLoginIdByUserToken(String userToken) throws Exception {
		if (StringUtils.isNotEmpty(userToken)) {
			byte[] decrUserToken = decodeWithHex(userToken);
			String[] decrArr = new String(decrUserToken).split("\\|");
			if (decrArr != null && decrArr.length == 2) {
				String[] arr = decrArr[0].split("@");
				if (arr != null && arr.length == 2) {
					if (StringUtils.isNotEmpty(arr[0])) {
						return arr[0];
					}
				}
			}
		}
		return null;
	}

	public static final void setUserTokenCookie(HttpServletResponse response, String loginId, String validateCode) throws Exception {
		setCookie(response, SecurityConstants.SECURITY_TOKEN, genUserToken(loginId, validateCode));
	}

	public static final String getUserTokenFromCookie(Cookie[] userCookies) {
		return getCookieByName(userCookies, SecurityConstants.SECURITY_TOKEN);
	}

	public static String getCookieByName(Cookie[] userCookies, String name) {
		if (StringUtils.isBlank(name) || userCookies == null || userCookies.length == 0) {
			return null;
		}
		for (Cookie cookie : userCookies) {
			if (name.equals(cookie.getName())) {
				String val = cookie.getValue();
				if (StringUtils.isNotBlank(val)) {
					try {
						val = new String(Hex.decodeHex(val.toCharArray()));
					} catch (DecoderException e) {
						LOGGER.error("getCookieByName",e);
					}
				}
				return val;
			}
		}
		return null;
	}

	public static void setCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, new String(Hex.encodeHex(value.getBytes())));
		addCookie(response, cookie);
	}

	private static void addCookie(HttpServletResponse response, Cookie aCookie) {
		aCookie.setMaxAge(-1);
		aCookie.setDomain(".sh.cmcc");
		aCookie.setPath("/");
		response.addCookie(aCookie);
	}

	private static byte[] decodeWithHex(String coded) throws Exception {
		return Hex.decodeHex(coded.toCharArray());
	}

	private static String encodeWithHex(byte[] original) {
		return new String(Hex.encodeHex(original));
	}

	static class MD5 {
		private final static String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
		        "d", "e", "f" };

		public static String byteArrayToHexString(byte[] b) {
			StringBuilder resultSb = new StringBuilder();
			for (int i = 0; i < b.length; i++) {
				resultSb.append(byteToHexString(b[i]));
			}
			return resultSb.toString();
		}

		private static String byteToHexString(byte b) {
			int n = b;
			if (n < 0) {
				n = 256 + n;
			}
			int d1 = n / 16;
			int d2 = n % 16;
			return HEX_DIGITS[d1] + HEX_DIGITS[d2];
		}
		// MessageDigest JDK provided
		public static byte[] md5Encode(String origin) throws Exception {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(origin.getBytes());
		}

	}
}
