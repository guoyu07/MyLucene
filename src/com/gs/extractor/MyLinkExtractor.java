/**
 * 
 */
package com.gs.extractor;

import java.net.ConnectException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
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
public class MyLinkExtractor {
	private static Logger logger = Logger.getLogger(MyLinkExtractor.class);

	String url;
	static Parser parser;
	static NodeFilter filter;
	static NodeList list;

	/**
	 * @param paurl
	 *            father page's url
	 * @param topN
	 *            the max of link to the page
	 * @return all the links of the webpage
	 */
	public static List extractor(URL paurl, int topN) {
		LinkedList<URL> urls = new LinkedList();
		filter = new NodeClassFilter(LinkTag.class);
		try {
			parser = new Parser(paurl.url);
			list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				URL churl = new URL();
				String lin = sub(list.elementAt(i).toHtml());
				if (lin == ""
						|| lin == null
						|| !(lin.endsWith(".htm") || lin.endsWith(".html") || lin
								.endsWith(".shtml")))
					continue;
				churl.level = paurl.level + 1;
				churl.url = lin;
				urls.addFirst(churl);
				if (i > topN - 1)
					break;
			}
		} catch (EncodingChangeException e) {
			logger.error("Encoding Error!");
		} catch (ParserException e) {
			logger.error("Some Error");
		}
		return urls;
	}

	private static String sub(String html) {
		char[] c = html.toCharArray();
		if (c.length <= 13)
			return null;
		String result = "";
		if (html.substring(0, 13).equals("<a href=\"http"))
			for (int i = 9; i < c.length && c[i] != '"'; i++) {
				result += c[i];
				if (c[i] == '<') {
					result = "";
					break;
				}
			}
		return result;
	}
}
