/**
 * GS
 */
package com.gs.test;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestThread extends Thread {
	public void run(){
System.out.println("Doing!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
