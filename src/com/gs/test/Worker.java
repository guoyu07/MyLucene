/**
 * GS
 */
package com.gs.test;

import com.gs.utils.CloseWorker;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class Worker implements CloseWorker {

	@Override
	public boolean close() {
		System.out.println("Close!");
		return false;
	}

}
