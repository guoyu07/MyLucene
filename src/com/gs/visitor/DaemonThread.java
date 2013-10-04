/**
 * GS
 */
package com.gs.visitor;
import java.util.Iterator;

import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class DaemonThread extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private VisitorManager manager;
	private VisitorQueue proceedingQueue;
	
	public void run() {
		this.proceedingQueue = manager.getProceedingQueue();
		while(!proceedingQueue.isQueueEmpty()&&!manager.isQueueEmpty()){
			Iterator<Object> it = proceedingQueue.iterator();
			while(it.hasNext()){
				Visitor v = (Visitor) it.next();
				if((System.currentTimeMillis() - v.getStartTime())>10000){
					v.destory();
				}
			}
		}
	}
	
	public void setVisitorManager(VisitorManager manager){
		this.manager = manager;
	} 
}
