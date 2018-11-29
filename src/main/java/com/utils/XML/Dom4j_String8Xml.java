package com.utils.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class Dom4j_String8Xml {
	/**
	 * 1.xml 2 Sting 将xml指定节点内下的所有内容转换成string
	 * 
	 * @throws Exception
	 */
	@Test
	public void testxml2Sting() throws Exception {

		// 创建saxreader对象
		SAXReader reader = new SAXReader();
		// 读取一个文件，把这个文件转换成Document对象
		Document document = reader.read(new File("src/main/webapp/dom4j/02.xml"));
		// 获取根元素
		// 把文档转换字符串
		System.out.println("------------原文转换String---------------");
//		String docXmlText = document.asXML();
//		System.out.println(docXmlText);
		System.out.println("------------原文转换String结束---------------");
		// csdn元素标签根转换的内容
		System.out.println("------------根节点下所有内容转换String---------------");
		Element root = document.getRootElement();
		String rootXmlText = root.asXML();
		System.out.println(rootXmlText);
		System.out.println("------------根节点下内容转换String结束---------------");
		// 获取java元素标签 内的内容
		System.out.println("------------根节点下的某指定节点下的所有内容转换String---------------");
		Element e = root.element("西游记");
		System.out.println(e.asXML());
		System.out.println("------------根节点下的某指定节点下的所有内容转换String---------------");
	}

	/**
	 * 2.把一个文本字符串转换Document对象
	 * 
	 * @throws Exception
	 */
	@Test
	public void testString2XML() throws Exception {
		String text = "<csdn><java>Java班</java><net>.Net班</net></csdn>";
		Document document = DocumentHelper.parseText(text);
		Element root = document.getRootElement();
		System.out.println(root.getName());
		writerDocumentAsXML(document);
	}

	/**
	 * 3.创建一个document对象 往document对象中添加节点元素 转存为xml文件
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateXML() throws Exception {

		Document document = DocumentHelper.createDocument();// 创建根节点
		Element root = document.addElement("csdn");
		Element java = root.addElement("java");
		java.setText("java班");
		Element ios = root.addElement("ios");
		ios.setText("ios班");

		writerDocumentAsXML(document);
	}

	/**
	 * 把document对象写入新的文件
	 * 
	 * @param document
	 * @throws Exception
	 */
	public void writerDocumentAsXML(Document document) throws Exception {
		// 紧凑的格式
		// OutputFormat format = OutputFormat.createCompactFormat();
		// 排版缩进的格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置编码
		format.setEncoding("UTF-8");
		// 创建XMLWriter对象,指定了写出文件及编码格式
		// XMLWriter writer = new XMLWriter(new FileWriter(new
		// File("src//a.xml")),format);
		XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/webapp/dom4j/03.xml")), "UTF-8"), format);
		// 写入
		writer.write(document);
		// 立即写入
		writer.flush();
		// 关闭操作
		writer.close();
	}
}