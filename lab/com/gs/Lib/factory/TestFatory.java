/**
 * GS
 */
package com.gs.Lib.factory;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gs.model.Page;
/**
 * @author GaoShen
 * @packageName com.gs.Lib.factory
 */
public class TestFatory {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		
		Factory<PageProduct, PageConf> f = new Factory(PageProduct.class,3,new Comparator<PageProduct>());
		for (int i = 0; i < 5; i++) {
			try {
				f.getProduct().run();
			} catch (InstantiationException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
}
