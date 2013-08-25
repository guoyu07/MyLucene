/**
 * GS
 */
package com.gs.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author GaoShen
 * @packageName com.gs.io
 */
public class ContentReader {
	public String read(String path,long startoffset,long endoffset){
		String content=null;
		File file = new File(path);
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.skip(startoffset);
			byte b;
			byte[] b1 = new byte[(int) (endoffset - startoffset+3)];//In order to avoid stackoverflow
			for(int i=0;(b=(byte) fis.read())!=-1;i++){
				b1[i] = b;
			}
			content = new String(b1);
			
			System.out.println(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
		
	}
}
