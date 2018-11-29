package com.utils.Reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class TestReflect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Test
	public void getQualifiedName() throws Exception {
		TestReflect testReflect = new TestReflect();
		System.out.println(testReflect.getClass().getName());
		// 结果为全类名：com.tulun.study.Reflect.TestReflect
	}

	@Test
	public void getReflectClass() throws Exception {
		Class<?> class1 = null;
		Class<?> class2 = null;
		Class<?> class3 = null;
		// 一般采用这种形式
		class1 = Class.forName("com.tulun.study.Reflect.TestReflect");
		class2 = new TestReflect().getClass();
		class3 = TestReflect.class;
		System.out.println("类名称   " + class1.getName());
		System.out.println("类名称   " + class2.getName());
		System.out.println("类名称   " + class3.getName());
	}

	@Test
	public void getSuperclassAndInterfaces() throws Exception {
		Class<?> clazz = Class.forName("com.tulun.study.Reflect.TestReflect");
		// 取得父类
		Class<?> parentClass = clazz.getSuperclass();
		System.out.println("clazz的父类为：" + parentClass.getName());
		// clazz的父类为： java.lang.Object
		// 获取clazz实现了什么接口
		Class<?> intes[] = clazz.getInterfaces();
		System.out.println("clazz实现的接口有：");
		for (int i = 0; i < intes.length; i++) {
			System.out.println((i + 1) + "：" + intes[i].getName());
		}
		// clazz实现的接口有：
		// 1：java.io.Serializable
	}

	/*
	 * 获取一个类的所有构造方法
	 */
	@Test
	public void test() throws Exception {
		Class<?> class1 = null;
		class1 = Class.forName("com.tulun.study.Reflect.User");
		// 第一种方法，实例化默认构造方法，调用set赋值
		User user = (User) class1.newInstance();
		user.setAge(20);
		user.setName("Rollen");
		System.out.println(user);
		// 结果 User [age=20, name=Rollen]
		// 第二种方法 取得全部的构造函数 使用构造函数赋值
		Constructor<?> cons[] = class1.getConstructors();
		// 查看每个构造方法需要的参数
		for (int i = 0; i < cons.length; i++) {
			Class<?> clazzs[] = cons[i].getParameterTypes();
			System.out.print("cons[" + i + "] (");
			for (int j = 0; j < clazzs.length; j++) {
				if (j == clazzs.length - 1)
					System.out.print(clazzs[j].getName());
				else
					System.out.print(clazzs[j].getName() + ",");
			}
			System.out.println(")");
		}
		// 结果
		// cons[0] (java.lang.String)
		// cons[1] (int,java.lang.String)
		// cons[2] ()
		user = (User) cons[0].newInstance("Rollen");
		System.out.println(user);
		// 结果 User [age=0, name=Rollen]
		user = (User) cons[1].newInstance(20, "Rollen");
		System.out.println(user);
		// 结果 User [age=20, name=Rollen]
	}

	/*
	 * 获取一个类的所有属性
	 */
	@Test
	public void GetAllPropertiese() throws Exception {
		Class<?> clazz = TestReflect.class;
		System.out.println("===============本类属性===============");
		// 取得本类的全部属性
		Field[] field = clazz.getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			// 权限修饰符
			int mo = field[i].getModifiers();
			String priv = Modifier.toString(mo);
			// 属性类型
			Class<?> type = field[i].getType();
			System.out.println(priv + " " + type.getName() + " " + field[i].getName() + ";");
		}

		System.out.println("==========实现的接口或者父类的属性==========");
		// 取得实现的接口或者父类的属性
		Field[] filed1 = clazz.getFields();
		for (int j = 0; j < filed1.length; j++) {
			// 权限修饰符
			int mo = filed1[j].getModifiers();
			String priv = Modifier.toString(mo);
			// 属性类型
			Class<?> type = filed1[j].getType();
			System.out.println(priv + " " + type.getName() + " " + filed1[j].getName() + ";");
		}
	}

	/*
	 * 获取一个类的所有方法
	 */
	@Test
	public void GetAllMethod() throws Exception {
		Class<?> clazz = TestReflect.class;
		Method method[] = clazz.getMethods();
		for (int i = 0; i < method.length; ++i) {
			Class<?> returnType = method[i].getReturnType();
			Class<?> para[] = method[i].getParameterTypes();
			int temp = method[i].getModifiers();
			System.out.print(Modifier.toString(temp) + " ");
			System.out.print(returnType.getName() + "  ");
			System.out.print(method[i].getName() + " ");
			System.out.print("(");
			for (int j = 0; j < para.length; ++j) {
				System.out.print(para[j].getName() + " " + "arg" + j);
				if (j < para.length - 1) {
					System.out.print(",");
				}
			}
			Class<?> exce[] = method[i].getExceptionTypes();
			if (exce.length > 0) {
				System.out.print(") throws ");
				for (int k = 0; k < exce.length; ++k) {
					System.out.print(exce[k].getName() + " ");
					if (k < exce.length - 1) {
						System.out.print(",");
					}
				}
			} else {
				System.out.print(")");
			}
			System.out.println();
		}
	}

	/*
	 * 通过反射机制调用某个类的方法
	 */
	@Test
	public void GetMethod() throws Exception {
		Class<?> clazz = TestReflect.class;
		// 调用TestReflect类中的reflect1方法
		Method method = clazz.getMethod("reflect1");
		method.invoke(clazz.newInstance());
		// Java 反射机制 - 调用某个类的方法1.
		// 调用TestReflect的reflect2方法
		method = clazz.getMethod("reflect2", int.class, String.class);
		method.invoke(clazz.newInstance(), 20, "张三");
		// Java 反射机制 - 调用某个类的方法2.
		// age -> 20. name -> 张三
	}

	public void reflect1() {
		System.out.println("Java 反射机制 - 调用某个类的方法1.");
	}

	public void reflect2(int age, String name) {
		System.out.println("Java 反射机制 - 调用某个类的方法2.");
		System.out.println("age -> " + age + ". name -> " + name);
	}

	/*
	 * 通过反射机制操作某个类的属性
	 */

	private String proprety = null;

	@Test
	public void GetProperty() throws Exception {
		Class<?> clazz = TestReflect.class;
		Object obj = clazz.newInstance();
		// 可以直接对 private 的属性赋值
		Field field = clazz.getDeclaredField("proprety");
		field.setAccessible(true);
		field.set(obj, "Java反射机制");
		System.out.println(field.get(obj));
	}

	@Test
	public void test01() throws Exception {
		// MyInvocationHandler demo = new MyInvocationHandler();
		// Subject sub = (Subject) demo.bind(new RealSubject());
		// String info = sub.say("Rollen", 20);
		// System.out.println(info);
	}

}

class User {
	private int age;
	private String name;

	public User() {
		super();
	}

	public User(String name) {
		super();
		this.name = name;
	}

	public User(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + "]";
	}
}