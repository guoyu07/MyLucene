/**
 * GS
 */
package com.gs.utils;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.utils
 */
public class TimerHook {
	private Thread target;
	private int time;
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * @param target the thread which wanted to be time
	 * @param closeWorker a worker to do some after the target thread interrupteed,must implements the CloseWorker Interface
	 * @param time
	 */
	public TimerHook(Thread target,CloseWorker closeWorker,int time){
		try {
			this.target = target;
			this.time = time;
			target.start();
			long start = System.currentTimeMillis();
			int use = 0;
			while (use < time) {
				long now = System.currentTimeMillis();
				use = (int) (now - start);
				if (!target.isAlive()) {
					break;
				} // if the thread is stoped then stop the timer
			}
			if (target.isAlive()) {// if the thread is timeout ,stop the thread
				target.interrupt();
				logger.error("Interrupted! Timeout");
			}
			target.join();
		} catch (InterruptedException e) {
			closeWorker.close();
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * there will do nothing after timeout
	 * @param target
	 * @param time
	 */
	public TimerHook(Thread target,int time){
		try {
			this.target = target;
			this.time = time;
			target.start();
			long start = System.currentTimeMillis();
			int use = 0;
			while (use < time) {
				long now = System.currentTimeMillis();
				use = (int) (now - start);
				if (!target.isAlive()) {
					break;
				} // if the thread is stoped then stop the timer
			}
			if (target.isAlive()) {// if the thread is timeout ,stop the thread
				target.interrupt();
				logger.error("Interrupted! Timeout");
			}
			target.join();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
}
