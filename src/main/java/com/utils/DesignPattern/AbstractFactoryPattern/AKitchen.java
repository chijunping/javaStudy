package com.utils.DesignPattern.AbstractFactoryPattern;

// 以具体工厂 AKitchen 为例
class AKitchen implements KitchenFactory {

	public Food getFood() {
		return new Apple();
	}

	public TableWare getTableWare() {
		return new Knife();
	}
}