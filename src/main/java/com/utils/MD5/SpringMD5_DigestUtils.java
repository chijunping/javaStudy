package com.utils.MD5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * 
 * @ClassName: SpringMD5_DigestUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年6月7日 上午11:35:00
 */
public class SpringMD5_DigestUtils {

	/**
	 * 推荐使用Spring自带的MD5加密
	 */
	@Test
	public void testMD5() {
		try {
			// 1.org.springframework.util.DigestUtils工具类——推荐
			String digestUtils = DigestUtils.md5DigestAsHex("111".getBytes());
			System.out.println("spring自带MD5加密方法:结果为》》》" + digestUtils);
			// 2.计算md5函数
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update("111".getBytes());
			String messageDigest = new BigInteger(1, md.digest()).toString(16);
			System.out.println("网上MD5加密方法：结果为》》》》》》》" + messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1.计算文件MD5(已测)
	 */
	public static String getMD5_File(File file) throws Exception {// 结果：cf3df64bf97a98fea996ed1dd352cd03
		byte[] fileToByteArray = FileUtils.readFileToByteArray(file);
		String fileMD5 = DigestUtils.md5DigestAsHex(fileToByteArray);
		return fileMD5;
	}

	/**
	 * 2.计算文件MD5(已测)
	 */
	public static String getMD5_ByteArray(byte[] byteArray) {// 结果：cf3df64bf97a98fea996ed1dd352cd03
		String byteArrayMD5 = DigestUtils.md5DigestAsHex(byteArray);
		return byteArrayMD5;
	}

	/**
	 * 3.计算文件MD5(已测)
	 */
	public static String getMD5_InputStream(InputStream ins) throws Exception {// 结果：cf3df64bf97a98fea996ed1dd352cd03
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while ((len = ins.read(buf)) != -1) {
			bos.write(buf, 0, len);
		}
		byte[] byteArray = bos.toByteArray();
		String byteArrayMD5 = DigestUtils.md5DigestAsHex(byteArray);
		return byteArrayMD5;
	}

	public static void main(String[] args) throws Exception {
		File file = new File("src/main/resources/TestFile/dubbo-consumer.zip");

		// 1.测试：getMD5_file(file);
		String md5_file = getMD5_File(file);
		System.out.println(md5_file);

		// 2.测试：getMD5_ByteArray(fileToByteArray);
		byte[] fileToByteArray = FileUtils.readFileToByteArray(file);
		String md5_ByteArray = getMD5_ByteArray(fileToByteArray);
		System.out.println(md5_ByteArray);

		// 3.测试：getMD5_InputStream(InputStream ins)
		InputStream ins = new FileInputStream(file);
		String md5_InputStream = getMD5_InputStream(ins);
		System.out.println(md5_InputStream);
	}
}
