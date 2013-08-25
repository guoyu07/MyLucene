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

/**
 * @author GaoShen
 * @packageName com.gs.io
 */
public class ContentWriter {
	public long startoffset;
	public long endoffset;
	public void write(String path,String content){
		try {
			File file = new File(path);
			startoffset = file.length();
			FileWriter fw = new FileWriter(file,true);
			fw.write(content);
			fw.close();
			endoffset = file.length();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
