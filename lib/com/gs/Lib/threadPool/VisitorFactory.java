/**
 * GS
 */
package com.gs.Lib.threadPool;
import java.util.concurrent.ThreadFactory;

import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.threadPool
 */
public class VisitorFactory implements ThreadFactory {
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param visitor
	 */
	public void recycle(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
