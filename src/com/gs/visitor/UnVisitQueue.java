/**
 * GS
 */
package com.gs.visitor;

import org.apache.log4j.Logger;

import com.gs.crawler.URL;
import com.gs.utils.Queue;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class UnVisitQueue extends Queue {
	private Logger logger = Logger.getLogger(this.getClass());
	private BloomFilter filter = new BloomFilter(0.1, 99999);

	// 入队列
	/**
	 * @param url
	 * @return true-has not been crawl,false-has already been crawl
	 */
	public boolean push(URL url) {
		if (!filter.contains(url.url)) {
			filter.add(url.url);
			logger.debug("Already fetched! URL " + url.url);
			super.push(url);
			return true;
		} else {
			return false;
		}

	}

	// 出队列
	/**
	 * @return the url on the top of the queue
	 */
	public URL pop() {
		return (URL) super.pop();
	}

}
