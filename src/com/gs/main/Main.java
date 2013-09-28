/**
 * 
 */
package com.gs.main;

import java.util.Date;

import org.apache.log4j.Logger;

import com.gs.crawler.Crawler;
import com.gs.crawler.OS;
import com.gs.crawler.Property;

/**
 * @author GaoShen
 * @packageName com.gs.main
 */
public class Main {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long use = 0;
		try {
			long start = System.currentTimeMillis();
			Property p = new Property("http://news.qq.com", 3, 30, OS.Windows,
					"D://Test", true);
			Crawler c = new Crawler();
			c.crawl(p);
			long end = System.currentTimeMillis();
			use = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("共用时" + use + "毫秒, 折合" + (use / 1000) + "秒");
	}

}
