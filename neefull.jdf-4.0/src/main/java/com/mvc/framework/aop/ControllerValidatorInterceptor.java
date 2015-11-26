package com.mvc.framework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.beanvalidator.BeanValidators;

@Component
@Aspect
public class ControllerValidatorInterceptor {
	private static final Logger LOGGER = Logger.getLogger(ControllerValidatorInterceptor.class);
	@Autowired
	private Validator validator;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	private void controllerInvocation() {
	}

	@Around("controllerInvocation()")
	public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		// Annotation[] annotationList = method.getAnnotations();
		/*
		 * for(Annotation anno:annotationList){
		 * System.out.println(ResponseBody.class.isInstance(anno)); }
		 */
		Annotation[][] argAnnotations = method.getParameterAnnotations();
		// String[] argNames = methodSignature.getParameterNames();
		Object[] args = joinPoint.getArgs();

		for (int i = 0; i < args.length; i++) {
			if (hasValidAnnotations(argAnnotations[i])) {
				BeanValidators.validateWithException(validator, args[i]);
			}
		}
		return joinPoint.proceed(args);
	}

	private boolean hasValidAnnotations(Annotation[] annotations) {
		if (annotations != null && annotations.length > 0) {
			for (Annotation annotation : annotations) {
				if (Valid.class.isInstance(annotation)) {
					return true;
				}
			}
		}
		return false;
	}

	private String validateArg(Object arg, String argName) {
		try {

		} catch (ConstraintViolationException e) {
			LOGGER.error("validateArg", e);
			return StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
		}
		return null;
	}

}
