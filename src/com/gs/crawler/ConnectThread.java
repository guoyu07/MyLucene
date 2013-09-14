/**
 * GS
 */
package com.gs.crawler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class ConnectThread extends Thread {
	private String url;
	public int code;
	private GetMethod get;
	private boolean error;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		HttpClient hc = new HttpClient();
		get = new GetMethod(url);
		try {
			this.code = hc.executeMethod(get);
			if (code != 200)
				error = true;
			System.out.println("CODE " + code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		get.releaseConnection();
	}

	/**
	 * must set the url before start the thread!otherwise it would throw the
	 * NullPotinterException
	 * 
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public void releaseConnection() {
		get.releaseConnection();
	}

	/**
	 * @return
	 */
	public boolean isError() {
		return error;
	}
}
