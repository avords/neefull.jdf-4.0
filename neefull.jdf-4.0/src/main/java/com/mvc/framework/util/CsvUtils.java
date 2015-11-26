package com.mvc.framework.util;

import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {
	private CsvUtils(){
	}
	public static List<String> splitCSV(String src) throws Exception {
		if (src == null || src.equals("")){
			return new ArrayList<String>(0);
		}
		StringBuilder st = new StringBuilder();
		List<String> result = new ArrayList<String>();
		boolean beginWithQuote = false;
		for (int i = 0; i < src.length(); i++) {
			char ch = src.charAt(i);
			if (ch == '\"') {
				if (beginWithQuote) {
					i++;
					if (i >= src.length()) {
						result.add(st.toString());
						st = new StringBuilder();
						beginWithQuote = false;
					} else {
						ch = src.charAt(i);
						if (ch == '\"') {
							st.append(ch);
						} else if (ch == ',') {
							result.add(st.toString());
							st = new StringBuilder();
							beginWithQuote = false;
						} else {
							throw new Exception(
									"Single double-quote char mustn't exist in filed "
											+ (result.size() + 1)
											+ " while it is begined with quote\nchar at:"
											+ i);
						}
					}
				} else if (st.length() == 0) {
					beginWithQuote = true;
				} else {
					throw new Exception(
							"Quote cannot exist in a filed which doesn't begin with quote!\nfield:"
									+ (result.size() + 1));
				}
			} else if (ch == ',') {
				if (beginWithQuote) {
					st.append(ch);
				} else {
					result.add(st.toString());
					st = new StringBuilder();
					beginWithQuote = false;
				}
			} else {
				st.append(ch);
			}
		}
		if (st.length() != 0) {
			if (beginWithQuote) {
				throw new Exception("last field is begin with but not end with double quote");
			} else {
				result.add(st.toString());
			}
		}
		return result;
	}

	public static String convertStringToCSV(String convertString) {
		StringBuilder tempBuffer = new StringBuilder(20);
		if (convertString.contains(",") && !convertString.contains("\""))
			tempBuffer.append("\"").append(convertString).append("\"");
		else if (!convertString.contains("\"") && !convertString.contains(",")) {
			tempBuffer.append(convertString);
		} else {
			tempBuffer.append("\"").append(convertString.replace("\"", "\"\""))
					.append("\"");
		}
		return tempBuffer.toString();
	}
}
