# Junit Mockito Facilitor
## Description 
Class "AbtractTest" written to standardize the implementation of Mockito and Junit in a project with Spring Boot.
Example show with Spring Boot and compatible with any type of injection (by constructor or by Autowired).

Java classes: 
- FooInjectionAutowiredController.java
- FooFooInjectionConstructorController.java

Test  classes : 
- FooInjectionAutowiredControllerTest.java
- FooFooInjectionConstructorControllerTest.java

Falicilator class (package test): 
- AbstractTest

## How to use

1) In  Test class  add ```extends AbstractTest<JavaClassToTest>```.
2) Add you spring class dependency ```@Mock yourDependency```
3) Call you method to test like ``` this.test.yourMethodToTest```

Example with **FooInjectionConstructorController.java** : 
```
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
```


