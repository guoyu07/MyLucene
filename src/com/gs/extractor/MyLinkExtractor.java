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

import com.gs.util.Printer;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class MyLinkExtractor {

	String url;
	Parser parser;
	NodeFilter filter;
	NodeList list;

	public List extractor(String url) {
		List<String> urls = new LinkedList();
		filter = new NodeClassFilter(LinkTag.class);
		try {
			parser = new Parser(url);
			list = parser.extractAllNodesThatMatch(filter);
			TitleExtractor te = new TitleExtractor();
			Printer p = new Printer();
			for (int i = 0; i < list.size(); i++) {
				String lin = SubLink.sub(list.elementAt(i).toHtml());
				// System.out.println("lin = "+lin);
				if (lin == "" || lin == null)
					continue;
				p.print("----------------");
				p.print(list.elementAt(i).toHtml());
				te.extractor(lin);
				p.print(lin);
				urls.add(lin);
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
