package com.utils.IO_File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;


public class FileConcatenate {

	/**
	 * 
	 */
    @Test
	public  void concennateFile() throws IOException {
    	String[] fileName={"C:/Users/Administrator/Desktop/截图保存/test1.txt"};
		String str;
		// 构建对该文件您的输入流
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Administrator/Desktop/截图保存/test3.txt"));
		for (String name : fileName) {
			BufferedReader reader = new BufferedReader(new FileReader(name));

			while ((str = reader.readLine()) != null) {
				writer.write(str);
				writer.newLine();//调用换行方法
			}
		}
	}

}