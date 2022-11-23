package com.example.testutility.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testutility.service.FooService;

@RestController
@RequestMapping("constructor")
public class FooInjectionConstructorController {

	private FooService fooService;
	
	public FooInjectionConstructorController(FooService fooService) {
		this.fooService = fooService;
	}
	
	@GetMapping("foo-string")
	public String getFooString(){
		return fooService.getFooString();
	}
}
