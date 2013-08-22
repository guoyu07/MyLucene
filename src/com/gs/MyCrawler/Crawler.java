/**
 * 
 */
package com.gs.MyCrawler;

import java.util.Iterator;
import java.util.List;

import com.gs.extractor.MyLinkExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class Crawler {
	private int deepth = 3;
	public void crawl(String url){
		Queue q = new Queue();
		URL starturl = new URL();
		starturl.level = 1;
		starturl.url = url;
		q.enQueue(starturl);
		URL u;
		while(!q.empty()){
			u = q.deQueue();
			if(u.level<deepth){
				List<URL> list = MyLinkExtractor.extractor(u);
				Iterator<URL> iterator = list.iterator();
				while(iterator.hasNext()){
					q.enQueue(iterator.next());
				}
				DownLoader.down(u);
			}else{
				DownLoader.down(u);
			}
		}
	}
}
