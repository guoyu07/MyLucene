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
public class VisitorQueue extends Queue {
	private Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#push(java.lang.Object)
	 */
	@Override
	public boolean push(Object obj) {
		// TODO Auto-generated method stub
		return super.push(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#pop()
	 */
	@Override
	public Visitor pop() {
		// TODO Auto-generated method stub
		return (Visitor) super.pop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#isQueueEmpty()
	 */
	@Override
	public boolean isQueueEmpty() {
		// TODO Auto-generated method stub
		return super.isQueueEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#empty()
	 */
	@Override
	public void empty() {
		// TODO Auto-generated method stub
		super.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object obj) {
		super.remove(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.utils.Queue#size()
	 */
	@Override
	public int size() {
		return super.size();
	}
	
	public boolean contains(Visitor v){
		return super.contains(v);
	}
}
