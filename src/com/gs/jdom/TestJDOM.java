/**
 * GS
 */
package com.gs.jdom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

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
		/*// method 1、创建一个Doc文档，添加一个元素root
		doc = new Document(new Element("root"));
		print(doc);

		// method 2、创建一个Doc文档，添加一个元素root，设置root元素的节点文本
		doc = new Document(new Element("root").setText("this is a root el"));
		print(doc);*/

		// method 3、创建一个Doc文档，添加一个元素root，设置root元素的节点文本且添加一个属性id，值为110
		Element root = new Element("root");
		root.setText("你好啊");
		root.setAttribute("问候", "你好");
		root.addContent(new Element("great").setAttribute("sa", "qwe"));
		doc.setRootElement(root);
		
		print(doc);

		/*// method 4、创建一个Doc文档，添加一个元素root，设置root元素的节点文本
		doc.addContent(new Element("root").setText("你好"));
		print(doc);
		fail("method 4: \n" + out.outputString(doc));

		fail(doc.toString());*/
	}
	@Test
	public void test2(){
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
	public void test3(){
		CrawlerConfReader r = new CrawlerConfReader("D://Test//conf.xml");
		System.out.println("------------");
		System.out.println(r.getProperty());
	}
	

	private void print(Document doc) {
		// 设置XML文件编码格式
		out.setFormat(Format.getCompactFormat().setEncoding("UTF-8"));
		System.out.println(out.outputString(doc));
	}

}
