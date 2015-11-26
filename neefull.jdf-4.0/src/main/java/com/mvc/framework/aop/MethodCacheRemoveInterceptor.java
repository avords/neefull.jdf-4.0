package com.mvc.framework.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.mvc.framework.cache.CacheService;

public class MethodCacheRemoveInterceptor implements AfterReturningAdvice {
	private CacheService cacheService;
	/**
	 * Need flush method
	 */
	private static final String[] CACHED_METHOD = new String[] {"get", "load" };

	public void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
		String targetName = target.getClass().getName();
		for (String cachedMethod : CACHED_METHOD ) {
			String cacheKey = KeyGenerator.getCacheKey(targetName, cachedMethod, arguments);
			cacheService.delete(cacheKey);
		}
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
}
