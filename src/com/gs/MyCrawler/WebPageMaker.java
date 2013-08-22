package com.gs.MyCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;



public class WebPageMaker {

	public static void make(String title,String content) throws IOException {
		try {
			//File file = new File("D:\\Test\\WebPages\\"+title+".htm");
			File file = new File("/home/master/test/WebPages/"+title+".txt");
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
		} catch (FileNotFoundException e) {
			System.out.println("Some Error with the title");
		}catch (IOException e){
			System.out.println("Some IO Error");
		}
	}

	
}
