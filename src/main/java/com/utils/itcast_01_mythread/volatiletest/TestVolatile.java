package com.utils.itcast_01_mythread.volatiletest;

public class TestVolatile {

	public static volatile int numb = 0;

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						numb++;
					}
				}
			}).start();

		}

		Thread.sleep(2000);
		/*
		 * 理想值是100000，但volatile只保证共享元素的可见性，
		 * 不保证元素的原子性和排斥性，所以并发量大的时候难免同时操作统一元素导致某些线程拿到的数据非最新， 最终结果会小于期望值
		 */
		System.out.println(numb);
	}

}
