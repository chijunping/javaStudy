package com.utils.HttpClientUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static DefaultHttpClient httpclient = new DefaultHttpClient();

	/**
	 * 1. get请求，带参数：map
	 */
	public static String doGet(String url, Map<String, String> param) {
		// 创建Httpclient对象
		String resultString = "";
		HttpResponse response = null;
		HttpGet httpGet = null;
		try {
			// 由url和param创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 由uri创建http GET请求
			httpGet = new HttpGet(uri);
			// 执行httpGet请求
			response = httpclient.execute(httpGet);
			// 判断请求返回码：200成功，否则返回失败码
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "utf-8");
				return resultString;
			} else {
				return "500";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "500";
		} finally {
			httpGet.releaseConnection();
		}

	}

	/**
	 * 2.get请求，不带参数
	 */
	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * 3.post请求，带参数：map
	 */
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		String resultString = "";
		HttpResponse response = null;
		// 创建Http Post请求
		HttpPost httpPost = new HttpPost(url);
		try {
			// 根据param创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 根据参数列表模拟表单实体
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpclient.execute(httpPost);
			// 判断请求返回码：200成功，否则返回失败码
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "utf-8");
				return resultString;
			} else {
				return "500";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "500";
		} finally {
			httpPost.releaseConnection();
		}
	}

	/**
	 * 4.post请求，不带参数
	 */
	public static String doPost(String url) {
		return doPost(url, null);
	}

	/**
	 * 5.post请求，带参数：jsonString
	 */
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		String resultString = "";
		HttpResponse response = null;
		// 创建Http Post请求
		HttpPost httpPost = new HttpPost(url);
		try {
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpclient.execute(httpPost);
			// 判断请求返回码：200成功，否则返回失败码
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "utf-8");
				return resultString;
			} else {
				return "500";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "500";
		} finally {
			httpPost.releaseConnection();
		}
	}
}
