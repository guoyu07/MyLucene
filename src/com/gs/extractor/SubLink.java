/**
 * 
 */
package com.gs.extractor;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class SubLink {
	public static String sub(String html) {
		char[] c = html.toCharArray();
		if (c.length < 12)
			return null;
		String result = "";
		if (html.substring(0, 13).equals("<a href=\"http"))
			for (int i = 9; i < c.length && c[i] != '"'; i++) {
				result += c[i];
				if (c[i] == '<') {
					result = "";
					break;
				}
			}
		return result;
	}

}
