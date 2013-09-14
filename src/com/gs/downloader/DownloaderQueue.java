/**
 * GS
 */
package com.gs.downloader;

import com.gs.utils.Queue;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownloaderQueue extends Queue {

	/* (non-Javadoc)
	 * @see com.gs.utils.Queue#push(java.lang.Object)
	 */
	@Override
	public boolean push(Object downloader) {
		return super.push((Downloader)downloader);
	}

	/* (non-Javadoc)
	 * @see com.gs.utils.Queue#pop()
	 */
	@Override
	public Downloader pop() {
		return (Downloader)super.pop();
	}

	/* (non-Javadoc)
	 * @see com.gs.utils.Queue#isQueueEmpty()
	 */
	@Override
	public boolean isQueueEmpty() {
		return super.isQueueEmpty();
	}

	/* (non-Javadoc)
	 * @see com.gs.utils.Queue#empty()
	 */
	@Override
	public void empty() {
		super.empty();
	}

}
