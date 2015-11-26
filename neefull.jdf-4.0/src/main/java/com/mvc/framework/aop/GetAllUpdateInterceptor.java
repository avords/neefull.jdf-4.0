package com.mvc.framework.aop;

import java.lang.reflect.Method;

import com.mvc.framework.cache.CacheService;

public class GetAllUpdateInterceptor extends MethodCacheRemoveInterceptor {
	private CacheService cacheService;

	public void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
		String targetName = target.getClass().getName();
		String cacheKey = KeyGenerator.getCacheKey(targetName, "getAll", null);
		cacheService.delete(cacheKey);
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
}
