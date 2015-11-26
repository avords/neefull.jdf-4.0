package com.mvc.security.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.junit.Ignore;
@Ignore
public class UserTest {
	private static final String ROOT_URL = "http://localhost:8080/news/user";

	public static void main(String[] args) throws Exception {
		save();
	}

	public static void save() throws Exception {
		for (int i = 0; i < 1; i++) {
			URL url = new URL(ROOT_URL + "/" + i + "/" + (new Date()).getTime());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			rd.close();
			conn.disconnect();
		}
	}
}
