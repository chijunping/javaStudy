package com.utils.IO_File;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.junit.Test;

public class CountingOutputStreamDemo {
	/**
	 * 记录流的读取写入字节数：CountingOutputStream
	 */
	@Test
	public void CountingOutputStream() {

		// 输入流的统计
		// CountingInputStream countStream = null;
		// 输出流的统计
		CountingOutputStream countStream = null;
		FileOutputStream fos = null;
		try {
			File file = new File("C:\\Users\\Administrator\\Desktop\\临时test\\javaTest.txt");
			fos = new FileOutputStream(file);
			countStream = new CountingOutputStream(fos);
			countStream.write("Hello".getBytes());

			if (countStream != null) {
				// 已经通过这个stream的字节数
				int bytesWritten = countStream.getCount();
				System.out.println("已经往javaTest.txt这个文件写入的字节数为： " + bytesWritten);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(countStream);
			IOUtils.closeQuietly(fos);
		}
	}

}
