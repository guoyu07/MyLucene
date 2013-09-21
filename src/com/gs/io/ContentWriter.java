/**
 * GS
 */
package com.gs.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * <strong>Remember to close!<strong>
 * 
 * @author GaoShen
 * @packageName com.gs.io
 */
public class ContentWriter {
	private Logger logger = Logger.getLogger(this.getClass());
	public long startoffset;
	public long endoffset;
	private File file;
	private FileWriter fw;
	private String path;

	/**
	 * @param content
	 */
	public void write(String content, String path) {
		try {
			file = new File(path);
			fw = new FileWriter(file, true);
			startoffset = file.length();
			fw.write(content);
			fw.close();
			endoffset = file.length();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
