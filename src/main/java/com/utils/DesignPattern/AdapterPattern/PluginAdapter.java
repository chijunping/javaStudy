package com.utils.DesignPattern.AdapterPattern;

/**
 * 适配器
 */
public class PluginAdapter implements CnPluginInterface {
	private EnPluginInterface enPlugin;

	public PluginAdapter(EnPluginInterface enPlugin) {
		this.enPlugin = enPlugin;
	}

	/**
	 * 这是重点，适配器重载了国际插座，但是内部代码实现的英标插座
	 */
	@Override
	public void chargeWith2Pins() {
		enPlugin.chargeWith3Pins();
	}
}