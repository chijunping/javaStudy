package com.utils.IO_File.IOUtils_FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * 使用org.apache.commons.io.IOUtils工具类简化IO操作
 * 
 * @ClassName: IOUtilsTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年5月26日 下午2:38:09
 */
public class IOUtilsTest {

	/**
	 * 关闭相关流
	 */
	@Test
	public void closeQuietly() throws Exception {
		File file = null;
		InputStream ins = null;
		try {
			file = new File("C:\\Test.java");
			ins = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ins);
		}

	}

	/**
	 * 将输入流/字节数组转换成文本：IOUtils.toString(is);
	 */
	@Test
	public void IOUtils_toString() throws Exception {
		// 例1:
		URL url = new URL("http://www.163.com");
		// 获取url的inputStream的两种方法：
		InputStream ins1 = url.openStream();
		InputStream ins2 = url.openConnection().getInputStream();
		InputStream ins3 = url.openStream();

		FileWriter output1 = new FileWriter("src/main/resources/TestFile/url_openStream1.txt");
		FileWriter output2 = new FileWriter("src/main/resources/TestFile/url_openStream2.txt");
		IOUtils.copy(ins1, output1, "GBK");
		IOUtils.copy(ins2, output2, "GBK");
		// 注意：ins1在上面已经使用过了，到这里时已经为空
		// String contents = IOUtils.toString(ins1, "UTF-8");

		/*
		 * 1.带编码
		 */
		String contents = IOUtils.toString(ins3, "GBK");
		System.out.println("Slashdot: " + contents);
		// 例2:
		FileInputStream is = new FileInputStream(new File("src/main/resources/TestFile/show_info.xml"));
		/*
		 * 2.不带编码
		 */
		String xmlString = IOUtils.toString(is);
		System.out.println("xml转string: \n" + xmlString);
	}

	/**
	 * 流——>数组：
	 */
	@Test
	public void toByteArray() throws Exception {
		FileInputStream ins = new FileInputStream(new File("src/main/resources/TestFile/show_info.xml"));
		byte[] byteArray = IOUtils.toByteArray(ins);

	}

	/**
	 * 复制文件：copy(in2, os);
	 */
	@Test
	public void copy() {
		InputStream ins = null;
		Writer write = null;
		FileOutputStream os = null;
		try {
			ins = new FileInputStream(new File("src/main/resources/TestFile/show_info.xml"));
			write = new FileWriter("src/main/resources/TestFile/show_info1.xml");
			os = new FileOutputStream("src/main/resources/TestFile/show_info2.xml");

			// 1.错误用法
			/*
			 * IOUtils.copy(ins, write);
			 * //###:问题：复制的文件show_info2.xml内容为空，推测出此时输入流ins已经为空
			 * ！结论：输入流ins已经使用过，不能二次使用。 //如果需要二次使用同一个输入流，需要执行复制操作
			 * IOUtils.copy(ins, os);
			 */

			// 2.正确用法

			/*
			 * ##: 复制输入流
			 */
			byte[] byteArray = IOUtils.toByteArray(ins);
			ByteArrayInputStream in1 = new ByteArrayInputStream(byteArray);
			ByteArrayInputStream in2 = new ByteArrayInputStream(byteArray);
			// 测试两个复制出来的in1、in2是否能用，是否相等:分别执行copy()操作(结果：两个复制流写出的文件完全一致)
			IOUtils.copy(in1, write);
			IOUtils.copy(in2, os);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// ByteArrayOutputStream或ByteArrayInputStream是内存读写流，不同于指向硬盘的流，
			// 它内部是使用字节数组读内存的，这个字节数组是它的成员变量，当这个数组不再使用变成垃圾的时候，Java的垃圾回收机制会将它回收。所以不需要关流
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(write);
			IOUtils.closeQuietly(ins);
		}
	}

	/**
	 * 复制输入流： |----结论：一个流只能用一次。
	 * |----（适用环境实例：需要对一个文件的输入流进行以下两个操作：1.上传；2.计算MD5码——此时需要复制该流）
	 * 推荐：很多时候数组操作和流操作的结果一样，此时尽量用数组操作
	 */
	@Test
	public void copyInputSream() {
		FileInputStream in = null;
		ByteArrayInputStream bIn1 = null;
		ByteArrayInputStream bIn2 = null;
		try {
			// 1.获取文件的流，可直接写文件路径，不需要new file(String path)
			in = new FileInputStream("src/main/resources/TestFile/show_info1.xml");
			// 2.复制输入流
			byte[] bs = IOUtils.toByteArray(in);
			bIn1 = new ByteArrayInputStream(bs);
			bIn2 = new ByteArrayInputStream(bs);
			// 3.判断复制出来的两个流是否相等
			boolean contentEquals = IOUtils.contentEquals(bIn1, bIn2);
			System.out.println(contentEquals);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ByteArrayOutputStream或ByteArrayInputStream是内存读写流，不同于指向硬盘的流，
			// 它内部是使用字节数组读内存的，这个字节数组是它的成员变量，当这个数组不再使用变成垃圾的时候，Java的垃圾回收机制会将它回收。所以不需要关流
			// IOUtils.closeQuietly(bIn2);
			// IOUtils.closeQuietly(bIn1);
			IOUtils.closeQuietly(in);
		}
	}
}