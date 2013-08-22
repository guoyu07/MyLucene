/**
 * 
 */
package com.gs.main;

import com.gs.MyCrawler.Crawler;

/**
 * @author GaoShen
 * @packageName com.gs.main
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Crawler c = new Crawler();
		//c.crawl("http://localhost:8080/webpage");
		c.crawl("http://news.qq.com",999999,999999);
	}

}
