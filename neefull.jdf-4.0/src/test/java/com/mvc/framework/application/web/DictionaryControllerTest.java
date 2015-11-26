package com.mvc.framework.application.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Ignore;
@Ignore
public class DictionaryControllerTest{
	private static final String ROOT_URL = "http://www.mvc.com/kj/dict/";

	public static void main(String[] args) throws Exception  {
		for (int i = 0; i < 1; i++) {
			URL url = new URL(ROOT_URL + "save?name=" + i + "&comment=" + i + "&value=" + i);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			rd.close();
			conn.disconnect();
		}
	}

}
