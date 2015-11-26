package com.mvc.framework.cache;

import java.util.Date;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

public class EhcacheAdaptor implements CacheService {
	private static final Logger LOGGER = Logger.getLogger(EhcacheAdaptor.class);
	private Cache ehcache;

	public boolean add(String key, Object value) {
		Element element = new Element(key, value);
		try {
			ehcache.put(element);
			return true;
		} catch (Exception e) {
			LOGGER.error("add",e);
		}
		return false;
	}

	public boolean add(String key, Object value, Date expiry) {
		return add(key, value);
	}

	public boolean delete(String key) {
		return ehcache.remove(key);
	}

	public Object get(String key) {
		Element element = ehcache.get(key);
		return null==element?null:element.getValue();
	}

	public boolean keyExists(String key) {
		return ehcache.isElementInMemory(key);
	}

	public boolean replace(String key, Object value) {
		ehcache.remove(key);
		return add(key, value);
	}

	public boolean replace(String key, Object value, Date expiry) {
		return replace(key, value);
	}

	public boolean set(String key, Object value) {
		return add(key, value);
	}

	public boolean set(String key, Object value, Date expiry) {
		return add(key, value);
	}

	public Cache getEhcache() {
    	return ehcache;
    }

	public void setEhcache(Cache ehcache) {
    	this.ehcache = ehcache;
    }

}
