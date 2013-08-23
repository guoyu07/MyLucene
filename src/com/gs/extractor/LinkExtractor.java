/**
 * 
 */
package com.gs.extractor;

import javax.swing.JOptionPane;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * LinkExtractor extracts all the links from the given webpage
 * and prints them on standard output.
 */
/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class LinkExtractor {
	/**
	 * Run the link extractor.
	 * 
	 * @param args
	 *            [0] Optional url to extract links from. An input dialog is
	 *            displayed if it is not supplied.
	 */
	public static void main(String[] args) {
		String url;
		Parser parser;
		NodeFilter filter;
		NodeList list;

		if (0 >= args.length) {
			url = (String) JOptionPane.showInputDialog(null,
					"Enter the URL to extract links from:", "Web Site",
					JOptionPane.PLAIN_MESSAGE, null, null,
					"http://htmlparser.sourceforge.net/wiki/");
			if (null == url)
				System.exit(1);
		} else
			url = args[0];
		filter = new NodeClassFilter(LinkTag.class);
		if ((1 < args.length) && args[1].equalsIgnoreCase("-maillinks"))
			filter = new AndFilter(filter, new NodeFilter() {
				public boolean accept(Node node) {
					return (((LinkTag) node).isMailLink());
				}
			});
		try {
			parser = new Parser(url);
			list = parser.extractAllNodesThatMatch(filter);
			
			for (int i = 0; i < list.size(); i++) {
				
				String lin = SubLink.sub(list.elementAt(i).toHtml());
				if(lin == "") continue;
				System.out.println("----------------");
				System.out.println(list.elementAt(i).toHtml());
				System.out.println(SubLink.sub(list.elementAt(i).toHtml()));
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
