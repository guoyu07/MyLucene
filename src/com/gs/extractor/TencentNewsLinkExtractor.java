/**
 * GS
 */
package com.gs.extractor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jdom.input.SAXBuilder;

import com.gs.DAO.DAO;
import com.gs.crawler.URL;
import com.gs.io.ContentWriter;
import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TencentNewsLinkExtractor implements LinkExtractor {
	private Logger logger = Logger.getLogger(this.getClass());
	String url;
	Parser parser;
	NodeFilter filter;
	NodeList list;
	private String html;

	/*
	 * Extract Tencent News Center's Link like "http://news.qq.com/....."
	 * 
	 * @see com.gs.extractor.LinkExtractor#extractor(com.gs.crawler.URL, int)
	 */
	@Deprecated
	public List<URL> extractByHtmlParser(URL paurl, int topN) {
		LinkedList<URL> urls = new LinkedList();
		// filter = new TagNameFilter("a");
		filter = new NodeClassFilter(LinkTag.class);
		try {
			parser = new Parser(paurl.url);
			list = parser.extractAllNodesThatMatch(filter);
			SAXReader reader = new SAXReader();
			for (int i = 0; i < list.size(); i++) {
				URL churl = new URL();
				String s1 = list.elementAt(i).toHtml();
				System.out.println(s1);
				String[] ss = s1.split(">");
				s1 = ss[0] + "/>";
				System.out.println(s1);
				StringReader sr = new StringReader(s1);
				Document document = null;
				try {
					document = reader.read(sr);
				} catch (DocumentException e) {
					logger.error(e.getMessage());
				}
				if (document != null) {
					Element root = document.getRootElement();
					churl.url = root.attributeValue("href");
					boolean isTencentNewsLink = churl.url
							.startsWith("http://news.qq.com")
							&& churl.url.endsWith("htm");
					System.out.println(isTencentNewsLink + "  " + churl.url);
					if (!isTencentNewsLink) {

						continue;
					}
				}
				churl.level = paurl.level + 1;
				if (churl.url != null) {
					urls.addFirst(churl);
				}
				if (urls.size() > topN - 1)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.extractor.LinkExtractor#extract(com.gs.crawler.URL, int)
	 */
	@Override
	public List<URL> extract(URL paurl, int topN) {
		LinkedList<URL> urls = new LinkedList();
		String s = null;
		HttpClient hc = new HttpClient();
		GetMethod get;
		try {
			hc.setConnectionTimeout(5000);
			get = new GetMethod(paurl.url);
			hc.executeMethod(get);
			s = (get.getResponseBodyAsString());
			html = s;
			
			get.releaseConnection();
		} catch (ConnectTimeoutException e) {
			logger.error("Bad connection");
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		if(s == null || s.equals("")){
			return urls;
		}
		String regex = "<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(s);
		while (mt.find()) {
			String u = mt.group(1);
			if (u.startsWith("http://news.qq.com")
					&& (u.endsWith("htm") || u.endsWith("html") || u
							.endsWith("shtml"))) {
				urls.add(new URL(u, paurl.level + 1));
			}
		}

		return urls;
	}
	
	
	
	private String extractContent(String html){
		String re = null;
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</div>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(html);
		if (mt.find()) {
			re = (mt.group(1).replaceAll("[a-zA-Z_/\"<>=.:]", ""));
		}
		return re;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

}
