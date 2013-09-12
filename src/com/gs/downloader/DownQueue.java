/**
 * GS
 */
package com.gs.downloader;

import com.gs.utils.Queue;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownQueue extends Queue {

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
