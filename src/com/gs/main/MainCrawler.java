package com.gs.main;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import org.apache.commons.io.FileUtils;

import com.gs.crawler.Crawler;
import com.gs.crawler.OS;
import com.gs.crawler.Property;

/**
 * GS
 */

/**
 * @author GaoShen
 * @packageName 
 */
public class MainCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Property p = new Property(100,100,OS.Linux,"/root/Test", true);
		Crawler c = new Crawler();
		double count = c.crawl(p);
		double use = (System.currentTimeMillis()-start)/1000;
		double speed = count/use;
		String report = "Start time is  "+new Date(start).toLocaleString()+"\nFinish time is  "+new Date(System.currentTimeMillis()).toLocaleString()+"\nTotal use  "+use+"s"+"\nSpeed  "+speed+" pages/s";
		try {
			FileUtils.writeStringToFile(new File(p.path+"/report.txt"), report);
			System.out.println(report);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
