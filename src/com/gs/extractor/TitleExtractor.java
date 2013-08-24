/**
 * 
 */
package com.gs.extractor;

import java.util.LinkedList;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TitleExtractor {
	private String url;
	private static Parser parser;
	private static NodeFilter filter;
	private static NodeList list;

	/**
	 * @param url the url which to be extract the title
	 * @return title
	 */
	public static String extractor(String url) {
		filter = new NodeClassFilter(TitleTag.class);
		String title = null;
		try {
			parser = new Parser(url);
			list = parser.extractAllNodesThatMatch(filter);
			
			for (int i = 0; i < list.size(); i++) {
				title = sub(list.elementAt(i).toHtml());
			}
		} catch (ParserException e) {
			System.out.println("Some Error ParserException");
		}
		return title;
	}
	
	private static String sub(String html) {
		char[] c = html.toCharArray();
		if (c.length < 14)
			return null;
		String result = "";
		if (html.substring(0, 7).equals("<title>"))
			for (int i = 7; i < c.length && c[i] != '<'; i++) {
				result += c[i];
			}
		return result;
	}
}
