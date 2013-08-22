/**
 * 
 */
package com.gs.MyCrawler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

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
		String content = null;
		try {
			HttpClient hc = new HttpClient();
			GetMethod get = new GetMethod(u.url);
			int code = hc.executeMethod(get);
			content = get.getResponseBodyAsString();
			get.releaseConnection();
			WebPageMaker.make(TitleExtractor.extractor(u.url), content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
