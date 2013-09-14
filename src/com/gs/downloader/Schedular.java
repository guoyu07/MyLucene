/**
 * GS
 */
package com.gs.downloader;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * the merge schedular
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Schedular {
	private Logger logger = Logger.getLogger(this.getClass());
	private String path;
	private int count = 0;
	private int limit = 10485760; // 10m

	/**
	 * @param mergefile
	 */
	public Schedular(String mergefile) {
		this.path = mergefile;
	}

	/**
	 * @return
	 */
	public String getPath() {
		File merge = new File(path + "merge" + count + ".txt");
		if (!merge.exists())
			try {
				merge.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (merge.length() > limit)
			count++;
		return merge.getAbsolutePath();
	}

}
