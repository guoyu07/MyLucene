/**
 * 
 */
package com.gs.extractor;

import org.htmlparser.beans.StringBean;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.util.ParserException;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class MainExtractor {
	private String resource;

	/**
	 * Construct a StringExtractor to read from the given resource.
	 * 
	 * @param resource
	 *            Either a URL or a file name.
	 */
	/*public MainExtractor(String resource) {
		this.resource = resource;
	}
*/
	/**
	 * Extract the text from a page.
	 * 
	 * @return The textual contents of the page.
	 * @param links
	 *            if <code>true</code> include hyperlinks in output.
	 * @exception ParserException
	 *                If a parse error occurs.
	 */
	public String extractStrings(boolean links) throws ParserException {
		StringBean sb;

		sb = new StringBean();
		sb.setLinks(links);
		sb.setURL(resource);

		return (sb.getStrings());
	}

	/**
	 * Mainline.
	 * 
	 * @param args
	 *            The command line arguments.
	 */
	public static String extractor(String url) {
		boolean links;
		StringExtractor se;
		links = false;
		se = new StringExtractor(url);
		String re = null;
		try {
			re = se.extractStrings(links);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return re;
	}
}
