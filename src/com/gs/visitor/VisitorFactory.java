/**
 * GS
 */
package com.gs.visitor;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.gs.crawler.Property;
import com.gs.downloader.Downloader;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class VisitorFactory {
	private VisitorQueue freeVisitorQueue = new VisitorQueue();
	private VisitorQueue proceedingVisitorQueue = new VisitorQueue();
	private int countofinitial = 0; // a counter of initial
	private Property property;
	private Logger logger = Logger.getLogger(this.getClass());
	private VisitorManager manager;
	private final static int limitVisitor = 30;

	/**
	 * @param property
	 */
	public VisitorFactory(Property property, VisitorManager manager) {
		this.property = property;
		this.manager = manager;
	}

	/**
	 * @return
	 */
	public Visitor getVisitor() {
		Visitor current;
		if (freeVisitorQueue.isQueueEmpty()) { // there is a free one
			logger.debug("===========A new Visitor is initialed!=============");
			current = new Visitor(property, this, manager,countofinitial);
			countofinitial++;
			proceedingVisitorQueue.push(current); // move it to proceeding queue
			logger.debug("Total initial : " + countofinitial
					+ "\nFree Downloader : " + freeVisitorQueue.size()
					+ "\nProceeding Downloader : "
					+ proceedingVisitorQueue.size());
		} else {
			logger.debug("-----------Use Old Visitor!-------------");
			boolean NoSuchElementFlag = false;
			try {
				current = freeVisitorQueue.pop();
				// use a free one
			} catch (NoSuchElementException e) {
				current = new Visitor(property, this, manager,countofinitial);
				countofinitial++;
				proceedingVisitorQueue.push(current);// move it to procedding
														// queue
				NoSuchElementFlag = true;
			}
			if (!NoSuchElementFlag) {
				proceedingVisitorQueue.push(current);
				freeVisitorQueue.remove(current); // remove it from the
													// freequeue
			}
			logger.debug("Total initial : " + countofinitial
					+ "\nFree Visitor : " + freeVisitorQueue.size()
					+ "\nProceeding Visitor : " + proceedingVisitorQueue.size());
		}
		return current;
	}

	/**
	 * @return true-empty
	 */
	public boolean isProceedingQueueEmpty() {
		return proceedingVisitorQueue.isQueueEmpty();
	}

	/**
	 * @param visitor recycle the visitor from the factory
	 */
	public void recycle(Visitor visitor) {
		logger.debug("~~~~~Release Visitor~~~~~~~");
		proceedingVisitorQueue.remove(visitor);
		freeVisitorQueue.push(visitor);
	}

	/**
	 * @return
	 */
	public int getProceedingQueueSize() {
		return proceedingVisitorQueue.size();
	}

	/**
	 * the limit line of visitor initial
	 * @return
	 */
	public boolean isVisitorLimited() {
		if (countofinitial < limitVisitor) {
			return false;
		} else
			return true;
	}

	/**
	 * @return empty-true
	 */
	public boolean isFreeVisitorQueueEmpty() {
		if (freeVisitorQueue.size() == 0) {
			return true;
		} else
			return false;
	}

	/**
	 * @return
	 */
	public VisitorQueue getProceedingQueue() {
		return proceedingVisitorQueue;
	}

	/**
	 * 
	 */
	public VisitorQueue getFreeVisitorQueue() {
		return freeVisitorQueue;
	}

	/**
	 * 
	 */
	public void destoryAllVisitors() {
		Iterator<Object> pi = proceedingVisitorQueue.iterator();
		while(pi.hasNext()){
			Visitor v = (Visitor) pi.next();
			v = null;
		}
		Iterator<Object> fi = freeVisitorQueue.iterator();
		while(fi.hasNext()){
			Visitor v = (Visitor) fi.next();
			v = null;
		}
	}

}
