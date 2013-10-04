/**
 * GS
 */
package com.gs.extractor;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;

import com.gs.crawler.URL;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TestLinkExtractor {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		try {
			Parser parser;
			NodeFilter filter;
			NodeList list;
			filter = new NodeClassFilter(LinkTag.class);
			parser = new Parser("http://news.qq.com");
			list = parser.extractAllNodesThatMatch(filter);
			SAXReader reader = new SAXReader();
			for (int i = 0; i < list.size(); i++) {
				String s1 = list.elementAt(i).toHtml();
				// System.out.println(s1);
				StringReader sr = new StringReader(s1);
				Document document = null;
				try {
					document = reader.read(sr);
				} catch (DocumentException e) {
				}
				if (document != null) {
					Element root = document.getRootElement();
					System.out.println(root.attributeValue("href") + "   "
							+ root.getText());
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	String url;
	Parser parser;
	NodeFilter filter;
	NodeList list;
	public List extractor(URL paurl, int topN) {
		LinkedList<URL> urls = new LinkedList();
		filter = new NodeClassFilter(LinkTag.class);
		try {
			parser = new Parser(paurl.url);
			list = parser.extractAllNodesThatMatch(filter);
			SAXReader reader = new SAXReader();
			for (int i = 0; i < list.size(); i++) {
				URL churl = new URL();
				String s1 = list.elementAt(i).toHtml();
				StringReader sr = new StringReader(s1);
				Document document = null;
				try {
					document = reader.read(sr);
				} catch (DocumentException e) {
				}
				if (document != null) {
					Element root = document.getRootElement();
					churl.url = root.attributeValue("href");
				}
				churl.level = paurl.level + 1;
				urls.addFirst(churl);
				if (i > topN - 1)
					break;
			}
		} catch (EncodingChangeException e) {
			logger.error("Encoding Error!");
		} catch (ParserException e) {
			logger.error("Some Error");
		} catch(Exception e){
			logger.error(e.getMessage());
		}
		return urls;
	}
	
	@Test
	public void test1(){
		for(URL u : MyLinkExtractor.extractor(new URL("http://news.qq.com",1), 500)){
			System.out.println(u.url);
		}
	}
	
}
