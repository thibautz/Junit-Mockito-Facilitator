package com.example.testutility.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.testutility.facilitator.AbstractTest;

class FooServiceTest extends AbstractTest<FooService> {
	
	@Test
	void getFooString(){
		assertEquals("Foo",this.test.getFooString());
	}
}
