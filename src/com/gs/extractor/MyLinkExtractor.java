/**
 * 
 */
package com.gs.extractor;

import java.net.ConnectException;
import java.util.LinkedList;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.gs.MyCrawler.URL;
import com.gs.util.Printer;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class MyLinkExtractor {

	String url;
	static Parser parser;
	static NodeFilter filter;
	static NodeList list;

	public static List extractor(URL paurl ,int topN) {
		LinkedList<URL> urls = new LinkedList();
		filter = new NodeClassFilter(LinkTag.class);
		try {
			parser = new Parser(paurl.url);
			list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				URL churl = new URL();
				String lin = SubLink.sub(list.elementAt(i).toHtml());
				if (lin == "" || lin == null||!(lin.endsWith(".htm")||lin.endsWith(".html")||lin.endsWith(".shtml")))
					continue;
				churl.level = paurl.level+1;
				churl.url = lin;
				urls.addFirst(churl);
				if(i > topN-1) break;
			}
		} catch (EncodingChangeException e) {
			System.out.println("Encoding Error!");
		} catch (ParserException e) {
			System.out.println("Some Error");
		}
		return urls;
	}
}
