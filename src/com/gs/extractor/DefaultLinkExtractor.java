/**
 * 
 */
package com.gs.extractor;

import java.io.StringReader;
import java.net.ConnectException;
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
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.gs.crawler.URL;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class DefaultLinkExtractor implements LinkExtractor {
	private static Logger logger = Logger.getLogger(DefaultLinkExtractor.class);

	String url;
	 Parser parser;
	 NodeFilter filter;
	 NodeList list;

	/**
	 * @param paurl
	 *            father page's url
	 * @param topN
	 *            the max of link to the page
	 * @return all the links of the webpage
	 */
	public  List<URL> extract(URL paurl, int topN) {
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
					// logger.debug("this is not a http link");
				}
				if (document != null) {
					Element root = document.getRootElement();
					churl.url = root.attributeValue("href");
					if (churl.url == null
							|| churl.url == ""
							|| churl.url.startsWith("http://rss")
							|| !(churl.url.startsWith("http")||churl.url.endsWith(".htm")
									|| churl.url.endsWith(".html")
									|| churl.url.endsWith(".shtml")
									|| churl.url.endsWith(".jsp") || churl.url
										.endsWith(".php"))) {
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

	/* (non-Javadoc)
	 * @see com.gs.extractor.LinkExtractor#getHtml()
	 */
	@Override
	public String getHtml() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
