package com.utils.IO_File;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * 数据IO流：往文本文件写数据，读数据
 * @ClassName: DataStreamDemo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年5月25日 下午3:02:04
 */
public class DataStreamDemo {

	/**
	 * 往文本文件写文本
	 * 问题：写入后数字会出现乱码，将乱码读出来却不是乱码了
	 */
	@Test
	public  void writeStream() {
		Member[] members = { new Member("Justin", 90), new Member("momor", 95), new Member("Bush", 88) };
		try {
			DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\insert or update.txt"));

			for (Member member : members) {
				// 写入UTF字符串
				dataOutputStream.writeUTF(member.getName());
				// 写入int数据
				dataOutputStream.writeInt(member.getAge());
			}

			// 所有数据至目的地
			dataOutputStream.flush();
			// 关闭流
			dataOutputStream.close();

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readStream() throws Exception{
		Member[] members=new Member[2];
		DataInputStream dataInputStream = new DataInputStream(new FileInputStream("C:\\Users\\Administrator\\Desktop\\insert or update.txt"));

		// 读出数据并还原为对象
		for (int i = 0; i < members.length; i++) {
			// 读出UTF字符串
			String name = dataInputStream.readUTF();
			// 读出int数据
			int score = dataInputStream.readInt();
			members[i] = new Member(name, score);
		}

		// 关闭流
		dataInputStream.close();

		// 显示还原后的数据
		for (Member member : members) {
			System.out.printf("%s\t%d%n", member.getName(), member.getAge());
		}
	}
}