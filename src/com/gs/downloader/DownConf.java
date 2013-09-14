/**
 * GS
 */
package com.gs.downloader;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownConf {
	public Downloader downloader;
	/**
	 * @param url
	 * @param path
	 * @param count
	 */
	public DownConf(String url, String path, int count,Downloader downloader) {
		this.url = url;
		this.path = path;
		this.count = count;
		this.downloader = downloader;
	}
	public String url;
	public String path;
	public int count;
	
}
