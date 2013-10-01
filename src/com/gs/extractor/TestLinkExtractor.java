/**
 * GS
 */
package com.gs.extractor;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
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
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return urls;
	}

	@Test
	public void testTencentLinkExt() {
		TencentNewsLinkExtractor e = new TencentNewsLinkExtractor();
		List<URL> l = e.extract(new URL("http://news.qq.com", 1), 999);
		for (URL u : l) {
			System.out.println(u.url);
		}
	}

	@Test
	public void testReg() throws IOException {
		String s = FileUtils.readFileToString(new File("D://Test//newc.htm"));
		// String s = FileUtils.readFileToString(new
		// File("D://Test//testHTML.txt"));
		String regex = "<a.*?/a>";
		// String regex = "<a.*>(.*)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(s);
		while (mt.find()) {
			// System.out.println(mt.group());
			// System.out.println();
			// String s2 = ">.*?</a>";// 标题部分
			String s3 = "href=.*?>";

			/*
			 * Pattern pt2 = Pattern.compile(s2); Matcher mt2 =
			 * pt2.matcher(mt.group()); while (mt2.find()) {
			 * //System.out.println("标题：" + mt2.group().replaceAll(">|</a>",
			 * "")); }
			 */

			Pattern pt3 = Pattern.compile(s3);
			Matcher mt3 = pt3.matcher(mt.group());
			while (mt3.find()) {
				System.out.println("网址："
						+ mt3.group().replaceAll("href=|>", ""));
			}
		}
	}

	@Test
	public void testreg2() throws IOException {
		String s = FileUtils.readFileToString(new File("D://Test//newc.htm"));
		// String s = FileUtils.readFileToString(new
		// File("D://Test//testHTML.txt"));
		String regex = "<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>";
		// String regex = "<a.*>(.*)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(s);
		while (mt.find()) {
			System.out.println(mt.group(1) + "   " + mt.group(2));
		}
	}

	@Test
	public void testIO() throws HttpException, IOException {
		HttpClient hc = new HttpClient();
		GetMethod get;
		hc.setConnectionTimeout(5000);
		get = new GetMethod("http://www.hebust.cn");
		try {
			hc.executeMethod(get);
		} catch (ConnectTimeoutException e) {
			logger.error("Bad connection");
		}
		System.out.println(get.getResponseBodyAsString());
		get.releaseConnection();
	}

}
