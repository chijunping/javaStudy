package com.utils.JUC.atguigu.juc1;

/*
 * 一、volatile 关键字：1.当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 					2.相较于 synchronized 是一种较为轻量级的同步策略。
 * 
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile_01 {

	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();

		while (true) {
			if (td.isFlag()) {
				// 如果主线程调用子线程的isFlag()方法结果为true，说明主线程看了子线程对数据的修改结果，否则说明两个线程之间对数据的修改不可见
				System.out.println("主线程看到了子线程对数据的修改：------------------");
				break;
			}
		}
	}

}

// 当主线程需要用到子线程的共享数据时，需要用此创建子线程实体类的方式；或者用future携带返回值
class ThreadDemo implements Runnable {

	// 给共享数据加上“volatile”修饰——解决多线程数据的内存可见性
	private volatile boolean flag = false;

	@Override
	public void run() {

		try {
			// 放大问题
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		flag = true;

		System.out.println("子线程已经修改：flag=" + isFlag());

	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}