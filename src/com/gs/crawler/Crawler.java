package com.gs.crawler;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.gs.Lucene.Indexer;
import com.gs.extractor.MyLinkExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class Crawler {
	private int deepth = 3;
	private int topN = 2;

	/**
	 * default deepth is 3 and the topN is 2<br>
	 * First it will crawl the webpages ,and down them to local.Then it will
	 * index them.
	 * 
	 * @param property
	 *            some property of the crawler
	 */
	public void crawl(Property property) {

		this.deepth = property.deepth;
		this.topN = property.topN;
		Downloader downloader = new Downloader(property.docfile,property.mergefile);
		Queue q = new Queue();
		URL starturl = new URL();
		for (String currentURL : property.seeds) {
			if (!q.isQueueEmpty()) {
				q.empty();
			}
			starturl.level = 1;
			starturl.url = currentURL;
			q.enQueue(starturl);
			URL u;
			while (!q.empty()) {
				u = q.deQueue();
				if (u.level < deepth) {
					List<URL> list = MyLinkExtractor.extractor(u, topN);
					Iterator<URL> iterator = list.iterator();
					while (iterator.hasNext()) {
						q.enQueue(iterator.next());
					}
					downloader.down(u);
				} else {
					downloader.down(u);
				}
			}
		}
		downloader.close();
		if (property.needsIndex) {
			Indexer indexer = new Indexer();
			indexer.index(property.Indexfile, property.docfile);
		}
		File docfile = new File(property.docfile);
		for(File f : docfile.listFiles()){
			f.delete();
		}
		docfile.delete();
	}
}
