/**
 * GS
 */
package com.gs.Lib.factory;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.Lib.factory
 */
public interface Product {
	public boolean destory();

	public <E extends Conf> void setConf(final E conf);

	public long getStartTime();

	public void run();

	public Object getInstance();
}
