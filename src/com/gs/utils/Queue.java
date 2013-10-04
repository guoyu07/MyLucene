package com.gs.utils;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Queue {
	// ʹ������ʵ�ֶ���
	private Logger logger = Logger.getLogger(this.getClass());
	private LinkedList queue = new LinkedList();

	// �����
	/**
	 * @param url
	 * @return true-has not been crawl,false-has already been crawl
	 */
	public boolean push(Object obj) {
		queue.addLast(obj);
		return true;
	}

	// ������
	/**
	 * @return the url on the top of the queue
	 */
	public Object pop() {
		return (Object) queue.removeFirst();
	}

	// �ж϶����Ƿ�Ϊ��
	/**
	 * @return true-empty false-not empty
	 */
	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Very Dangerous!
	 */
	public void empty() {
		queue.clear();
	}

	public void remove(Object obj) {
		queue.remove(obj);
	}

	public int size() {
		return queue.size();
	}
	public Iterator<Object> iterator(){
		return queue.iterator();
	}
	
	public boolean contains(Object obj){
		return queue.contains(obj);
	}
}
