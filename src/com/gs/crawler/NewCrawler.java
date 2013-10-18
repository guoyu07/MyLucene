/**
 * GS
 */
package com.gs.crawler;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gs.DAO.DAO;
import com.gs.Lucene.Indexer;
import com.gs.TFIDF.CorpusIDF;
import com.gs.crawler.Property;
import com.gs.downloader.DownloadManager;
import com.gs.extractor.IDFactory;
import com.gs.visitor.VisitorManager;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class NewCrawler {
	private Logger logger = Logger.getLogger(this.getClass());

	public void c(String confXMLPath) {
		System.out.println("Please Input the path of conf.xml");
		Property p = new Property(confXMLPath);
		new DAO(p).create();
		//DownloadManager dm = new DownloadManager(p);
		VisitorManager m = new VisitorManager(p/*, dm*/);
		long start = System.currentTimeMillis();
		m.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		m.setFinish(true);
		int i = 0;
		while (!m.isQueueEmpty()/* && !dm.isQueueEmpty()*/) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}

		while (m.isAlive()) {
			try {
				Thread.sleep(1000);
				i++;
				logger.error("Visitor Manager have not been done.       Please Wait!  "
						+ i);
				m.test();
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			 if (i > 10) {m.interrupt();break;}
		}
		//m.destoryAllVisitors();
		m = null;
		System.gc();
		/*if (dm.isAlive()) {
			logger.error("Download have not been done.        Please Wait!");
			int i1 = 0;
			while (dm.isAlive()) {
				try {
					Thread.sleep(1000);
					i1++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 if (i1 > 10) {dm.interrupt();break;} // downloader manager
				// timeout,proceed ,exit the
			}
		}*/
		double count = Double.valueOf(IDFactory.getID());
		double use = (System.currentTimeMillis() - start) / 1000;
		double speed = count / use;
		String report = "Start time is  " + new Date(start).toLocaleString()
				+ "\r\nFinish time is  "
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "\r\nTotal use  " + use + "s" + "\r\nSpeed  " + speed
				+ " pages/s";
		try {
			FileUtils.writeStringToFile(new File(p.path + "/report.txt"),
					report);
			logger.info(report);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (p.needsIndex) { // start index
			Indexer indexer = new Indexer();
			indexer.index(p.Indexfile, p.docfile);
		}
		
		CorpusIDF c = new CorpusIDF();
		c.idf(new File(p.docfile), p.map);
		logger.info("Build map finished!");

		 System.gc();
		// System.exit(0);
	}

	public void c() {
		
		System.out.println("Please Input the path of conf.xml");
		Scanner s = new Scanner(System.in);
		String confXMLPath = s.nextLine();
		Property p = new Property(confXMLPath);
		new DAO(p).create();
		//DownloadManager dm = new DownloadManager(p);
		VisitorManager m = new VisitorManager(p/*, dm*/);
		long start = System.currentTimeMillis();
		m.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		m.setFinish(true);
		int i = 0;
		while (!m.isQueueEmpty()/* && !dm.isQueueEmpty()*/) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}

		while (m.isAlive()) {
			try {
				Thread.sleep(1000);
				i++;
				logger.error("Visitor Manager have not been done.       Please Wait!  "
						+ i);
				m.test();
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			 if (i > 10) {m.interrupt();break;}
		}
		//m.destoryAllVisitors();
		m = null;
		System.gc();
		/*if (dm.isAlive()) {
			logger.error("Download have not been done.        Please Wait!");
			int i1 = 0;
			while (dm.isAlive()) {
				try {
					Thread.sleep(1000);
					i1++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 if (i1 > 10) {dm.interrupt();break;} // downloader manager
				// timeout,proceed ,exit the
			}
		}*/
		double count = Double.valueOf(IDFactory.getID());
		double use = (System.currentTimeMillis() - start) / 1000;
		double speed = count / use;
		String report = "Start time is  " + new Date(start).toLocaleString()
				+ "\r\nFinish time is  "
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "\r\nTotal use  " + use + "s" + "\r\nSpeed  " + speed
				+ " pages/s";
		try {
			FileUtils.writeStringToFile(new File(p.path + "/report.txt"),
					report);
			logger.info(report);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (p.needsIndex) { // start index
			Indexer indexer = new Indexer();
			indexer.index(p.Indexfile, p.docfile);
		}
		
		CorpusIDF c = new CorpusIDF();
		c.idf(new File(p.docfile), p.map);
		logger.info("Build map finished!");

		 System.gc();
		// System.exit(0);
	}

}
