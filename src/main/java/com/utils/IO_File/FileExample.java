package com.utils.IO_File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * 创建一个文件（目录+文件），并往里写入内容
 * 
 * @ClassName: FileExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年5月25日 下午5:29:01
 */
public class FileExample {

	@Test
	public void createFile() throws Exception {
		File f = new File("C:\\Users\\Administrator\\Desktop\\踩踩踩踩踩踩/itest001.doc");
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			// 1.判断该文件路径是否存在，不存在则逐层创建父目录
			if (!f.exists()) {
				File parentFile = f.getParentFile();
				parentFile.mkdirs();
			}
			// 2.在父目录下创建该文件
			f.createNewFile();
			// f.delete(); // 删除此抽象路径名表示的文件或目录

			// 3.获取一个测试文件的输入流inputStream
			inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\world格式模板.doc"));
			// 4.获取文件f的outPutStream
			outputStream = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();

			System.out.println("该分区大小:" + f.getTotalSpace() / (1024 * 1024 * 1024) + "G"); // 返回由此抽象路径名表示的文件或目录的名称。
			System.out.println("文件名 :" + f.getName()); // 返回由此抽象路径名表示的文件或目录的名称。
			System.out.println("文件父目录字符串: " + f.getParent());// 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}
}