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
	Parser parser;
	NodeFilter filter;
	NodeList list;
	public void extractor(String url){
		filter = new NodeClassFilter(TitleTag.class);
		try {
			parser = new Parser(url);
			list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.elementAt(i).toHtml());
			}
		} catch (ParserException e) {
			e.printStackTrace();
			System.out.println("Some Error");
		}
	}
}
