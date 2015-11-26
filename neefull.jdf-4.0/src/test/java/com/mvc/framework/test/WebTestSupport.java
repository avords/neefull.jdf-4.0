package com.mvc.framework.test;

import org.junit.Ignore;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@Ignore
public class WebTestSupport {

	public final MockHttpServletRequest getRequest() {
		return new MockHttpServletRequest("GET", "");
	}

	public final MockHttpServletResponse getResponse() {
		return new MockHttpServletResponse();
	}
}
