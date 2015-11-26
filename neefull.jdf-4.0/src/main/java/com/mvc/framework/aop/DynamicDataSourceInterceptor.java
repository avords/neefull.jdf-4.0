package com.mvc.framework.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.mvc.framework.dao.DataSourceSwitcher;

public class DynamicDataSourceInterceptor implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
	private static final Logger LOGGER = Logger.getLogger(DynamicDataSourceInterceptor.class);

	public void before(Method method, Object[] args, Object target) throws Throwable {
		if (method.getName().startsWith("save") || method.getName().startsWith("update")
				|| method.getName().startsWith("create") || method.getName().startsWith("delete")
				|| method.getName().startsWith("run") || method.getName().startsWith("batch")
				|| method.getName().startsWith("execute")) {
			DataSourceSwitcher.setMaster();
		} else {
			DataSourceSwitcher.setSlave();
		}
	}

	public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {
	}

	// 抛出Exception之后被调用
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DataSourceSwitcher.setSlave();
	}

}
