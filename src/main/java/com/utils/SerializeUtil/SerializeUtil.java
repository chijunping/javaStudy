package com.utils.SerializeUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * 序列化工具类
 */
/*
 * ByteArrayInputStream/ByteArrayOutputStream是基于内存Byte数组的流， 不需要close，
 * 当没有强引用的时候会自动被垃圾回收了
 */
public class SerializeUtil {
	/**
	 * 日志
	 */
	private static Logger logger = Logger.getLogger(SerializeUtil.class);

	/**
	 * 序列化
	 * 
	 * @param object
	 * 
	 * @return
	 */
	public static byte[] serialize(Object object) {
		byte[] bytes = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			logger.error("serialize", e);
		} finally {
			IOUtils.closeQuietly(oos);
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		} catch (Exception e) {
			logger.error("unserialize", e);
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(bais);
		}
		return obj;
	}
}