/**
 * 
 */
package com.gs.extractor;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class SubTitle {
	public static String sub(String html) {
		char[] c = html.toCharArray();
		if (c.length < 14)
			return null;
		String result = "";
		if (html.substring(0, 7).equals("<title>"))
			for (int i = 7; i < c.length && c[i] != '<'; i++) {
				result += c[i];
			}
		return result;
	}
}
