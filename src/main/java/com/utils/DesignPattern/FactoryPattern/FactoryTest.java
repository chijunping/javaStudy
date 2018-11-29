package com.utils.DesignPattern.FactoryPattern;

import org.junit.Test;

/**
 * 工厂测试类
 * 
 * 总结：凡是出现了大量的产品需要创建，并且具有共同的接口时，可以通过工厂方法模式进行创建。在以上的三种模式中，
 * 第一种如果传入的字符串有误，不能正确创建对象， 第三种相对于第二种，不需要实例化工厂类，所以，大多数情况下，
 * ###：我们会选用第三种——静态工厂方法模式。
 * 
 * 应用举例：
 *  java.util.Calendar - getInstance()
	java.util.Calendar - getInstance(TimeZone zone)
	java.util.Calendar - getInstance(Locale aLocale)
	java.util.Calendar - getInstance(TimeZone zone, Locale aLocale)
	java.text.NumberFormat - getInstance()
	java.text.NumberFormat - getInstance(Locale inLocale)
 */
public class FactoryTest {

	// 测试普通工厂方法
	public static void main(String[] args) {
		HumanFactory factory = new HumanFactory();
		// 工厂类根据入参决定为Human接口创建何种实现类对象
		Human male = factory.createHuman("male");
		male.eat();
		male.sleep();
		male.beat();
	}

	// 测试多个工厂方法
	@Test
	public void testMainyFactoryMethods() {
		HumanFactory factory = new HumanFactory();
		Human male = factory.createMale();
		male.eat();
		male.sleep();
		male.beat();
	}

	// 测试静态工厂方法(多数情况下使用静态工厂)
	@Test
	public void testStaticFactoryMethods() {
		Human male = HumanFactory.createMale1();
		male.eat();
		male.sleep();
		male.beat();
	}
}
