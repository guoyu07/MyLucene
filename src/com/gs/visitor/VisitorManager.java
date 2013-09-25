/**
 * GS
 */
package com.gs.visitor;

import org.apache.log4j.Logger;

import com.gs.crawler.Property;
import com.gs.crawler.URL;
import com.gs.downloader.DownConf;
import com.gs.downloader.DownThread;
import com.gs.downloader.DownloadManager;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class VisitorManager extends Thread {
	private int topN;
	private int deepth;
	private URL[] starturls;
	private Property property;
	private VisitorFactory factory;
	private UnVisitQueue queue = new UnVisitQueue();
	private int count = 0;
	private Visitor currentVisitor;
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean finish = false;
	private DownloadManager downloadManager;

	/**
	 * @param property
	 * @param downloadManager
	 *            give VisitorManger a downloader to add urls to the downloader
	 *            manager
	 */
	public VisitorManager(Property property, DownloadManager downloadManager) {
		this.property = property;
		this.downloadManager = downloadManager;
		downloadManager.start(); // start the downloader manager
		this.topN = property.topN;
		this.deepth = property.deepth;
		for (String url : property.seeds) {
			queue.push(new URL(url, 1)); // push the initial urls to the unvisit
											// queue
		}
		factory = new VisitorFactory(property, this); // initial the visitor
														// factory.It's a
														// Singleton.
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			if (!queue.isQueueEmpty()) {
				if (factory.isFreeVisitorQueueEmpty()
						&& factory.isVisitorLimited()) { // both the free
															// visitor queue and
															// the number of
															// visitor reach the
															// limit line
					Thread.yield(); 
				} else {
					count++; // the title of docs
					currentVisitor = factory.getVisitor();
					VisitConf conf = new VisitConf(queue.pop(), currentVisitor); //the configur of visitor
					VisitThread visitThread = new VisitThread();//the thread to run the visitor
					visitThread.setConf(conf);
					visitThread.start();
				}
			}
			if (queue.size() == 0 && factory.getProceedingQueueSize() == 0 //there is nothing to visit ,don't have a proceeding visitor and the flag of finish is true
					&& finish) {
				System.out.println("Queue Size : " + queue.size()
						+ "Proceeding Queue : "
						+ factory.getProceedingQueueSize());
				break;
			} // the condition to shutdown the manager
		}
		logger.debug("=======!!!!!!!!!!!!!!Visitor Manager ShutDown!!!!!!!!!!=========");
		downloadManager.setFetchAllDone(true); //shutdown the downloader manager

	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	/**
	 * @param url
	 */
	public void add(URL url) {
		queue.push(url);
		downloadManager.add(url.url);
	}

	public void test() {
		logger.debug("Queue Size : " + queue.size()
				+ "Proceeding Queue : " + factory.getProceedingQueueSize());
	}

}
