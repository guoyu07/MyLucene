package com.gs.MyCrawler;

import java.util.LinkedList;

public class Queue {
	// ʹ������ʵ�ֶ���
	private LinkedList queue = new LinkedList();
	private BloomFilter filter =  new BloomFilter();

	// �����
	public boolean enQueue(URL url) {
		if (filter.exist(url.url)) {
			return false;
		} else {
			queue.addLast(url);
			return true;
		}

	}

	// ������
	public URL deQueue() {
		return (URL) queue.removeFirst();
	}

	// �ж϶����Ƿ�Ϊ��
	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public boolean empty() {
		return queue.isEmpty();
	}
}
