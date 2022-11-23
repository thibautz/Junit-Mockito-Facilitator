package com.example.testutility.facilitator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTest<T> {
	
	public Map<Class, Object> mocks = new HashMap<>();
	
	@InjectMocks
	public T test;
	
	@BeforeEach
	public void setUpInjectMock() throws Exception {
		Class<T> clazz = getTypeParameterClass();
		
		List<Constructor> constructors = Arrays.asList(clazz.getDeclaredConstructors());
		Constructor constructorWithLongestParameter = Collections.max(constructors,
				(o1, o2) -> Integer.compare(o1.getParameterCount(), o2.getParameterCount()));
		
		// case without spring constructor
		if (constructorWithLongestParameter.getParameterTypes().length == 0)
		{
			test = Mockito.spy(getTypeParameterClass());
			MockitoAnnotations.openMocks(this);
		}
		else
		{
			this.injectMockForSpringConstructor(constructorWithLongestParameter, clazz);
		}
	}
	
	private void injectMockForSpringConstructor(Constructor constructorWithLongestParameter, Class<T> clazz) throws IllegalAccessException {
		List<Object> objects = new ArrayList<>();
		
		//Get all fields
		Field[] declaredFields = this.getClass().getDeclaredFields();
		//To improve : create MultiKeymap with keys : class & name
		Map<Class<?>, Field> map = Stream.of(declaredFields).filter(f -> !f.getType().isPrimitive() && !Modifier.isStatic(f.getModifiers()))
				.collect(Collectors.toMap(Field::getType, Function.identity()));
		for (Class<?> type : constructorWithLongestParameter.getParameterTypes())
		{
			getMocks(objects, map, type);
		}
		try
		{
			test = clazz.cast(constructorWithLongestParameter.newInstance(objects.toArray(new Object[objects.size()])));
			test = Mockito.spy(test);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private void getMocks(List<Object> objects, Map<Class<?>, Field> map, Class<?> type) throws IllegalAccessException {
		Object mock = mocks.get(type);
		if (mock == null)
		{
			Field field = map.get(type);
			field.setAccessible(true);
			
			mock = field.get(this);
			mocks.put(type, field.get(this));
		}
		objects.add(mock);
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getTypeParameterClass() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<T>) paramType.getActualTypeArguments()[0];
	}
}