/**
 * GS
 */
package com.gs.crawler;

import org.apache.log4j.Logger;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.util.ParserException;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class ContentExtractorThread extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private String url;
	private String result = null;
	public void run(){
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
		result = re;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
