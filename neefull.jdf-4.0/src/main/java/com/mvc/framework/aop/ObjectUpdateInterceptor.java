package com.mvc.framework.aop;

import java.lang.reflect.Method;

import com.mvc.framework.cache.CacheService;
import com.mvc.framework.model.AbstractEntity;

public class ObjectUpdateInterceptor extends MethodCacheRemoveInterceptor {
	
	private CacheService cacheService;

	public void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
		String targetName = target.getClass().getName();
		if(arguments.length==1){
			if(arguments[0] instanceof AbstractEntity || arguments[0] instanceof Long){
				String cacheKey = KeyGenerator.getCacheKey(targetName, "getByObjectId", arguments);
				//Delete directly, because the transit property's load is not called automatically so can not override it.
				cacheService.delete(cacheKey);
			}
		}
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
}
