/**
 * GS
 */
package com.gs.downloader;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloadManager extends Thread {
	private DownQueue queue;
	private Downloader downloader;
	private Downloader downloader2;
	private Schedular schedular;

	/**
	 * @param schedular
	 */
	public DownloadManager(String path, String mergefile) {
		schedular = new Schedular(mergefile);
		downloader = new Downloader(path, mergefile);
		downloader2 = new Downloader(path, mergefile);
		queue = new DownQueue();
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
				downloader.down(queue.pop(), schedular.getPath(), count);
			}
		}
	}
	
	public Status getDownloaderStatus(){
		return downloader.getStatus();
	}
	
	public boolean isQueueEmpty(){
		return queue.isQueueEmpty();
	}
}
