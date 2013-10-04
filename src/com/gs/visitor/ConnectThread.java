/**
 * GS
 */
package com.gs.visitor;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class ConnectThread extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
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
		this.setName("Connect Test Thread");
		HttpClient hc = new HttpClient();
		get = new GetMethod(url);
		try {
			this.code = hc.executeMethod(get);
			if (code != 200)
				error = true;
			logger.debug("CODE " + code);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try {
			get.releaseConnection();
		} catch (Exception e) {
			this.error = true;
			this.interrupt();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
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
