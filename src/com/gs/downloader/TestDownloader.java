/**
 * GS
 */
package com.gs.downloader;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class TestDownloader {

	@Test
	public void testqueue() {
		DownQueue q = new DownQueue();
		q.push("url1");
		q.push("u2");
		q.push("u3");
		System.out.println(q.pop());
		System.out.println(q.pop());
		System.out.println(q.pop());
		// System.out.println(q.pop());
	}
	
	@Test
	public void testManager(){
		DownloadManager m = new DownloadManager("D://Test//doc//","D://Test//merge//");
		m.start();
		m.add("http://news.qq.com");
		m.add("http://www.taobao.com");
		
		m.add("http://www.baidu.com");
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		while(m.getDownloaderStatus() == Status.Proceeding||!m.isQueueEmpty()){}
	}

}
