package com.utils.zip;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

public class Unzip {
	public static void main(String[] args) throws Exception {
		String srcPath = "src/main/webapp/dom4j/cloth_model_show_17ss-dress-hb134 - 副本.zip";
		String dest = "src/main/webapp/dom4j/";
		File file = new File(srcPath);
		FileInputStream in = new FileInputStream(file);
	}

	/**
	 * 解压压缩文件流，并根据解压层次来判断，是否只解析第一层，还是解析所有数据 map<name,inputstream>
	 * 
	 * @param in
	 * @param parseLevel
	 * @return
	 */
//	public static Map<String, Object> unZip(BufferedInputStream in) {
//
//		if (in == null)
//			return null;
//
//		ZipEntry zipEntry = null;
//		FileOutputStream out = null;
//		String uniqueName, iconUrl = null;
//		Map<String, Object> map = new HashMap<String, Object>();
//		ZipInputStream zipIn = new ZipInputStream(in);
//		try {
//			zipEntry = zipIn.getNextEntry();
//			String name = zipEntry.getName();
//			while ((zipEntry = zipIn.getNextEntry()) != null) {
//				// 如果是文件夹路径方式，本方法内暂时不提供操作
//				if (zipEntry.isDirectory()) {
//					// String name = zipEntry.getName();
//					// name = name.substring(0, name.length() - 1);
//					// File file = new File(outputDirectory + File.separator +
//					// name);
//					// file.mkdir();
//				} else {
//					 // 如果是文件，则直接在对应路径下生成
//					 uniqueName = getSavedFileName(zipEntry.getName());
//					 File path = new File(dest + File.separator);
//					 if (!path.exists())
//					 path.mkdirs();
//					
//					 iconUrl = outputDirectory + File.separator + uniqueName;
//					 // File file = new File(iconUrl);
//					 file.createNewFile();
//					 out = new FileOutputStream(file);
//					 int b = 0;
//					 while ((b = zipIn.read()) != -1) {
//					 out.write(b);
//					 }
//					out.close();
//					map.put(zipEntry.getName(), iconUrl);
//				}
//			}
//			return map;
//		} catch (Exception ex) {
//			return null;
//		} finally {
//			IOUtils.closeQuietly(zipIn);
//			IOUtils.closeQuietly(in);
//			IOUtils.closeQuietly(out);
//		}
//	}

	
	@SuppressWarnings("unused")
	private static Map<String, Object> upZip(MultipartFile mFile) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (mFile != null) {
			InputStream in = null;
			InputStream in2 = null;
			ZipInputStream zipIn = null;
			ZipEntry zipEntry = null;

			try {
				in = mFile.getInputStream();
				zipIn = new ZipInputStream(in);
				while ((zipEntry = zipIn.getNextEntry()) != null) {
					// 如果是文件夹路径方式，本方法内暂时不提供操作
					if (zipEntry.isDirectory()) {
					} else {
						// 如果是文件，则直接存放在Map中
						String name = zipEntry.getName();
						name = name.substring(name.lastIndexOf("/") + 1);
						// // 获取当前条目的字节数组
						byte[] bt = IOUtils.toByteArray(zipIn);
						// 把当前条目的字节数据转换成Inputstream流
						in2 = new ByteArrayInputStream(bt);
						String rName = name.substring(name.lastIndexOf(".") + 1);
						if (rName.equals("xml")) {
							if (!name.contains("Content_Types")) {
								String xmlString = IOUtils.toString(in2, "utf-8");
								if (xmlString == null) {
									xmlString = "";
								}
								resMap.put("xmlName", name);
								resMap.put("xmlValue", xmlString);
							}
						} else {
							resMap.put("fileName", name);
							resMap.put("fileValue", in2);
						}
					}
				}
				// System.out.println(resMap);
				return resMap;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(in2);
				IOUtils.closeQuietly(zipIn);
			}
		}
		resMap.put("xmlName", "");
		resMap.put("xmlValue", "");
		resMap.put("fileName", "");
		return resMap;
	}
	@SuppressWarnings({ "unused", "deprecation" })
	private static Map<String, Object> upZip(InputStream in) {
		Map<String, Object> resMap = new HashMap<String, Object>();
			InputStream in2 = null;
			ZipInputStream zipIn = null;
			ZipEntry zipEntry = null;
			
			try {
				zipIn = new ZipInputStream(in);
				while ((zipEntry = zipIn.getNextEntry()) != null) {
					// 如果是文件夹路径方式，本方法内暂时不提供操作
					if (zipEntry.isDirectory()) {
					} else {
						// 如果是文件，则直接存放在Map中
						String name = zipEntry.getName();
						name = name.substring(name.lastIndexOf("/") + 1);
						// 把压缩文件内的流转化为字节数组，够外部逻辑使用(之后关闭流)
						byte[] bt = IOUtils.toByteArray(zipIn);
						in2 = new ByteArrayInputStream(bt);
						String rName = name.substring(name.lastIndexOf(".") + 1);
						if (rName.equals("xml")) {
							if (!name.contains("Content_Types")) {
								String xmlString = IOUtils.toString(in2, "utf-8");
								if (xmlString == null) {
									xmlString = "";
								}
								resMap.put("xmlName", name);
								resMap.put("xmlValue", xmlString);
							}
						} else {
							resMap.put("fileName", name);
							resMap.put("fileValue", bt);
						}
					}
				}
				// System.out.println(resMap);
				return resMap;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(in2);
				IOUtils.closeQuietly(zipIn);
			}
		
	}
	
	@Test
	public void upfile() throws Exception{
		InputStream in=new FileInputStream("C:/Users/Administrator/Desktop/临时test/pc.zip");
		Map<String, Object> upZip = upZip(in);
		byte[] fileValue = (byte[]) upZip.get("fileValue");
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(fileValue);
		String md5DigestAsHex2 = DigestUtils.md5DigestAsHex(fileValue);
		
		System.out.println(md5DigestAsHex);
		System.out.println(md5DigestAsHex2);
		System.out.println("");
	}
}