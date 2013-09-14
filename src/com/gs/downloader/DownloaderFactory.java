/**
 * GS
 */
package com.gs.downloader;

import java.util.NoSuchElementException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.DAO.DAO;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloaderFactory {
	private DownloaderQueue proceedingqueue = new DownloaderQueue();
	private DownloaderQueue freequeue = new DownloaderQueue();
	private String docpath;
	private String mergefile;
	private int countofinitial = 0;

	/**
	 * @return
	 */
	public Downloader getDownloader() {
		Downloader current;
		if (freequeue.isQueueEmpty()) {
			System.out.println("========================A new downloader is initialed!");
			current = new Downloader(docpath, mergefile,this);
			countofinitial++;
			proceedingqueue.push(current);
			System.out.println("Total initial : "+countofinitial+"\nFree Downloader : "+freequeue.size()+"\nProceeding Downloader : "+proceedingqueue.size());
		} else {
			System.out.println("------------------------Use Old Downloader!");
			try {
				current = freequeue.pop();
			} catch (NoSuchElementException e) {
				current = new Downloader(docpath, mergefile,this);
				countofinitial++;
				proceedingqueue.push(current);
			}
			proceedingqueue.push(current);
			freequeue.remove(current);
			System.out.println("Total initial : "+countofinitial+"\nFree Downloader : "+freequeue.size()+"\nProceeding Downloader : "+proceedingqueue.size());
		}
		return current;
	}

	/**
	 * @param docpath
	 * @param mergefile
	 */
	public DownloaderFactory(String docpath, String mergefile) {
		this.docpath = docpath;
		this.mergefile = mergefile;
	}
	/**
	 * @return
	 */
	public boolean isProceedingQueueEmpty(){
		return proceedingqueue.isQueueEmpty();
	}

	/**
	 * @param downloader
	 */
	public void releaseDownloader(Downloader downloader) {
		
		System.out.println("~~~~~~~~~~~Release~~~~~~~~~~");
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
	
	

}
