/**
 * GS
 */
package com.gs.downloader;

import org.apache.log4j.Logger;

/**
 * For MultiThread Downloader.Must set the conf before start the Thread.
 * 
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class DownThread extends Thread {
	private Logger logger = Logger.getLogger(this.getClass());
	private Downloader downloader;
	private DownConf conf;

	public void run() {
		this.setName("Downloader Thread");
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
