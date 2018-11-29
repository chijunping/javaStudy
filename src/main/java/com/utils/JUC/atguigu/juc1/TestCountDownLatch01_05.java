package com.utils.JUC.atguigu.juc1;

import java.util.concurrent.CountDownLatch;

/*
 * CountDownLatch ：闭锁（倒计时门闩），在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch01_05 {

	public static void main(String[] args) {
		// 定义一个闭锁计数器，初始值为子线程数50
		final CountDownLatch latch = new CountDownLatch(50);

		// 线程开始并发执行的时间
		long start = System.currentTimeMillis();

		// 50个并发子线程在慢慢执行
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						for (int i = 0; i < 5000; i++) {
							// 打印50000内能被2整除的数
							if (i % 2 == 0) {
								System.out.println(i);
							}
						}
					} finally {
						// 倒计时--
						latch.countDown();
					}
				}
			}).start();
		}

		// 一般，主线程不是等待上述子线程组的for循环结束后再执行for循环外的代码，它会同时继续执行，因为线程是并行的
		// 但是，这里限制了主线程继续并行执行的条件：latch.await()——乖乖等待，直到latch的内部计数器减为0时才能继续执行
		try {
			latch.await();
		} catch (InterruptedException e) {
		}

		// 线程结束并发执行的时间
		long end = System.currentTimeMillis();

		System.out.println("耗费时间为：" + (end - start));
	}

}
