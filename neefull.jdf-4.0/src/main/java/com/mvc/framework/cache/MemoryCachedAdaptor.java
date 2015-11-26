package com.mvc.framework.cache;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.danga.MemCached.MemCachedClient;
@Component("memoryCachedAdaptor")
public class MemoryCachedAdaptor implements CacheService {
	private MemCachedClient memCacheClient;

	public boolean add(String key, Object value) {
		return memCacheClient.add(key, value);
	}

	public boolean add(String key, Object value, Date expiry) {
		return memCacheClient.add(key, value, expiry);
	}

	public boolean delete(String key) {
		return memCacheClient.delete(key);
	}

	public Object get(String key) {
		return memCacheClient.get(key);
	}

	public boolean keyExists(String key) {
		return memCacheClient.keyExists(key);
	}

	public boolean replace(String key, Object value) {
		return memCacheClient.replace(key, value);
	}

	public boolean replace(String key, Object value, Date expiry) {
		return memCacheClient.replace(key, value, expiry);
	}

	public boolean set(String key, Object value) {
		return memCacheClient.set(key, value);
	}

	public boolean set(String key, Object value, Date expiry) {
		return memCacheClient.set(key, value, expiry);
	}

	public void setCacheProvider(MemCachedClient cacheProvider) {
		this.memCacheClient = cacheProvider;
	}

	public MemCachedClient getMemCacheClient() {
    	return memCacheClient;
    }

	public void setMemCacheClient(MemCachedClient memCacheClient) {
    	this.memCacheClient = memCacheClient;
    }
}
