/**
 * GS
 */
package com.gs.visitor;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gs.crawler.Property;
import com.gs.downloader.DownloadManager;
import com.gs.test.NewCrawler;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class VisitorMangerTest {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		Property p = new Property("D://Test//conf.xml");
		DownloadManager dm = new DownloadManager(p);
		VisitorManager m = new VisitorManager(p,dm);
		m.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		m.setFinish(true);
		int i=0;
		while (m.isAlive()) {
			try {
				Thread.sleep(5000);
				i++;
				System.out.println("wait");
				m.test();
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			//if(i > 5)break;
		}
		
		
		if (dm.isAlive()) {
			logger.info("Download have not been done.        Wait!");
			int i1 = 0;
			while (dm.isAlive()) {
				try {
					Thread.sleep(1000);
					i1++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/*if (i1 > 10)
					break; // downloader manager timeout,proceed ,exit the
							// program forcibly
*/			}
		}
		logger.info("=======!!!!!!!!!!!!!!Downloader Manager ShutDown!!!!!!!!!!=========");

	}
	
	@Test
	public void testJUnit(){
		String st = null;
		//st.charAt(0);
		assertNotNull(st);
	}
	
	
}
