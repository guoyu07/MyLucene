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
public class ConnectThread extends Thread{
	private String url;
	public int code;
	private GetMethod get;
	
	public void run(){
		HttpClient hc = new HttpClient();
		get = new GetMethod(url);
		try {
			this.code = hc.executeMethod(get);
			System.out.println("CODE "+code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		get.releaseConnection();
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void releaseConnection(){
		get.releaseConnection();
	}
}
