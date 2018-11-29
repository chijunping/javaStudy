package com.utils.itcast_04_reflect.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;

public class TestServerTask implements Runnable{
	private Socket socket;
	public TestServerTask(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		InputStream in;
		OutputStream out;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String request = br.readLine();
			String[] split = request.split(":");
			String className = split[0];
			String methodName = split[1];
			String methodParam= split[2];
			//解析socket输入流，根据读出的类名反射出类
			Class<?> forName = Class.forName(className);
			System.out.println("解析socket输入流，根据读出的类名反射出类: " + forName);
			//根据反射得到的类，获取一个该类的实例
			Object newInstance = forName.newInstance();
			//根据反射得到的类，和socket输入流中解析得到的方法名，获取该类实例的指定方法对象
			Method method = forName.getMethod(methodName,String.class);
			System.out.println("calling method: " + method);
			//执行指定对象的指定方法，并传入参数
			Object invoke = method.invoke(newInstance, methodParam);
			System.out.println("results: " + (int)invoke);
			
			//往socket输出流中存入数据（socket的返回通知）：根据socket输入流数据执行指定类的对象的指定方法后的返回值
			PrintWriter pw = new PrintWriter(new BufferedOutputStream(out));
			pw.println((int)invoke);
			pw.flush();
			
			br.close();
			pw.close();
			socket.close();
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
	}

}
