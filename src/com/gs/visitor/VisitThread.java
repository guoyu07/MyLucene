/**
 * GS
 */
package com.gs.visitor;

import java.util.List;

import org.apache.log4j.Logger;

import com.gs.crawler.URL;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class VisitThread extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private VisitConf conf;
	private Visitor visitor;
	private List<URL> list;

	/**
	 * @param conf
	 */
	public void setConf(VisitConf conf) {
		this.conf = conf;
	}

	public void run() {
		this.setName("Visitor Thread");
		visitor = conf.visitor;
		list = visitor.visit(conf.url);
	}
}
