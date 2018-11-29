package com.utils.JUC.itcase.queue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class Test {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(1);
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();
		for(int i=0;i<10;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {	
					try {
						semaphore.acquire();
						String input = queue.take();
						String output = TestDo.doSome(input);
						System.out.println(Thread.currentThread().getName()+ ":" + output);
						semaphore.release();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}).start();
		}
		
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		for(int i=0;i<10;i++){  //���в��ܸĶ�
			String input = i+"";  //���в��ܸĶ�
			try {
				queue.put(input);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//���ܸĶ���TestDo��
class TestDo {
	public static String doSome(String input){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String output = input + ":"+ (System.currentTimeMillis() / 1000);
		return output;
	}
}
