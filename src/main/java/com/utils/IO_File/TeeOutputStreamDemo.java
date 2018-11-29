package com.utils.IO_File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.Test;

public class TeeOutputStreamDemo {
	/**
	 * 同一内容写入到多个不同的文件（覆盖原来内容）：TeeOutputStream
	 */
	@Test
	public void TeeOutputStream() throws Exception {

		File test1 = new File("C:\\Users\\Administrator\\Desktop\\临时test\\javaTest.txt");
		File test2 = new File("C:\\Users\\Administrator\\Desktop\\临时test\\dubbo视频地址.txt");
		OutputStream outStream = null;
		FileOutputStream fos1 = null;
		FileOutputStream fos2 = null;
		try {
			fos1 = new FileOutputStream(test1);
			fos2 = new FileOutputStream(test2);
			// 用TeeOutputStream封装两个不同的文本文件的输出流
			outStream = new TeeOutputStream(fos1, fos2);
			// 两个封装的输出流会分别写出指定内容
			outStream.write("测试文本内容。。。".getBytes());
			outStream.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outStream);
			IOUtils.closeQuietly(fos1);
			IOUtils.closeQuietly(fos2);
		}
	}
}

