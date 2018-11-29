package com.utils.BeanUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class BeanUtils {
	
	
	
	public static void main(String[] args) throws Exception {
		Integer i1=1000;
		Integer i2=1000;
		System.out.println(i1==i2);
		Integer i3=1000;
		Integer i4=1000;
		System.out.println(i3==i4);
	}

	static class MyClone {
		private String name;
		private String age;
		
		
		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getAge() {
			return age;
		}


		public void setAge(String age) {
			this.age = age;
		}


		public MyClone() {
		}


		public MyClone(String name, String age) {
			this.name = name;
			this.age = age;
		}


		public static <T> T clone(T obj) throws Exception {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bout);
			oos.writeObject(obj);

			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bin);
			return (T) ois.readObject();

			// 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
			// 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
		}
	}
}
