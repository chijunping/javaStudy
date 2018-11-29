package com.utils.DesignPattern.FactoryPattern;

/**
 * 女人实现类
 * 
 * @ClassName: Female
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年6月1日 下午3:46:23
 */
public class Female implements Human {

	@Override
	public void eat() {
		System.out.println("女人，吃饭。。。");

	}

	@Override
	public void sleep() {
		System.out.println("女人，睡觉。。。");

	}

	@Override
	public void beat() {
		System.out.println("女人，打豆豆。。。");

	}

}
