package com.utils.DesignPattern.FactoryPattern;

/**
 * 创建普通工厂类
 * 
 * @ClassName: HumanFactory
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年6月1日 下午3:46:56
 */
public class HumanFactory {

	// 1.普通工厂方法
	public Human createHuman(String gender) {
		if (gender.equals("male")) {
			return new Male();
		} else if (gender.equals("female")) {
			return new Female();
		} else {
			System.out.println("请输入正确的类型！");
			return null;
		}
	}

	// 2.多个工厂方法
	public Male createMale() {
		return new Male();
	}

	public Female createFemale() {
		return new Female();
	}

	// 3.静态工厂方法
	public static Male createMale1() {
		return new Male();
	}

	public static Female createFemale1() {
		return new Female();
	}
}
