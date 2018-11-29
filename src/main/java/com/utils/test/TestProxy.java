package com.utils.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy {

	public static void main(String[] args) {
		Proxy.newProxyInstance(MyPerson.class.getClassLoader(),MyPerson.class.getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
