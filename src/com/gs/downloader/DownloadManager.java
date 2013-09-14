/**
 * GS
 */
package com.gs.downloader;

import com.gs.DAO.DAO;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloadManager extends Thread {
	private DownQueue queue;
	private Downloader downloader;
	private Schedular schedular;
	private DownloaderFactory downloadfactory;
	private Downloader currentDownloader;
	private boolean fetchAllDone = false;

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
		new DAO().create();
	}

	public static int count = 0;

	public boolean add(String u) {
		queue.push(u);
		return true;
	}

	public void run() {
		while (true) {
			if (!queue.isQueueEmpty()) {
				count++;
				currentDownloader = downloadfactory.getDownloader();
				DownConf conf = new DownConf(queue.pop(), schedular.getPath(), count,currentDownloader);
				DownThread downThread = new DownThread();
				downThread.setConf(conf);
				downThread.start();
			}
			if(queue.isQueueEmpty()&&downloadfactory.isProceedingQueueEmpty()&&fetchAllDone){break;}
		}
		System.out.println("~~~~~!!!!!!!!!!!!!!Manager ShutDown!!!!!!!!!!~~~~~~~");
	}
	
	public boolean isAllDownloaderFree(){
		return downloadfactory.isProceedingQueueEmpty();
	}
	
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
