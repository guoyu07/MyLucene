package com.gs.crawler;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.Lucene.Indexer;
import com.gs.downloader.DownloadManager;
import com.gs.extractor.MyLinkExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class Crawler {
	private int deepth = 3;
	private int topN = 2;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * default deepth is 3 and the topN is 2<br>
	 * First it will crawl the webpages ,and down them to local.Then it will
	 * index them.
	 * 
	 * @param property
	 *            some property of the crawler
	 */
	public int crawl(Property property) {
		logger.info("Crawler Start");
		this.deepth = property.deepth;
		this.topN = property.topN;
		ConnectionTest tester = new ConnectionTest(); //It's a tester of url 
		DownloadManager downloadmanager = new DownloadManager(property.docfile,property.mergefile);
		FetchQueue q = new FetchQueue();
		URL starturl = new URL();
		downloadmanager.start();
		for (String currentURL : property.seeds) { //currentURL is the initial url which is given by the user
			if (!q.isQueueEmpty()) {
				q.empty();
			}
			starturl.level = 1;
			starturl.url = currentURL;
			q.push(starturl);
			URL u;
			while (!q.isQueueEmpty()) { //the cycle of crawl
				u = q.pop();
				if (!tester.test(u.url, 5000)) { //In order to avoid bad links
					continue;
				}
				if (u.level < deepth) {
					List<URL> list = MyLinkExtractor.extractor(u, topN);
					Iterator<URL> iterator = list.iterator();
					while (iterator.hasNext()) {
						if(u.url.length()>220) continue; //In order to avoid data too long exception
						q.push(iterator.next());
					}
					downloadmanager.add(u.url);
				} else {
					downloadmanager.add(u.url);
				}
			}
		}
		downloadmanager.setFetchAllDone(true);
		if (property.needsIndex) { //start index
			Indexer indexer = new Indexer();
			indexer.index(property.Indexfile, property.docfile);
		}
		/*File docfile = new File(property.docfile);
		for(File f : docfile.listFiles()){ //delete the file of docs
			f.delete();
		}
		docfile.delete();//delete the docfile directory 
*/		if(downloadmanager.isAlive()){
				logger.info("Download have not been done.        Wait!");
				while (downloadmanager.isAlive()) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
		logger.info("Proceeding Downloader : "+downloadmanager.proceedingNum());
		
		return downloadmanager.count; //total pages of down
	}
}
