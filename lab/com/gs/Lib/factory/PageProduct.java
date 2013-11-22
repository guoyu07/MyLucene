/**
 * GS
 */
package com.gs.Lib.factory;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.Lib.factory
 */
public class PageProduct implements Product {
	private Logger logger = Logger.getLogger(this.getClass());
	private static int id = 0;
	private static final int name = id++;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.Lib.factory.Product#destory()
	 */
	@Override
	public boolean destory() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.Lib.factory.Product#setConf(com.gs.Lib.factory.Conf)
	 */
	@Override
	public <E extends Conf> void setConf(E conf) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.Lib.factory.Product#getStartTime()
	 */
	@Override
	public long getStartTime() {
		return System.currentTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.Lib.factory.Product#run()
	 */
	@Override
	public void run()  {
		System.out.println("Running"+name);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gs.Lib.factory.Product#getInstance()
	 */
	@Override
	public Object getInstance() {
		return null;
	}

	public int getId() {
		return name;
	}
}
