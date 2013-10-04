/**
 * 
 */
package com.gs.extractor;

import org.apache.log4j.Logger;
import org.htmlparser.beans.StringBean;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.util.ParserException;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class ContentExtractor {
	private static Logger logger = Logger.getLogger(ContentExtractor.class);

	/**
	 * @param url
	 *            which want to be extract the content
	 * @return the main content of the webpage
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
			logger.error(e.getMessage());
		}
		char[] c = re.toCharArray();
		for(int i=0;i<c.length;i++){
			if((c[i]>='A'&&c[i]<='Z')||(c[i]>='a'&&c[i]<='z')||(c[i]>=33&&c[i]<=47)||(c[i]>=58&&c[i]<=64)||(c[i]>=123&&c[i]<=126)||(c[i]>=91&&c[i]<=96)){
				c[i] = ' ';
			}
		}
		re = new String(c);
		return re;
	}
}
