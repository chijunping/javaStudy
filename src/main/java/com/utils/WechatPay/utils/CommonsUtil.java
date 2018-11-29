package com.utils.WechatPay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CommonsUtil {
	private static String paramValue1;

	// static{
	// Properties prop = new Properties();
	// InputStream inStream =
	// CommonsUtil.class.getResourceAsStream("/weChart.properties");
	// try {
	// prop.load(inStream);
	// paramValue1=prop.getProperty("APPID");
	// System.out.println(inStream);
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }

	// 获取用户授权的CODE
	public static String GetCodeRequest = null;
	// 通过网页授权获取用户信息
	public static String REQUESTURL = null;
	// 获取授权请求、获取code
	// public static final String URL =
	// "http://asiacarp.3dyim.com/yimengomp/weChatShoppingMall/saveWechartUser.do";
	public static String URL = null;

	// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
	// public static final String NOTIFY_URL =
	// "http://asiacarp.3dyim.com/vShop/paySuccess.html";
	// public static final String NOTIFY_URL =
	// "http://omptest.3dyim.com/vShop/paySuccess.html";
	// public static final String NOTIFY_URL =
	// "http://omptest.3dyim.com/yimengomp/restapi/wechat/paysuccess2.do";//
	// 测试环境
	public static String NOTIFY_URL = "http://www.baidu.com";
	// public static final String NOTIFY_URL =
	// "http://asiacarp.3dyim.com/yimengomp/restapi/wechat/paysuccess2.do";//正式环境

	public static String APPID = "wxace9f27e7bba7ccc";// 测试
	public static String APPSECRET = "cf288041d68f3a5cc7eb02d53c8bb7b2";// 测试
	public static String PARTNER = "1364717702";// 测试
	public static String PARTNERKEY = "DIASIWL2314LDKWQddliwq09234kldja";// 测试

	// public static final String APPID = "wxc0d0b2ed3ab63e7e";//正式
	// public static final String APPSECRET =
	// "57e914e6415df89699a11163e78524be";//正式
	// public static final String PARTNER = "1325535001";//正式
	// public static final String PARTNERKEY =
	// "DSE785DS8924D9DSsw82fs69ds3dgs91";//正式

	// 获取微信退款接口地址
	public static String weChartRefund = null;

	/**
	 * 已关注
	 * */
	public static final String SUBSCRIBE = "1";// 已关注

	static {
		Logger logger = Logger.getLogger(Object.class);
		Properties propertie = new Properties();
		InputStream inputStream = CommonsUtil.class
				.getResourceAsStream("/com/epro/yimeng/config/param.properties");
		try {
			propertie.load(inputStream);
			APPID = propertie.getProperty("weChart.APPID");
			APPSECRET = propertie.getProperty("weChart.APPSECRET");
			PARTNER = propertie.getProperty("weChart.PARTNER");
			PARTNERKEY = propertie.getProperty("weChart.PARTNERKEY");
			weChartRefund = propertie.getProperty("weChart.weChartRefund");
			URL = propertie.getProperty("weChart.SaveUserURL");
			NOTIFY_URL = propertie.getProperty("weChart.SaveUserURL");
			REQUESTURL = propertie.getProperty("weChart.REQUESTURL");
			GetCodeRequest = propertie.getProperty("weChart.GetCodeRequest");
			inputStream.close();
		} catch (IOException e) {
			logger.error("初始化参数失败，原因：" + e.getMessage());
		}
	}

	/**
	 * 
	 * @return 20位的ID
	 */
	public static final String getSequenceID() {
		long timeLong = System.currentTimeMillis();
		long roundom = Math.round(Math.random() * 10000000);
		StringBuffer sb = new StringBuffer();
		sb.append(timeLong).append(roundom);
		return sb.toString();
	}

	/**
	 * 
	 * @param length
	 *            随机数长度
	 * @return length长度的随机数
	 */
	public static final String getRandomString(int length) {
		long timeLong = System.currentTimeMillis();
		long roundom = Math.round(Math.random() * 10000000);
		StringBuffer sb = new StringBuffer();
		sb.append(timeLong).append(roundom);
		return sb.toString();
	}

	/**
	 * base64进制加密
	 * 
	 * @param password
	 * @return
	 */
	public static final String encrytBase64(String password) {
		return new BASE64Encoder().encode(password.getBytes());
	}

	/**
	 * base64进制解密
	 * 
	 * @param cipherText
	 * @return
	 * @throws IOException
	 */
	public static final String decryptBase64(String cipherText)
			throws IOException {
		return new String(new BASE64Decoder().decodeBuffer(cipherText));
	}

	/**
	 * 
	 * @return
	 */
	public static final String getTimeLongs() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * MD5加密 生成32位md5码
	 * 
	 * @param 待加密字符串
	 * @return 返回32位md5码
	 */
	public static final String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] strBytes = inStr.getBytes("UTF-8"); // 获得inStr的byte[]
		byte[] md5Bytes = md5.digest(strBytes); // 获得inStr的md5摘要byte[]
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff; // & 按位与，位相同为1，不同为0
			if (val < 16) { // 当值小于16，转换成16进制只有一位
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 判断参数是否为null，为null返回空字符串，不为null返回当前参数值
	 * 
	 * @param string
	 * @return
	 */
	public static final String convertNull(String string) {
		if (string == null) {
			return "";
		}
		return string;
	}

	public static final String convertNull(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static void main(String[] args) {
		try {
			// YimengClothing clothing = new YimengClothing();
			// clothing.setAddYear("2015");
			// Method method = clothing.getClass().getMethod("getAddYear");
			// String s = (String) method.invoke(clothing);
			// System.out.println(s);
			// ==============
			// String paramName = "addYear";
			// char[] chars = paramName.toCharArray();
			// chars[0] = Character.toUpperCase(chars[0]);
			// paramName = String.valueOf(chars);
			// System.out.println(paramName);

			System.out.println(System.currentTimeMillis());
			System.out.println(Math.random() * 1000000000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 对字符串进行加密
	public static String Encrypt(String strSrc, String encName) {
		// parameter strSrc is a string will be encrypted,
		// parameter encName is the algorithm name will be used.
		// encName dafault to "MD5"
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	// Encrypt()的辅助函数,把字符串的字节转化成16进制的字符
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public static String getParamValue1() {
		return paramValue1;
	}

	public static void setParamValue1(String paramValue1) {
		CommonsUtil.paramValue1 = paramValue1;
	}
}