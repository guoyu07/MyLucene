package com.gs.crawler;

import java.util.LinkedList;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Queue {
	// 使用链表实现队列
	private LinkedList queue = new LinkedList();
	private BloomFilter filter = new BloomFilter(0.1,99999);

	// 入队列
	/**
	 * @param url
	 * @return true-has not been crawl,false-has already been crawl
	 */
	public boolean enQueue(URL url) {
		if (!filter.contains(url.url)) {
			filter.add(url.url);
			queue.addLast(url);
			return true;
		} else {
			return false;
		}

	}

	// 出队列
	/**
	 * @return the url on the top of the queue
	 */
	public URL deQueue() {
		return (URL) queue.removeFirst();
	}

	// 判断队列是否为空
	/**
	 * @return true-empty false-not empty
	 */
	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public boolean empty() {
		return queue.isEmpty();
	}
}
