package com.mvc.framework.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Ajax response
 *
 * @version 1.0
 * @created 2007-5-24 22:01:08
 * @author pubangxiong
 */
public final class AjaxUtils {
	private AjaxUtils() {
	}

	public static final String RESPONSE_RESULT_SUCCESS_STR = "Success";

	public static final String RESPONSE_RESULT_FAIL_STR = "Fail";
	
	/**
	 * Only response the specific message
	 *
	 * @param response
	 * @param result
	 * @param message
	 * @throws Exception
	 */
	public static void doJsonResponse(HttpServletResponse response, String message) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
	}


	/**
	 * Only response the specific message
	 *
	 * @param response
	 * @param result
	 * @param message
	 * @throws Exception
	 */
	public static void doAjaxResponse(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
	}

	/**
	 * Response result and message(connect by '|')
	 *
	 * @param response
	 * @param result
	 * @param message
	 * @throws Exception
	 */
	public static void doAjaxResponse(HttpServletResponse response, String result, String message) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (result != null && "".equals(result)) {
			out.println(message);
		} else {
			out.println(result + "|" + message);
		}
		out.flush();
		out.close();
	}

	/**
	 * Response an array,will be connect with '|'
	 *
	 * @param response
	 * @param results
	 * @throws Exception
	 */
	public static void doAjaxResponse(HttpServletResponse response, String[] results) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (results != null && results.length > 0) {
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < results.length; i++) {
				buffer.append(results[i] + "|");
			}
			out.print(buffer.substring(0, buffer.lastIndexOf("|")));
		}
		out.flush();
		out.close();
	}

	/**
	 * Response XML format text
	 */
	public static void doAjaxResponseXml(HttpServletResponse response, String message) throws Exception {
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
	}
}
