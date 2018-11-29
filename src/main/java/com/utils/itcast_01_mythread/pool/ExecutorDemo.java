package com.utils.itcast_01_mythread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 列出并发包中的各种线程池
 * @author
 *
 */

public class ExecutorDemo {
	
	public static void main(String[] args) {
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		//返回可用的java虚拟机的处理器数。
		int cpuNums = Runtime.getRuntime().availableProcessors();
		System.out.println(cpuNums);
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(cpuNums);
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(8);
		
		
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	}
}
