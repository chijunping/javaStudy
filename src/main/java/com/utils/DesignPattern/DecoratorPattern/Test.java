package com.utils.DesignPattern.DecoratorPattern;

/**
 * 装饰者模式 
 * 1.应用： 当你需要动态地给一个对象添加功能，实现功能扩展的时候，就可以使用装饰者模式。
 * 当你需要动态地给一个对象添加功能，实现功能扩展的时候，就可以使用装饰者模式。
 * Java IO 类中有一个经典的装饰者模式应用， BufferedReader 装饰了 InputStreamReader. BufferedReader
 * input = new BufferedReader(new InputStreamReader(System.in));
 * InputStreamReader(InputStream in) - InputSteamReader 读取 bytes 字节内容，然后转换成
 * characters 流 输出。 BufferedReader(Reader in) - 从 characters 流 中读取内容并缓存。
 * 
 * 2. 装饰者模式、适配器模式区别：
 * 关于新职责：适配器也可以在转换时增加新的职责，但其主要目的并不在此；而装饰者模式主要目的，就是给被装饰者增加新职责用的。
 * 关于原接口：适配器模式是用新接口来调用原接口
 * ，原接口对新系统来说是不可见或者说不可用的；而装饰者模式原封不动的使用原接口，系统对装饰的对象也通过原接口来完成使用。
 * 关于其包裹的对象：适配器是知道被适配者的详细情况的
 * （就是那个类或那个接口）；而装饰者只知道其接口是什么，至于其具体类型（是基类还是其他派生类）只有在运行期间才知道。
 */
public class Test {

	public static void main(String[] args) {
		Girl g1 = new AmericanGirl();
		System.out.println(g1.getDescription());

		GoldenHair g2 = new GoldenHair(g1);
		System.out.println(g2.getDescription());

		Tall g3 = new Tall(g2);
		System.out.println(g3.getDescription());

		// 你也可以一步到位
		// Girl g = new Tall(new GoldenHair(new AmericanGirl()));
	}
}