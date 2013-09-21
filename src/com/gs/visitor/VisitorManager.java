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
	 * 
	 */
	public VisitorManager(Property property,DownloadManager downloadManager) {
		this.property = property;
		this.downloadManager = downloadManager;
		downloadManager.start();
		this.topN = property.topN;
		this.deepth = property.deepth;
		int i = 0;
		for (String url : property.seeds) {
			queue.push(new URL(url, 1));
			i++;
		}
		factory = new VisitorFactory(property, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				if (!queue.isQueueEmpty()) {
					if (factory.isFreeVisitorQueueEmpty()&&factory.isVisitorLimited()) {
						Thread.sleep(1000);
					}else{
						count++; // the title of docs
						currentVisitor = factory.getVisitor();
						VisitConf conf = new VisitConf(queue.pop(), currentVisitor);
						VisitThread visitThread = new VisitThread();
						visitThread.setConf(conf);
						visitThread.start();
					}
				}
				if (queue.size() == 0 && factory.getProceedingQueueSize() == 0
						&& finish) {
					System.out.println("Queue Size : " + queue.size()
							+ "Proceeding Queue : "
							+ factory.getProceedingQueueSize());
					break;
				} // the condition to shutdown the manager
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		logger.info("=======!!!!!!!!!!!!!!Visitor Manager ShutDown!!!!!!!!!!=========");
		downloadManager.setFetchAllDone(true);
		
		
		
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public void add(URL url) {
		queue.push(url);
		downloadManager.add(url.url);
	}

	public void test() {
		System.out.println("Queue Size : " + queue.size()
				+ "Proceeding Queue : " + factory.getProceedingQueueSize());
	}

}
