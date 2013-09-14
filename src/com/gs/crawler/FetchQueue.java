package com.gs.crawler;

import java.util.LinkedList;

import com.gs.utils.Queue;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class FetchQueue extends Queue{
	private BloomFilter filter = new BloomFilter(0.1, 99999);

	// 入队列
	/**
	 * @param url
	 * @return true-has not been crawl,false-has already been crawl
	 */
	public boolean push(URL url) {
		if (!filter.contains(url.url)) {
			filter.add(url.url);
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
