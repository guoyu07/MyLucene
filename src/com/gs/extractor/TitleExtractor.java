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
	String url;
	static Parser parser;
	static NodeFilter filter;
	static NodeList list;
	public   static String extractor(String url){
		filter = new NodeClassFilter(TitleTag.class);
		String title = null;
		try {
			parser = new Parser(url);
			list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				title = SubTitle.sub(list.elementAt(i).toHtml());
			}
		} catch (ParserException e) {
			System.out.println("Some Error");
		}
		return title;
	}
}
