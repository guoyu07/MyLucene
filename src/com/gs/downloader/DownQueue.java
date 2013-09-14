/**
 * GS
 */
package com.gs.downloader;

import org.apache.log4j.Logger;

import com.gs.utils.Queue;

/**
 * the url which to be down
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownQueue extends Queue {
	private Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#push(java.lang.Object)
	 */
	@Override
	public boolean push(Object url) {
		return super.push((String) url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#pop()
	 */
	@Override
	public String pop() {
		return (String) super.pop();
	}

}
