/**
 * GS
 */
package com.gs.extractor;

import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class IDFactory {

	private static int id = 0;

	public static String getID() {
		return String.valueOf(id++);
	}

}