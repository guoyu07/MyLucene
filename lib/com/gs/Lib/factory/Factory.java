/**
 * GS
 */
package com.gs.Lib.factory;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.Lib.factory
 */
public class Factory<T extends Product, E extends Conf> {
	private Logger logger = Logger.getLogger(this.getClass());
	private static int counter = 0;
	private Queue<T> proceedingQueue;
	private Queue<T> freeQueue;
	private int limit;
	private Class<T> clazz;

	public Factory(final Class<T> clazz,final int limit,final Comparator<T> comparator) {
		proceedingQueue = new PriorityQueue<T>(limit, comparator);
		freeQueue = new PriorityQueue<T>(limit, comparator);
		this.clazz = clazz;
		this.limit = limit;
	}

	/**
	 * This is a Blocking method.
	 * @param conf
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T getProduct(final E conf) throws InstantiationException, 
			IllegalAccessException {
		T re = null;
		while (re == null) {
			if(!freeQueue.isEmpty()){
				re = freeQueue.poll();
				break;
			}
			if (counter <= limit) {
				re = clazz.newInstance();
				re.setConf(conf);
				proceedingQueue.add(re);
				counter++;
			}
		}
		return re;
	}

	/**
	 * This is a Blocking method.
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public T getProduct() throws InstantiationException, IllegalAccessException {
		T re = null;
		while (re == null) {
			if(!freeQueue.isEmpty()){
				re = freeQueue.poll();
				break;
			}
			if (counter <= limit) {
				re = clazz.newInstance();
				proceedingQueue.add(re);
				counter++;
			}
		}
		return re;
	}

	/**
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public boolean recycle(final T t) throws Exception {
		try {
			proceedingQueue.remove(t);
			freeQueue.add(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		}
		return true;
	}
}
