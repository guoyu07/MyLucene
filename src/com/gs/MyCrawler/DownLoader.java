/**
 * 
 */
package com.gs.MyCrawler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.gs.extractor.MainExtractor;
import com.gs.extractor.TitleExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class DownLoader {

	/**
	 * @param u
	 */
	public static void down(URL u) {
		try {
			String title = TitleExtractor.extractor(u.url);
			String content = MainExtractor.extractor(u.url);
			WebPageMaker.make(title, content);
			System.out.println("Down"+title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
