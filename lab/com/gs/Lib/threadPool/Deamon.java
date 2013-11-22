/**
 * GS
 */
package com.gs.Lib.threadPool;
import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.threadPool
 */
public class Deamon extends Thread{
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void run() {
		try {
			System.out.println("Start");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
}
