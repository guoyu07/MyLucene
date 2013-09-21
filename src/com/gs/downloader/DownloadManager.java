/**
 * GS
 */
package com.gs.downloader;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.gs.DAO.DAO;
import com.gs.crawler.Property;

/**
 * The main class of the download system
 * 
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloadManager extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private DownQueue queue; // the urls to be downed
	private Schedular schedular; // merge path schedular
	private DownloaderFactory downloadfactory;
	private Downloader currentDownloader;
	private boolean fetchAllDone = false; // a flag of crawl .no more url to be
											// added

	/**
	 * @return the fetchAllDone
	 */
	public boolean isFetchAllDone() {
		return fetchAllDone;
	}

	/**
	 * @param fetchAllDone
	 *            the fetchAllDone to set
	 */
	public void setFetchAllDone(boolean fetchAllDone) {
		this.fetchAllDone = fetchAllDone;
	}

	/**
	 * @param schedular
	 */
	public DownloadManager(Property property) {
		schedular = new Schedular(property.mergefile);
		downloadfactory = new DownloaderFactory(property);
		queue = new DownQueue();
		new DAO(property).create(); // create the table
	}

	public static int count = 0;

	/**
	 * add url to the manager
	 * 
	 * @param u
	 * @return
	 */
	public boolean add(String u) {
		queue.push(u);
		return true;
	}

	public void run() {
		while (true) {
			if (!queue.isQueueEmpty()) {
				try {
					if (downloadfactory.isDownloaderLimited()
							&& downloadfactory.getFreeDownloaderNum() == 0) {
						Thread.sleep(1000);
					} else {
						count++; // the title of docs
						currentDownloader = downloadfactory.getDownloader();
						DownConf conf = null;
						try {
							conf = new DownConf(queue.pop(),
									schedular.getPath(), count,
									currentDownloader);
						} catch (NoSuchElementException e) {
							currentDownloader.recycle();
							conf = null;
							e.printStackTrace();
							logger.error(e.getMessage());
							continue;
						}
						DownThread downThread = new DownThread();
						downThread.setConf(conf);
						downThread.start();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
			if (queue.isQueueEmpty()
					&& downloadfactory.isProceedingQueueEmpty() && fetchAllDone) {
				break;
			} // the condition to shutdown the manager
		}
		logger.info("~~~~~!!!!!!!!!!!!!!Downloader Manager ShutDown!!!!!!!!!!~~~~~~~");
	}

	/**
	 * @return
	 */
	public boolean isAllDownloaderFree() {
		return downloadfactory.isProceedingQueueEmpty();
	}

	/**
	 * is there any url to be download
	 * 
	 * @return
	 */
	public boolean isQueueEmpty() {
		return queue.isQueueEmpty();
	}

	public int freeDownloaderNum() {
		return downloadfactory.getFreeDownloaderNum();
	}

	public int proceedingNum() {
		return downloadfactory.getProceedingNum();
	}

}
