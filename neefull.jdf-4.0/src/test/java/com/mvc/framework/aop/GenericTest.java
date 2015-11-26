package com.mvc.framework.aop;

import org.junit.Ignore;

@Ignore
public class GenericTest {
	public static void main(String[] args) {
		Test<String> test = new GenericTest.Test();
		System.out.println(test.get());
	}

	static class Test<T> {
		public String get(){
			return getClass().getCanonicalName();
		}
	}
}
