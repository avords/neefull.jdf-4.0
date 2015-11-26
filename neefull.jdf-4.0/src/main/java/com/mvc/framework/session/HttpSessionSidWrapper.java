package com.mvc.framework.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mvc.framework.cache.CacheService;

public class HttpSessionSidWrapper extends HttpSessionWrapper {

	private String sid = "";
	private CacheService cacheService;

	private Map map = null;

	public HttpSessionSidWrapper(String sid, HttpSession session, CacheService cacheService) {
		super(session);
		this.sid = sid;
		this.cacheService = cacheService;
		map = (Map) cacheService.get(sid);
		if (map == null) {
			map = new HashMap();
		}
		cacheService.add(sid, map);
	}

	public Object getAttribute(String arg0) {
		return this.map.get(arg0);
	}

	public String getId() {
		return sid;
	}

	public Enumeration getAttributeNames() {
		return (new Enumerator(this.map.keySet(), true));
	}

	public void invalidate() {
		this.map.clear();
		cacheService.delete(this.sid);
	}

	public void removeAttribute(String arg0) {
		this.map.remove(arg0);
		cacheService.replace(this.sid, this.map);
	}

	@SuppressWarnings("unchecked")
	public void setAttribute(String arg0, Object arg1) {
		this.map.put(arg0, arg1);
		cacheService.replace(this.sid, this.map);
	}

}
