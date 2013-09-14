/**
 * GS
 */
package com.gs.downloader;

import org.apache.log4j.Logger;

import com.gs.DAO.DAO;

/**
 * The main class of the download system
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloadManager extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private DownQueue queue; //the urls to be downed
	private Schedular schedular; //merge path schedular
	private DownloaderFactory downloadfactory;
	private Downloader currentDownloader;
	private boolean fetchAllDone = false; //a flag of crawl .no more url to be added

	/**
	 * @return the fetchAllDone
	 */
	public boolean isFetchAllDone() {
		return fetchAllDone;
	}

	/**
	 * @param fetchAllDone the fetchAllDone to set
	 */
	public void setFetchAllDone(boolean fetchAllDone) {
		this.fetchAllDone = fetchAllDone;
	}

	/**
	 * @param schedular
	 */
	public DownloadManager(String docpath, String mergefile) {
		schedular = new Schedular(mergefile);
		downloadfactory = new DownloaderFactory(docpath, mergefile);
		queue = new DownQueue();
		new DAO().create(); //create the table
	}

	public static int count = 0;

	/**
	 * add url to the manager
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
				count++; //the title of docs
				currentDownloader = downloadfactory.getDownloader();
				DownConf conf = new DownConf(queue.pop(), schedular.getPath(), count,currentDownloader);
				DownThread downThread = new DownThread();
				downThread.setConf(conf);
				downThread.start();
			}
			if(queue.isQueueEmpty()&&downloadfactory.isProceedingQueueEmpty()&&fetchAllDone){break;} //the condition to shutdown the manager
		}
		logger.info("~~~~~!!!!!!!!!!!!!!Manager ShutDown!!!!!!!!!!~~~~~~~");
	}
	
	/**
	 * @return
	 */
	public boolean isAllDownloaderFree(){
		return downloadfactory.isProceedingQueueEmpty();
	}
	
	/**
	 * is there any url to be download
	 * @return
	 */
	public boolean isQueueEmpty(){
		return queue.isQueueEmpty();
	}
	
	public int freeDownloaderNum(){
		return downloadfactory.getFreeDownloaderNum();
	}
	
	public int proceedingNum(){
		return downloadfactory.getProceedingNum();
	}
	
}
