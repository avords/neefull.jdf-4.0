package com.mvc.framework.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.mvc.framework.cache.CacheService;

public class MethodCacheInterceptor implements MethodInterceptor {
	
	private CacheService cacheService;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		String cacheKey = KeyGenerator.getCacheKey(targetName, methodName, arguments);
		Object result = cacheService.get(cacheKey);
		if (null==result) {
			result = invocation.proceed();
			cacheService.add(cacheKey, result, null);
		}
		return result;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

}
