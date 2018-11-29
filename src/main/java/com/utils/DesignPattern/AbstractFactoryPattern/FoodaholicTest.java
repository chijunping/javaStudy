package com.utils.DesignPattern.AbstractFactoryPattern;



/**
 * 抽象工厂模式：在普通工厂 模式前提下设置工厂类为接口，为其提供实现类
 * 2.5 工厂方法模式、抽象工厂模式区别

	工厂方法模式、抽象工厂模式，傻傻分不清楚...
	为了解释得更清楚，先介绍两个概念：
		1.产品等级结构：比如一个抽象类是食物，其子类有苹果、牛奶等等，则抽象食物与具体食物名称之间构成了一个产品等级结构。食物是抽象的父类，而具体的食物名称是其子类。
		2.产品族：在抽象工厂模式中，产品族是指由同一个工厂生产的，位于不同产品等级结构中的一组产品。如 AKitchen 生产的苹果、刀子，苹果属于食物产品等级结构中，
		而刀子则属于餐具产品等级结构中。而 BKitchen 可能生成另一组产品，如牛奶、杯子。
	因此工厂方法模式、抽象工厂模式最大的区别在于：
	工厂方法模式：针对的是 一个产品等级结构。
	抽象工厂模式：针对 多个产品等级结构。
	吃货们，懂了吧？
 */
// 吃货要开吃了
public class FoodaholicTest {

	public void eat(KitchenFactory k) {
		System.out.println("A foodaholic is eating " + k.getFood().getFoodName() + " with " + k.getTableWare().getToolName());
	}

	public static void main(String[] args) {
		FoodaholicTest fh = new FoodaholicTest();
		KitchenFactory kf = new AKitchen();
		fh.eat(kf);
	}
}