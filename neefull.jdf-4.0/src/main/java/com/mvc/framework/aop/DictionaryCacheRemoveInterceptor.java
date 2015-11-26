package com.mvc.framework.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.mvc.framework.cache.CacheService;

public class DictionaryCacheRemoveInterceptor extends MethodCacheRemoveInterceptor {
	private static final Logger LOGGER = Logger.getLogger(DictionaryCacheRemoveInterceptor.class);
	private CacheService cacheService;
	/**
	 * Need flush method
	 */
	private static final String[] CACHED_METHOD = new String[] { "getChildrenByParentId", "getChildrenByRootId",
								"getDictionariesByDictionaryId","getDirectChildrenByParentId"
								,"getDictionariesByDictionaryIdOrderByNameAsc","getDictionariesByDictionaryIdOrderByNameDesc"};

	public void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
		String targetName = target.getClass().getName();
		for (String cachedMethod : CACHED_METHOD) {
			String cacheKey = KeyGenerator.getCacheKey(targetName, cachedMethod, arguments);
			cacheService.delete(cacheKey);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("remove key:" + cacheKey);
			}
		}
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
}
