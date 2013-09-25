/**
 * GS
 */
package com.gs.test;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gs.crawler.Property;
import com.gs.downloader.DownloadManager;
import com.gs.visitor.VisitorManager;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class NewCrawler {
	private Logger logger = Logger.getLogger(this.getClass());

	public void c(String confXMLPath) {
		long start = System.currentTimeMillis();
		Property p = new Property(confXMLPath);
		DownloadManager dm = new DownloadManager(p);
		VisitorManager m = new VisitorManager(p, dm);
		m.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		m.setFinish(true);
		int i = 0;
		while (m.isAlive()) {
			try {
				Thread.sleep(5000);
				i++;
				logger.error("Visitor Manager have not been done.       Please Wait!  "+i);
				m.test();
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			//if (i > 5)				break;
		}

		if (dm.isAlive()) {
			logger.error("Download have not been done.        Please Wait!");
			int i1 = 0;
			while (dm.isAlive()) {
				try {
					Thread.sleep(1000);
					i1++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//if (i1 > 10)	break; // downloader manager timeout,proceed ,exit the
							// program forcibly
			}
		}
		double count = dm.count;
		double use = (System.currentTimeMillis() - start) / 1000;
		double speed = count / use;
		String report = "Start time is  " + new Date(start).toLocaleString()
				+ "\nFinish time is  "
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "\nTotal use  " + use + "s" + "\nSpeed  " + speed
				+ " pages/s";
		try {
			FileUtils.writeStringToFile(new File(p.path + "/report.txt"),
					report);
			logger.info(report);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.gc();
		//System.exit(0);
	}
	
	public void c() {
		System.out.println("Please Input the path of conf.xml\n");
		Scanner s = new Scanner(System.in);
		String confXMLPath = s.nextLine();
		long start = System.currentTimeMillis();
		Property p = new Property(confXMLPath);
		DownloadManager dm = new DownloadManager(p);
		VisitorManager m = new VisitorManager(p, dm);
		m.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		m.setFinish(true);
		int i = 0;
		while (m.isAlive()) {
			try {
				Thread.sleep(5000);
				i++;
				logger.error("Visitor Manager have not been done.       Please Wait!  "+i);
				m.test();
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			//if (i > 5)				break;
		}

		if (dm.isAlive()) {
			logger.error("Download have not been done.        Please Wait!");
			int i1 = 0;
			while (dm.isAlive()) {
				try {
					Thread.sleep(1000);
					i1++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//if (i1 > 10)	break; // downloader manager timeout,proceed ,exit the
							// program forcibly
			}
		}
		double count = dm.count;
		double use = (System.currentTimeMillis() - start) / 1000;
		double speed = count / use;
		String report = "Start time is  " + new Date(start).toLocaleString()
				+ "\nFinish time is  "
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "\nTotal use  " + use + "s" + "\nSpeed  " + speed
				+ " pages/s";
		try {
			FileUtils.writeStringToFile(new File(p.path + "/report.txt"),
					report);
			logger.info(report);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.gc();
		//System.exit(0);
	}

}
