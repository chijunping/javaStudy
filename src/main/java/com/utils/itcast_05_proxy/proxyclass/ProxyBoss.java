package com.utils.itcast_05_proxy.proxyclass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBoss {
	/**
	 * 对接口方法进行代理
	 * 
	 * @param discountCoupon
	 *            优惠金额
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<?> interfaceClass, final Class<?> implementsClass) throws Exception {

		/*
		 * 获得代理对象的逻辑代码：
		 */
		Object newProxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass }, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// 如果调用的方法名为：yifu(String size)时，可以在这里扩展该方法（这里的扩展：对该方法原来的结果做减法）
				if (method.getName() == "yifu") {
					Integer returnValue = (Integer) method.invoke(implementsClass.newInstance(), args);// 调用原始对象以后返回的值
					return returnValue - 10;// 减少售价：-10
				}
				// 如果调用的方法名为：kuzi()时，可以在这里扩展该方法（这里的扩展：对该方法原来的结果做加法）
				else if (method.getName() == "kuzi") {
					Integer returnValue = (Integer) method.invoke(implementsClass.newInstance(), args);// 调用原始对象以后返回的值
					return returnValue + 10;// 提高售价：+10
				}
				// 其他方法按原来逻辑执行
				else {
					Object invoke = method.invoke(implementsClass.newInstance(), args);
					return invoke;
				}
			}
		});

		return (T) newProxyInstance;
	}
	
	
	public static <T> T getProxyInstance(Class<?> interFaceClazz,final Class<?> implClazz){
		
		Object newProxyInstance = Proxy.newProxyInstance(interFaceClazz.getClassLoader(),new Class<?>[]{interFaceClazz},new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object invoke = method.invoke(implClazz, args);
				return null;
			}
		});
		return null;
	}
}
