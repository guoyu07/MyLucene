/**
 * GS
 */
package com.gs.visitor;

import org.apache.log4j.Logger;

import com.gs.crawler.URL;

/**
 * the configure of visitor thread
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class VisitConf {

	private Logger logger = Logger.getLogger(this.getClass());
	public Visitor visitor;
	public URL url;
	/**
	 * @param the url to visit
	 * @param currentVisitor the visitor to visit
	 */
	public VisitConf(URL url, Visitor currentVisitor) {
		this.url = url;
		this.visitor = currentVisitor;
	}
}
