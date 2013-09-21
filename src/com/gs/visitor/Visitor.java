/**
 * GS
 */
package com.gs.visitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.crawler.ConnectionTest;
import com.gs.crawler.Property;
import com.gs.crawler.URL;
import com.gs.extractor.MyLinkExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class Visitor {
	private Logger logger = Logger.getLogger(this.getClass());
	private int topN;
	private VisitorFactory factory;
	private ConnectionTest tester = new ConnectionTest();
	private VisitorManager manager;
	private int deepth;

	public Visitor(Property property, VisitorFactory factory,
			VisitorManager manager) {
		this.topN = property.topN;
		this.deepth = property.deepth;
		this.factory = factory;
		this.manager = manager;
	}

	public List<URL> visit(URL url) {
		if (url.level < deepth) {
			List<URL> list = null;
			if (!tester.test(url.url, 5000)) { 
				// In order to avoid bad links
				recycle();
				return new LinkedList<URL>();
			}
			list = MyLinkExtractor.extractor(url, topN);
			
			for (URL nurl : list) {
				manager.add(nurl);
				logger.info("URL = " + nurl.url + "    Level = " + nurl.level);
			}
			recycle();
			return list;
		} else {
			recycle();
			return new LinkedList<URL>();
		}
	}

	public void recycle() {
		factory.recycle(this);
	}
}
