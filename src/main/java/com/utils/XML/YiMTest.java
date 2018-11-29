package com.utils.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class YiMTest {

	public static void main(String[] args) throws Exception {
		File file = new File("src/main/resources/TestFile/17ss-skirt-zy171+61b0020.xml");
		String platForm = "";
		String skuId = "";
		checkResourceXml(file, platForm, skuId);
	}

	public static Map<String, Object> checkResourceXml(File file, String platForm, String skuId) throws Exception {
		// 将xml文件转为xmlString
		Map<String, Object> resMap = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String xmlString = IOUtils.toString(br);
		// 截取字符串，确保文件以“<root>”开头
		xmlString = xmlString.substring(xmlString.indexOf("<root>")).trim();
		// 获取node节点的集合，从node节点获取商品的id值，并根据id查询数据库是否存在该id的商品，如果不存在则提醒先导
		Map<String, Object> xmlResult = readXmlString(xmlString, platForm, skuId);
		return resMap;

	}

	private static Map<String, Object> readXmlString(String xmlString, String platForm, String skuId) {
		Map<String, Object> xmlResult = new HashMap<>();
		Map<String, Object> checkXmlResult = new HashMap<>();
		// 将xmlString转换成xml
		Document xml = null;
		try {
			xml = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
			xmlResult.put("code", "1");
			xmlResult.put("msg", "外部xml格式不正确！");
			return xmlResult;
		}
		// 获取xml根节点，并获取<node>节点的iterator以供遍历
		Element rootElement = xml.getRootElement();
		Iterator<Element> elementIterator = rootElement.elementIterator("node");
		while (elementIterator.hasNext()) {
			Element node = elementIterator.next();
			String goodNo = node.attributeValue("id");
			// 查询数据库有无该goodNo对应的作品
			int count = getProductByGoodNo(goodNo);
			if (count == 0) {
				xmlResult.put("code", "1");
				xmlResult.put("msg", "作品货号【" + goodNo + "】不对");
				return xmlResult;
			}
			// 查询数据库关于该goodNo对应的作品的xml信息
			checkXmlResult = getProductionSKUXMLByGoodNo(goodNo);
			if (checkXmlResult == null) {
				xmlResult.put("code", "1");
				xmlResult.put("msg", "内部xml未导入，请先导入内部xml");
				return xmlResult;
			}

			String type = node.attribute("Type").getValue();
			if ("Display".equals(type)) {
				Element materialslist = node.element("materialslist");
				checkElement(materialslist);
			}

		}
		return xmlResult;
	}

	// 专门负责校验各个节点的合法性，为所有不合法处给出错误提示
	private static Map<String, Object> checkElement(Element materialslist) {
		Map<String, Object> resMap = new HashMap<>();
		Iterator<Element> materialsIterator = materialslist.elementIterator("materials");
		while (materialsIterator.hasNext()) {
			Element materials = materialsIterator.next();
			String fabricArtNo = materials.attributeValue("id");
			String clothName = materials.attributeValue("name");
			// .......根据id、name校验代码。略。。。
			System.out.println();
		}

		return resMap;
	}

	private static Map<String, Object> getProductionSKUXMLByGoodNo(String goodNo) {
		// 略....
		return new HashMap<>();
	}

	private static int getProductByGoodNo(String goodNo) {
		// 略....
		return 1;
	}
}
