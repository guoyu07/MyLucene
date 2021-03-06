package com.gs.utils;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Queue {
	// 使用链表实现队列
	private Logger logger = Logger.getLogger(this.getClass());
	private LinkedList queue = new LinkedList();

	// 入队列
	/**
	 * @param url
	 * @return true-has not been crawl,false-has already been crawl
	 */
	public boolean push(Object obj) {
		queue.addLast(obj);
		return true;
	}

	// 出队列
	/**
	 * @return the url on the top of the queue
	 */
	public Object pop() {
		return (Object) queue.removeFirst();
	}

	// 判断队列是否为空
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
