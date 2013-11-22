/**
 * GS
 */
package com.gs.Lib.factory;

import org.apache.log4j.Logger;

import com.gs.model.Page;

/**
 * @author GaoShen
 * @param <T>
 * @packageName com.gs.Lib.factory
 */
public class Comparator<T extends PageProduct> implements java.util.Comparator<T> {
	private Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(T o1, T o2) {
		if (o1.getId() == o2.getId()) {
			return 0;
		} else if(o1.getId() > o2.getId()){
			return 1;
		}else {
			return -1;
		}
	}
}
