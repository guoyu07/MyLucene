/**
 * 
 */
package com.gs.main;

import java.util.Date;

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
		long use = 0;
		try {
			Date d = new Date();
			long start = d.getTime();
			Crawler c = new Crawler();
			// c.crawl("http://localhost:8080/webpage");
			c.crawl("http://news.qq.com", 999999, 999999);
			long end = d.getTime();
			use = end - start;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("共耗时" + use + "毫秒");
		}
		System.out.println("共耗时" + use + "毫秒");
	}

}
