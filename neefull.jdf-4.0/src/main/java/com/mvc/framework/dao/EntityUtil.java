package com.mvc.framework.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil {
	private static Map<String,List<String>> ids = new HashMap<String,List<String>>();

	public static List<String> getId(Class clazz){
		return ids.get(clazz.getName());
	}
	public static void setId(Class clazz,List<String> id){
		ids.put(clazz.getName(),id);
	}
}
