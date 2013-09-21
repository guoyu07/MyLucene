/**
 * GS
 */
package com.gs.visitor;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.gs.crawler.Property;
import com.gs.downloader.Downloader;

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
		if (freeVisitorQueue.isQueueEmpty() /* && countofinitial < 50 */) { // there
																			// is
																			// a
																			// free
																			// one
			logger.info("===========A new Visitor is initialed!=============");
			current = new Visitor(property, this, manager);
			countofinitial++;
			proceedingVisitorQueue.push(current); // move it to proceeding queue
			logger.info("Total initial : " + countofinitial
					+ "\nFree Downloader : " + freeVisitorQueue.size()
					+ "\nProceeding Downloader : "
					+ proceedingVisitorQueue.size());
		} else {
			logger.info("-----------Use Old Visitor!-------------");
			boolean NoSuchElementFlag = false;
			try {
				current = freeVisitorQueue.pop();
				// use a free one
			} catch (NoSuchElementException e) {
				current = new Visitor(property, this, manager);
				countofinitial++;
				proceedingVisitorQueue.push(current);// move it to procedding queue
				NoSuchElementFlag = true;
			}
			if (!NoSuchElementFlag) {
				proceedingVisitorQueue.push(current);
				freeVisitorQueue.remove(current); // remove it from the freequeue
			}
			logger.info("Total initial : " + countofinitial
					+ "\nFree Visitor : " + freeVisitorQueue.size()
					+ "\nProceeding Visitor : " + proceedingVisitorQueue.size());
		}
		return current;
	}

	/**
	 * @return
	 */
	public boolean isProceedingQueueEmpty() {
		return proceedingVisitorQueue.isQueueEmpty();
	}

	/**
	 * @param visitor
	 */
	public void recycle(Visitor visitor) {
		logger.info("~~~~~Release Visitor~~~~~~~");
		proceedingVisitorQueue.remove(visitor);
		freeVisitorQueue.push(visitor);
	}

	public int getProceedingQueueSize() {
		return proceedingVisitorQueue.size();
	}

	/**
	 * @return
	 */
	public boolean isVisitorLimited() {
		if (countofinitial<30) {
			return false;
		}else return true;
	}

	/**
	 * @return
	 */
	public boolean isFreeVisitorQueueEmpty() {
		if (freeVisitorQueue.size() == 0) {
			return true;
		}else return false;
	}

}
