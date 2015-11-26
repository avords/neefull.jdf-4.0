package com.mvc.framework.util;

import junit.framework.Assert;

import org.junit.Test;
public class DomainUtilsTest {
	@Test
	public void testGetStaticDomain(){
		String domain1 = DomainUtils.getStaticDomain();
		String domain2 = DomainUtils.getStaticDomain();
		Assert.assertNotNull(DomainUtils.getStaticDomain());
		Assert.assertEquals(true, domain1.equals(domain2));
	}
}
