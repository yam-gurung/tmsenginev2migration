package com.tms;

import static org.testng.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class FooTest {

	@Test
	public void getBar() {
		Foo foo=new Foo();
		String result=foo.getBar();
		
		assertEquals("Bar",result);
	}

}
