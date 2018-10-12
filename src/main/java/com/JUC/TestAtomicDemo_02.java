package com.JUC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/*
 * 一、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 * 		  int i = 10;
 * 		  i = i++; //10
 *
 * 		  int temp = i;
 * 		  i = i + 1;
 * 		  i = temp;
 *
 * 二、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 * 		1. volatile 保证内存可见性
 * 		2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 * 			CAS 算法是硬件对于并发操作的支持
 * 			CAS 包含了三个操作数：
 * 			①内存值  V
 * 			②预估值  A
 * 			③更新值  B
 * 			当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 */
public class TestAtomicDemo_02 {
private static Logger logger= LoggerFactory.getLogger(TestAtomicDemo_02.class);

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();

        ExecutorService executorService = Executors.newCachedThreadPool();
        // 创建10个并发线程（10个线程在实际并发上只要有微小时间差）
        for (int i = 0; i < 10; i++) {
            Future<?> future = executorService.submit(new AtomicDemo());
            try {
                future.get();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
                logger.error("InterruptedException错误信息："+e.getMessage(), e);
            } catch (ExecutionException e) {
                logger.error(e.getMessage(),e);
                logger.error("ExecutionException错误信息："+e.getMessage(), e);
            }
        }
        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("serialNumber="+ad.getSerialNumber());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }

    }

    private static volatile int serialNumber = 0;
    // 共享数据用Aotomic类型：保证“原子性”
    //private AtomicInteger serialNumber = new AtomicInteger(0);
    static class AtomicDemo implements Runnable {



        public void run() {
            try {
                Thread.sleep(200);
                int a=1/0;
            } catch (InterruptedException e) {
            }

            System.out.println(getSerialNumber());
        }

        public  int getSerialNumber() {
//		return serialNumber.getAndIncrement();
            return serialNumber++;
        }
    }

}

