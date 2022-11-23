package com.example.testutility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testutility.service.FooService;

@RestController
@RequestMapping("autowired")
public class FooInjectionAutowiredController {
	@Autowired
	private FooService fooService;
	
	@GetMapping("foo-string")
	public String getFooString(){
		return fooService.getFooString();
	}
}
