/**
 * 
 */
package com.gs.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author GaoShen
 * @packageName com.gs.util
 */
public class Printer {
	public void print(String content){
		try {
			File file = new File("D:\\urls.txt");
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			System.out.println(content);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
