package com.mvc.framework.util;

import java.util.Date;

import org.junit.Ignore;
import org.quartz.CronExpression;
@Ignore
public class CronExpressionUtilsTest{
	public void main() {
		CronExpression cronExpression = CronExpressionUtils.getValidCronExpression("* 51 * * * ?");
		System.out.println(cronExpression.isSatisfiedBy(new Date()));
	}

}
