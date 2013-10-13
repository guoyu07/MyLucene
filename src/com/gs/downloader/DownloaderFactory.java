/**
 * GS
 */
package com.gs.downloader;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.gs.DAO.DAO;
import com.gs.crawler.Property;

/**
 * create downloader and schedu the queue
 * 
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloaderFactory {
	private Logger logger = Logger.getLogger(this.getClass());
	private DownloaderQueue proceedingqueue = new DownloaderQueue();
	/**
	 * @return the proceedingqueue
	 */
	public DownloaderQueue getProceedingqueue() {
		return proceedingqueue;
	}

	private DownloaderQueue freequeue = new DownloaderQueue();
	/**
	 * @return the freequeue
	 */
	public DownloaderQueue getFreequeue() {
		return freequeue;
	}

	private String docpath;
	private String mergefile;
	private int countofinitial = 0; // a counter of initial downloader
	private Property property;

	/**
	 * If there are some free downloaders,return a free one.othervise initial a
	 * new one and add it to the proceeding queue
	 * 
	 * @return
	 */
	public Downloader getDownloader() {
		Downloader current;
		if (freequeue.isQueueEmpty()) { // there is a free one
			logger.debug("===========A new downloader is initialed!=============");
			current = new Downloader(property, this,countofinitial);
			countofinitial++;
			proceedingqueue.push(current); // move it to proceeding queue
			logger.debug("Total initial : " + countofinitial
					+ "\nFree Downloader : " + freequeue.size()
					+ "\nProceeding Downloader : " + proceedingqueue.size());
		} else {
			logger.debug("-----------Use Old Downloader!-------------");
			try {
				current = freequeue.pop(); // use a free one
			} catch (NoSuchElementException e) {
				current = new Downloader(property, this,countofinitial);
				countofinitial++;
				proceedingqueue.push(current); // move it to procedding queue
				logger.debug("Total initial : " + countofinitial
						+ "\nFree Downloader : " + freequeue.size()
						+ "\nProceeding Downloader : " + proceedingqueue.size());
				return current;
			}
			proceedingqueue.push(current);
			freequeue.remove(current); // remove it from the freequeue
			logger.debug("Total initial : " + countofinitial
					+ "\nFree Downloader : " + freequeue.size()
					+ "\nProceeding Downloader : " + proceedingqueue.size());
		}
		return current;
	}

	/**
	 * @param docpath
	 * @param mergefile
	 */
	public DownloaderFactory(Property property) {
		this.docpath = property.docfile;
		this.mergefile = property.mergefile;
		this.property = property;
	}

	/**
	 * @return
	 */
	public boolean isProceedingQueueEmpty() {
		return proceedingqueue.isQueueEmpty();
	}

	/**
	 * If a downloader is finished its job remove it from proceeding queue add
	 * it to the free queue
	 * 
	 * @param downloader
	 */
	public void releaseDownloader(Downloader downloader) {

		logger.debug("~~~~~~~~~~~Release Downloader~~~~~~~~~~");
		proceedingqueue.remove(downloader);
		freequeue.push(downloader);
	}

	/**
	 * @return
	 */
	public int getFreeDownloaderNum() {
		return freequeue.size();
	}

	/**
	 * @return
	 */
	public int getProceedingNum() {
		return proceedingqueue.size();
	}
	
	public boolean isDownloaderLimited(){
		if(countofinitial>30){
			return true;
		}else return false;
	}

	/**
	 * @return
	 */
	public DownloaderQueue getProceedingQueue() {
		return proceedingqueue;
	}

}
