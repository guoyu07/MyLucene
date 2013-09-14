/**
 * GS
 */
package com.gs.downloader;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownThread extends Thread {
	private Downloader downloader;
	private DownConf conf;

	public void run(){
		downloader = conf.downloader;
		downloader.down(conf);
	}

	/**
	 * @param conf
	 */
	public void setConf(DownConf conf) {
		this.conf = conf;
	}
}
