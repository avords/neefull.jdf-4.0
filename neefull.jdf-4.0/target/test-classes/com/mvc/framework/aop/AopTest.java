package com.mvc.framework.aop;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Ignore;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import com.mvc.framework.model.Dictionary;
import com.mvc.framework.service.DictionaryFacade;
@Ignore
public class AopTest {
	
	public void testAop() throws Exception {
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression("execution(* com.mvc.framework.service.DictionaryManager.*(..))");
		Class targetClass = DictionaryFacade.class;
		Method method = targetClass.getMethod("save",Dictionary.class);
		Assert.assertTrue(aspectJExpressionPointcut.matches(method, targetClass, null));
	}

	public static void main(String[] args) throws Exception {
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression("execution(* com.pubx.news.business.content.service.ArticleManager.get(..))");
		Class targetClass = Dictionary.class;
		Method method = targetClass.getMethod("get",Long.class);
		Assert.assertTrue(aspectJExpressionPointcut.matches(method, targetClass, null));
	}
}
