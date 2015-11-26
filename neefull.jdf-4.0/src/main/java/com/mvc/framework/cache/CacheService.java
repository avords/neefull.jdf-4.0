package com.mvc.framework.cache;

import java.util.Date;

/**
 * application cache
 */
public interface CacheService {

	boolean add(String key, Object value);

	boolean add(String key, Object value, Date expiry);

	boolean delete(String key);

	Object get(String key);

	boolean replace(String key, Object value);

	boolean replace(String key, Object value, Date expiry);

	boolean set(String key, Object value);

	boolean set(String key, Object value, Date expiry);

	boolean keyExists(String key);
}
