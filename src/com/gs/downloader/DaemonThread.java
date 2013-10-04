/**
 * GS
 */
package com.gs.downloader;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.gs.utils.Status;
/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DaemonThread extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private DownloadManager manager;
	
	public void run() {
		DownloaderQueue proceedingQueue = manager.getProceedingQueue();
		while(!manager.isQueueEmpty()&&!manager.isAllDownloaderFree()){
			Iterator it = proceedingQueue.itertor();
			while(it.hasNext()){
				Downloader d = (Downloader) it.next();
				if((System.currentTimeMillis() - d.getStartTime()) > 10000){
					d.destory();
				}
			}
		}
	}
	
	public void setManager(DownloadManager manager){
		this.manager = manager;
	}
}
