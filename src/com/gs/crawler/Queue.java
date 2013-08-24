package com.gs.crawler;

import java.util.LinkedList;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Queue {
	// ʹ������ʵ�ֶ���
	private LinkedList queue = new LinkedList();
	private BloomFilter filter = new BloomFilter();

	public Queue(String bloomfile) {
		filter.con(bloomfile);
	}

	// �����
	/**
	 * @param url
	 * @return true-has not been crawl,false-has already been crawl
	 */
	public boolean enQueue(URL url) {
		if (filter.add(url.url)) {
			queue.addLast(url);
			return true;
		} else {
			return false;
		}

	}

	// ������
	/**
	 * @return the url on the top of the queue
	 */
	public URL deQueue() {
		return (URL) queue.removeFirst();
	}

	// �ж϶����Ƿ�Ϊ��
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
