package com.utils.DesignPattern.AdapterPattern;

/**
 * 适配器测试类
 * 
 * 应用实例：
 *  前面已经说了，当你想使用一个已有的类，但是这个类的接口跟你的又不一样，不能拿来直接用，这个时候你就需要一个适配器来帮你了，其主要作用就是在旧的接口、新的接口之间完成适配。
	比如说，做过 Android 的同学肯定写 ListView 的适配器都写吐了吧...
	适配器模式的三个特点：
	适配器对象实现原有接口
	适配器对象组合一个实现新接口的对象（这个对象也可以不实现一个接口，只是一个单纯的对象）
	对适配器原有接口方法的调用被委托给新接口的实例的特定方法
 */
public class AdapterTest {
	public static void main(String[] args) {
		EnPluginInterface enPlugin = new EnPlugin();
		Home home = new Home();
		PluginAdapter pluginAdapter = new PluginAdapter(enPlugin);
		home.setPlugin(pluginAdapter);
		// 会输出 “charge with EnPlugin”
		home.charge();
	}
}