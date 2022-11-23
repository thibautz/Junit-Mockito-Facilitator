package com.example.testutility.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.example.testutility.facilitator.AbstractTest;
import com.example.testutility.service.FooService;

class FooInjectionConstructorControllerTest extends AbstractTest<FooInjectionConstructorController> {
	@Mock
	FooService fooService;
	
	@Test
	void getFooString(){
		when(this.fooService.getFooString()).thenReturn("Foo");
		
		assertEquals("Foo",this.test.getFooString());
		verify(this.fooService).getFooString();
	}
}
