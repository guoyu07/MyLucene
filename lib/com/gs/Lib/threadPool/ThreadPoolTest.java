/**
 * GS
 */
package com.gs.Lib.threadPool;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
/**
 * @author GaoShen
 * @packageName com.gs.threadPool
 */
public class ThreadPoolTest {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		Deamon d = new Deamon();
		//d.setDaemon(true);
		d.start();
	}
}
