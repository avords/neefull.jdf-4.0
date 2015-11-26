package com.mvc.framework.util;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;


public final class CronExpressionUtils {
	private static final Logger LOGGER = Logger.getLogger(CronExpressionUtils.class);
	private CronExpressionUtils(){
	}

	public static CronExpression getValidCronExpression(String expression){
		CronExpression cronExpression = null;
		if(CronExpression.isValidExpression(expression)){
			try{
				cronExpression = new CronExpression(expression);
			}catch (ParseException e) {
				LOGGER.error("getValidCronExpression",e);
			}
		}
		return cronExpression;
	}
}
