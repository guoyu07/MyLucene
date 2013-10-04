/**
 * GS
 */
package com.gs.jdom;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

import com.gs.io.ContentReader;

/**
 * @author GaoShen
 * @packageName com.gs.jdom
 */
public class TestJDOM {
	private XMLOutputter out = null;

	@Before
	public void init() {
		out = new XMLOutputter();
	}

	@Test
	public void test() {
		Document doc = new Document();
		/*
		 * // method 1、创建一个Doc文档，添加一个元素root doc = new Document(new
		 * Element("root")); print(doc);
		 * 
		 * // method 2、创建一个Doc文档，添加一个元素root，设置root元素的节点文本 doc = new Document(new
		 * Element("root").setText("this is a root el")); print(doc);
		 */

		// method 3、创建一个Doc文档，添加一个元素root，设置root元素的节点文本且添加一个属性id，值为110
		Element root = new Element("root");
		root.setText("你好啊");
		root.setAttribute("问候", "你好");
		root.addContent(new Element("great").setAttribute("sa", "qwe"));
		doc.setRootElement(root);

		print(doc);

		/*
		 * // method 4、创建一个Doc文档，添加一个元素root，设置root元素的节点文本 doc.addContent(new
		 * Element("root").setText("你好")); print(doc); fail("method 4: \n" +
		 * out.outputString(doc));
		 * 
		 * fail(doc.toString());
		 */
	}

	@Test
	public void test2() {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(new File("D://Test//conf.xml"));
			Element rootEl = doc.getRootElement();
			String topN = rootEl.getChildText("topN");
			String deepth = rootEl.getChildText("deepth");
			String needsIndex = rootEl.getChildText("needsIndex");
			String os = rootEl.getChildText("os");
			String seeds = rootEl.getChildText("seeds");
			System.out.println("topN:" + topN);
			System.out.println("deepth:" + deepth);
			System.out.println("needsIndex:" + needsIndex);
			System.out.println("os:" + os);
			System.out.println("seeds:" + seeds);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test3() {
		CrawlerConfReader r = new CrawlerConfReader("D://Test//conf.xml");
		System.out.println("------------");
		System.out.println(r.getProperty());
	}

	@Test
	public void test4() {
		SAXBuilder builder = new SAXBuilder();
		try {
			String s = FileUtils.readFileToString(new File("D://Lucene//SogouCS.reduced//news.sohunews.020806.txt"), "GBK");
			String[] ss = s.split("</doc>");
			for(int i=0;i<ss.length;i++){
				ss[i] += "</doc>";
				char[] c = ss[i].toCharArray();
				for(int j=0;j<c.length;j++){
					if(c[j] == '&'){
						c[j] = '?';
					}
				}
				ss[i] = new String(c);
			}
			ss[ss.length-1] = null;
			for(String s1 : ss){
				if (!(s1 == null)) {
					System.out.println(s1);
					StringBufferInputStream sbis = new StringBufferInputStream(
							s1);
					Document doc = builder.build(sbis);
					Element rootEl = doc.getRootElement();
					System.out.println("Number : "+rootEl.getChildText("docno"));
					System.out.println("Content : "+rootEl.getChildText("content"));
				}
			}
			/*System.out.println(s);
			StringBufferInputStream sbis = new StringBufferInputStream(s);
			Document doc = builder.build(sbis);
			Element rootEl = doc.getRootElement();*/
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test5() {
		try {
			FileReader r = new FileReader(new File(
					"D://Lucene//news_tensite_xml.xml"));
			char a = 3;// 10M = 10485760B = 5242880 char
			int i = 0;
			int j = 0;
			File f = new File("D://Lucene//" + j + ".xml");
			FileWriter w = new FileWriter(f);
			while (a != -1) {
				a = (char) r.read();
				if (a != '&') {
					w.write(a);
					System.out.print(a);
				}
				i++;
				if (i > 5242880) {
					w.write("</docs>");
					w.close();
					j++;
					f = new File("D://Lucene//" + j + ".xml");
					w = new FileWriter(f);
					w.write("<docs>");
					System.out.println(j + "Finish!");
					i = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test6() {
		String s = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			// s = FileUtils.readFileToString(new File("D://Lucene//1.xml"));
			System.out.println(new String(s.getBytes("GBK"), "GBK"));
			// s.split("<doc></doc>");//\b<doc>\b.*\b</doc>\b
			String ss[] = s.split("</doc>");// \b<doc>\b.*\b</doc>\b
			for (int i = 0; i < ss.length; i++) {
				String s1 = new String(ss[i] + "</doc>");
				InputStream is = new ByteArrayInputStream(s1.getBytes());
				Document doc = builder.build(is);
				Element rootEl = doc.getRootElement();
				System.out.println(rootEl.getChildText("docno"));
				System.out.println(new String(rootEl.getChildText("content")
						.getBytes(), "GB2312"));
				// System.out.println(rootEl.getChildText("content"));

				if (i > 10)
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test7() {
		FileReader fr = null;
		int c = 0;
		String s = "";
		try {
			fr = new FileReader("D://Lucene//news_tensite_xml.xml");
			int i = 0;
			while ((c = fr.read()) != -1 && i < 1000) {
				s += (char)c;
				i++;
			}
			System.out.println(new String(new String(s.getBytes("utf-8"),"utf-8").getBytes("utf-8"),"utf-8"));
			FileUtils.writeStringToFile(new File("D://Test//abc.txt"), new String(new String(s.getBytes("GBK"),"GBK").getBytes("GBK"),"GB2312"));
			fr.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test8() {
		ContentReader c = new ContentReader();
		System.out.println(c.read("D://Lucene//news_tensite_xml.xml", 0, 999));

	}

	private void print(Document doc) {
		// 设置XML文件编码格式
		out.setFormat(Format.getCompactFormat().setEncoding("UTF-8"));
		System.out.println(out.outputString(doc));
	}

}
