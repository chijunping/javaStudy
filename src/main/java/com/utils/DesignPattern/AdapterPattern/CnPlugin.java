package com.utils.DesignPattern.AdapterPattern;

/**
 * 实现国标插座的充电方法
 */
public class CnPlugin implements CnPluginInterface {
	public void chargeWith2Pins() {
		System.out.println("实现国标插座的充电方法...");
	}
}
