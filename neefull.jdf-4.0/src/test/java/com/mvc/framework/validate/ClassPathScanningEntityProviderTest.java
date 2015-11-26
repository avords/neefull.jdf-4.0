package com.mvc.framework.validate;

import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class ClassPathScanningEntityProviderTest extends TestCase {
	@Test
	public void testScan(){
		ClassPathScanningEntityProvider classPathScanningEntityProvider = new ClassPathScanningEntityProvider(true);
		Set result = classPathScanningEntityProvider.findCandidateComponents("com.mvc");
		Assert.assertEquals(true, result.size()>0);
	}
}
