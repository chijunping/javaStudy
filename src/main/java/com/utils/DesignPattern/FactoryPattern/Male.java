package com.utils.DesignPattern.FactoryPattern;

/**
 * 男人实现类
 * 
 * @ClassName: Male
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年6月1日 下午3:46:10
 */
public class Male implements Human {

	@Override
	public void eat() {
		System.out.println("男人，吃饭。。。");

	}

	@Override
	public void sleep() {
		System.out.println("男人，睡觉。。。");

	}

	@Override
	public void beat() {
		System.out.println("男人，打豆豆。。。");

	}

}
