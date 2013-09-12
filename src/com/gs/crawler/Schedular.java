/**
 * GS
 */
package com.gs.crawler;

import java.io.File;
import java.io.IOException;

/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Schedular {
	private String path;
	private int count = 0;
	private int limit = 104857600; //100m
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
		File merge = new File(path+"merge"+count+".txt");
		if(!merge.exists())
			try {
				merge.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(merge.length()>limit)count++;
		return merge.getAbsolutePath();
	}

}
