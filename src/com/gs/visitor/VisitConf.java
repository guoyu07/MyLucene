/**
 * GS
 */
package com.gs.visitor;

import org.apache.log4j.Logger;

import com.gs.crawler.URL;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class VisitConf {

	private Logger logger = Logger.getLogger(this.getClass());
	public Visitor visitor;
	public URL url;
	/**
	 * @param pop
	 * @param currentVisitor
	 */
	public VisitConf(URL url, Visitor currentVisitor) {
		this.url = url;
		this.visitor = currentVisitor;
	}
}
