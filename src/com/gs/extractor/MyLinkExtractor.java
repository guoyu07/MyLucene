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

	public static List extractor(URL paurl) {
		List<URL> urls = new LinkedList();
		filter = new NodeClassFilter(LinkTag.class);
		try {
			parser = new Parser(paurl.url);
			list = parser.extractAllNodesThatMatch(filter);
			URL churl = new URL();
			for (int i = 0; i < list.size(); i++) {
				String lin = SubLink.sub(list.elementAt(i).toHtml());
				if (lin == "" || lin == null)
					continue;
				System.out.println("----------------");
				System.out.println(lin);
				churl.level = paurl.level+1;
				churl.url = lin;
				urls.add(churl);
			}
		} catch (EncodingChangeException e) {
			System.out.println("Encoding Error!");
		} catch (ParserException e) {
			//e.printStackTrace();
			System.out.println("Some Error");
		}
		return urls;
	}
}
