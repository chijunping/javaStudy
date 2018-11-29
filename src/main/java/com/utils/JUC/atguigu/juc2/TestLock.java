package com.utils.JUC.atguigu.juc2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 一、用于解决多线程安全问题的方式：
 * 
 * synchronized:隐式锁
 *   1. 同步代码块
 *   2. 同步方法
 * 
 * jdk 1.5 后：
 *   3. 同步锁 Lock
 * 注意：Lock是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class TestLock {

	public static void main(String[] args) {
		Ticket ticket = new Ticket();

		final Lock lock = new ReentrantLock();
		new Thread(ticket, "1号窗口").start();
		new Thread(ticket, "2号窗口").start();
		new Thread(ticket, "3号窗口").start();
	}

	// 初始余票：100张
	private int tick = 100;

	public Map<String, Object> TestLock() {
		Map<String, Object> resMap = new ConcurrentHashMap<>();

		// 创建同步锁
		Lock lock = new ReentrantLock();
		while (true) {
			lock.lock(); // 上锁
			try {
				if (tick > 0) {
					try {
						Thread.sleep(20);
						System.out.println("模拟为执行购票过程...");
						System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
						resMap.put("code", 0);
						resMap.put("msg", "购票过程正常结束");
						return resMap;
					} catch (Exception e) {
						e.printStackTrace();
						resMap.put("code", 1);
						resMap.put("msg", "购票过程异常（此处为睡眠异常）");
						return resMap;
					}
				} else {
					resMap.put("code", 1);
					resMap.put("msg", "余票不足！");
					return resMap;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();// 释放锁
			}
		}

	}

}

class Ticket implements Runnable {
	// 初始余票：100张
	private int tick = 100;
	// 创建同步锁
	private Lock lock = new ReentrantLock();

	/**
	 * 任务:卖票
	 */
	@Override
	public void run() {

		// while (true)表示：任何线程进到run()方法都会无限执行while (true){}代码块
		// 一般的：如果该代码块没有包在while (true){}内，则线程进来后只跑一次
		while (true) {

			lock.lock(); // 上锁

			try {
				if (tick > 0) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
					}

					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				} else {
					break;
				}
			} finally {
				lock.unlock(); // 释放锁
			}
		}
	}

}