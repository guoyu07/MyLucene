/**
 * GS
 */
package com.gs.utils;

import java.io.File;

/**
 * @author GaoShen
 * @packageName com.gs.utils
 */
public class Path {
	public static String path;

	public static String adaptPath(String path) {
		String se = new File(new String()).pathSeparator;
		if (System.getProperty("os.name").startsWith("Windows")) {
			if (!path.endsWith("//"))
				path.concat("//");
		} else if (System.getProperty("os.name").startsWith("Linux")) {
			if (!path.endsWith("/"))
				path.concat("/");
		}
		return path;
	}

}
