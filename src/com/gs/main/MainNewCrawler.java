/**
 * gaoshen
 */
package com.gs.main;
import org.apache.log4j.Logger;

import com.gs.crawler.NewCrawler;

/**
 * @author GaoShen
 * @packageName com.gs.main
 */
public class MainNewCrawler {
	private Logger logger = Logger.getLogger(this.getClass());
	public static void main(String[] args) {
		NewCrawler c  =new NewCrawler();
		c.c();
	}
}
