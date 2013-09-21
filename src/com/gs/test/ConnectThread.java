/**
 * GS
 */
package com.gs.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class ConnectThread extends Thread {
	private String url;

	public void run() {
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		try {
			int code = hc.executeMethod(get);
			System.out.println("CODE " + code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		get.releaseConnection();
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
