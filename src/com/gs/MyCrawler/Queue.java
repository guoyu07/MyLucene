package com.gs.MyCrawler;

import java.util.LinkedList;

public class Queue {
	// 使用链表实现队列
	private LinkedList queue = new LinkedList();
	private BloomFilter filter = new BloomFilter();

	public Queue() {
		filter.con();
	}

	// 入队列
	public boolean enQueue(URL url) {
		if (filter.add(url.url))
			queue.addLast(url);
		return true;

	}

	// 出队列
	public URL deQueue() {
		return (URL) queue.removeFirst();
	}

	// 判断队列是否为空
	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public boolean empty() {
		return queue.isEmpty();
	}
}
