package com.utils.JUC.atguigu.juc1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 
 *该程序用来模拟发送命令与执行命令，主线程代表指挥官，新建3个线程代表战士，
 * 1.战士一直等待着指挥官下达命令，
 * 2.若指挥官没有下达命令，则战士们都必须等待。
 * 3.一旦命令下达，战士们都去执行自己的任务，指挥官处于等待状态，
 * 4.战士们任务执行完毕则报告给指挥官，指挥官则结束等待。
 */
public class TestCountdownLatch02_05 {

	public static void main(String[] args) {
		// 创建一个线程池
		ExecutorService service = Executors.newCachedThreadPool();
		// 指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务
		final CountDownLatch cdOrder = new CountDownLatch(1);
		// 因为有3个战士，所以初始值为3，每一个战士执行任务完毕则cutDown 1次，当3个都执行完毕，变为0，则指挥官停止等待。
		final CountDownLatch cdAnswer = new CountDownLatch(3);

		//并发线程创建时间
	long  begin=System.currentTimeMillis();
		
		// 创建并发线程=========begin==========
		for (int i = 0; i < 3; i++) {
			//方式1：将任务交给线程池
			/*Runnable runnable = new Runnable() {
				public void run() {
					try {
						System.out.println("线程" + Thread.currentThread().getName() + "：战士正准备接受指挥官命令");
						// 战士们都处于等待命令状态
						cdOrder.await();
						System.out.println("线程" + Thread.currentThread().getName() + "：战士已接受指挥官命令");
						// 随机休眠时间
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + "：战士执行命令并向指挥官回应命令的执行结果");
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						// 任务执行完毕，返回给指挥官，cdAnswer减1。
						cdAnswer.countDown();
					}
				}
			};
			// 该线程不自己调用.start()方法执行线程run()方法，而是将自己添加为线程池任务
			service.execute(runnable);*/
			//方式2：当前线程自己调用start()方法执行run()方法
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println("线程" + Thread.currentThread().getName() + "：战士正准备接受指挥官命令");
						//三个战士（线程）同步执行到这里，一起等待命令CountDownLatch值为降0时时，再同步往下执行
						System.out.println("cdOrder等待前的值："+cdOrder.getCount());
						cdOrder.await();
						System.out.println("cdOrder停止等待时的值："+cdOrder.getCount());
						System.out.println("线程" + Thread.currentThread().getName() + "：战士已接受指挥官命令");
						// 随机休眠时间
						Thread.sleep((long) (Math.random() * 1000));
						System.out.println("线程" + Thread.currentThread().getName() + "：战士执行命令并向指挥官回应命令的执行结果");
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						// 每个战士（线程）任务执行完毕时，返回给指挥官，cdAnswer减1，三个线程都执行完后，cdAnswer的值为0，则指挥官收到“继续执行”命令。
						cdAnswer.countDown();
					}
				}
			}).start();
		}
		// 创建并发线程=========end==========
		
		try {
			Thread.sleep((long) (Math.random() * 10000));

			System.out.println("线程" + Thread.currentThread().getName() + "：指挥官即将发布命令");
			cdOrder.countDown(); // 发送命令，cdOrder减1，处于等待的战士们停止等待转去执行任务。
			System.out.println("线程" + Thread.currentThread().getName() + "：指挥官已发送命令，正在等待结果");
			
			// 命令发送后指挥官处于等待状态，一旦cdAnswer为0时停止等待继续往下执行
			
			System.out.println("cdAnswer等待前的值："+cdAnswer.getCount());
			cdAnswer.await(); 
			System.out.println("cdAnswer停止等待时的值："+cdAnswer.getCount());
			System.out.println("线程" + Thread.currentThread().getName() + "：指挥官已收到所有来自战士的响应结果");
			System.out.println("统计：并发线程执行命令总耗时为："+(System.currentTimeMillis()-begin));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			// 任务结束，停止线程池的所有线程
			service.shutdown(); 
		}

	}
}