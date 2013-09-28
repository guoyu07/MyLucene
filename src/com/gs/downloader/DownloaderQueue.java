/**
 * GS
 */
package com.gs.downloader;

import org.apache.log4j.Logger;

import com.gs.utils.Queue;

/**
 * a queue fro downloader
 * 
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloaderQueue extends Queue {
	private Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#push(java.lang.Object)
	 */
	@Override
	public boolean push(Object downloader) {
		return super.push((Downloader) downloader);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#pop()
	 */
	@Override
	public Downloader pop() {
		return (Downloader) super.pop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#isQueueEmpty()
	 */
	@Override
	public boolean isQueueEmpty() {
		return super.isQueueEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#empty()
	 */
	@Override
	public void empty() {
		super.empty();
	}

}
