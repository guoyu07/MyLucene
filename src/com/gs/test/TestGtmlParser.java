/**
 * 
 */
package com.gs.test;

import static org.junit.Assert.*;

import org.htmlparser.parserapplications.LinkExtractor;
import org.junit.Test;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestGtmlParser {

	@Test
	public void test() {
		/*LinkExtractor l  = new LinkExtractor();*/
		String[] s  = new String[7];
		s[0] = "http://news.qq.com";
		LinkExtractor.main(s);
	}

}
