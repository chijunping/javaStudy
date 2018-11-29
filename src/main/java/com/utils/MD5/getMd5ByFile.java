package com.utils.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

public class getMd5ByFile {

	/**
	 * 加密文件 2015年7月1日 上午12:06:37
	 */
	@Test
	public void getMd5ByFile() {
		try {
			// 文件大小不能超过Integer.MAX_VALUE
			File file = new File("src/main/resources/TestFile/dubbo-consumer.zip");

			FileInputStream in = new FileInputStream(file);

			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, Long.valueOf(file.length()));
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			String value = bi.toString(16);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对文件全文生成MD5摘要（可用）
	 * 
	 * @param MultipartFile
	 *            filedata 要加密的文件
	 * @return MD5摘要码
	 */
	public static String getFileMD5Code(MultipartFile filedata) throws Exception {
		if (filedata == null) {
			return null;
		}
		// 定义缓冲区大小
		byte[] buffer = new byte[1024 * 1024];
		DigestInputStream digestInputStream = null;
		InputStream inputStream = null;
		try {
			// 初始化MD5的消息摘要对象
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 获得上传文件的输入流
			inputStream = filedata.getInputStream();
			// 初始化DigestInputStream
			digestInputStream = new DigestInputStream(inputStream, messageDigest);
			// 读完所有文件字节
			while (digestInputStream.read(buffer) != -1)
				;
			// 获取包含文件信息的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 获得文件的摘要信息，以16位长度的byte数组存储
			byte[] digestBytes = messageDigest.digest();
			// 字节数组转换为32位MD5码
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < digestBytes.length; i++) {
				int val = ((int) digestBytes[i]) & 0xff; // & 按位与，位相同为1，不同为0
				if (val < 16) { // 当值小于16，转换成16进制只有一位
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (digestInputStream != null)
				digestInputStream.close();
			if (inputStream != null)
				inputStream.close();
		}
		return null;
	}

	/**
	 * 对文件全文生成MD5摘要（可用）
	 * 
	 * @param MultipartFile
	 *            filedata 要加密的文件
	 * @return MD5摘要码
	 */
	public static String getFileMD5Code(InputStream in) throws Exception {
		if (in == null) {
			return null;
		}
		// 定义缓冲区大小
		byte[] buffer = new byte[1024 * 1024];
		DigestInputStream digestInputStream = null;
		try {
			// 初始化MD5的消息摘要对象
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 初始化DigestInputStream
			digestInputStream = new DigestInputStream(in, messageDigest);
			// 读完所有文件字节
			while (digestInputStream.read(buffer) != -1)
				;
			// 获取包含文件信息的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 获得文件的摘要信息，以16位长度的byte数组存储
			byte[] digestBytes = messageDigest.digest();
			// 字节数组转换为32位MD5码
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < digestBytes.length; i++) {
				int val = ((int) digestBytes[i]) & 0xff; // & 按位与，位相同为1，不同为0
				if (val < 16) { // 当值小于16，转换成16进制只有一位
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (digestInputStream != null)
				digestInputStream.close();
			if (in != null)
				in.close();
		}
		return null;
	}

}
