package com.utils.IO_File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {

	public static void main(String[] args) {
		// 一次取出的字节数大小,缓冲区大小
		byte[] buffer = new byte[512];
		int numberRead = 0;
		FileInputStream readerIs = null;
		FileOutputStream writerOs = null;
		try {
			readerIs = new FileInputStream("C:\\Users\\Administrator\\Desktop\\截图保存\\test1.txt");
			// 如果文件不存在会自动创建
			writerOs = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\截图保存\\test2.txt");
			// numberRead的目的在于防止最后一次读取的字节小于buffer长度，
			while ((numberRead = readerIs.read(buffer)) != -1) {
				// 用test1.txt内容写入并替换test2.txt内容
				writerOs.write(buffer, 0, numberRead); // 否则会自动被填充0
				// PrintWrite.write(...)//将内容写到控制台输出
				// writerOs.write(buffer, 0, numberRead); //将内容写到输出流对应的文件
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				readerIs.close();
				writerOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}