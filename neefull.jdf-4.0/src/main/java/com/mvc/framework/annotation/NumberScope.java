package com.mvc.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Number validation annotation
 * @author pubx 2010-4-6 下午05:42:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumberScope {
	int minValue() default Integer.MIN_VALUE;
	int maxValue() default Integer.MAX_VALUE;
}
