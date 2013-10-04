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
import com.gs.utils.Status;

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
	private Status status;
	private long startTime;

	/**
	 * @param property
	 * @param factory
	 * @param manager
	 */
	public Visitor(Property property, VisitorFactory factory,
			VisitorManager manager) {
		this.topN = property.topN;
		this.deepth = property.deepth;
		this.factory = factory;
		this.manager = manager;
	}

	/**
	 * @param url
	 * @return a list of urls which the param page content
	 */
	public List<URL> visit(URL url) {
		this.startTime = System.currentTimeMillis();
		this.status = Status.Proceeding;
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

	/**
	 * recycle this visitor
	 */
	protected void recycle() {
		this.status = Status.Finished;
		factory.recycle(this);
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}
	
	public void destory(){
		if (this.factory.getProceedingQueue().contains(this)) {
			this.factory.getProceedingQueue().remove(this);
		}
		if (this.factory.getFreeVisitorQueue().contains(this)) {
			this.factory.getFreeVisitorQueue().remove(this);
		}
	}
}
