package com.mvc;

public final class ProjectConstants {
	private static final int PARANT_LAYER = 3;
	/**
	 * ROOT
	 */
	public static final String ROOT_PROJECT = "";
	private ProjectConstants() {
	}

	/**
	 * Project Name
	 */
	public static final String PROJECT_NAME;

	static {
		String url = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String[] path = url.split("/");
		String context = path[path.length - PARANT_LAYER];
		if("ROOT".equalsIgnoreCase(context)){
			PROJECT_NAME = ROOT_PROJECT;
		}else{
			PROJECT_NAME = context;
		}
		//Weblogic bug
		System.getProperties().setProperty("file.encoding", "UTF-8");
	}
}
