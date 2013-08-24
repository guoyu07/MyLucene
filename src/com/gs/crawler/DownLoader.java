/**
 * 
 */
package com.gs.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.gs.extractor.ContentExtractor;
import com.gs.extractor.TitleExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class DownLoader {

	/**
	 * @param u
	 *            the url of the page which you want to down
	 * @param path
	 *            the path to save the webpage
	 */
	public static void down(URL u, String path) {
		try {
			String title = TitleExtractor.extractor(u.url);
			String content = ContentExtractor.extractor(u.url);
			if (make(title, content, path))
				System.out.println("Down   " + title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param title
	 *            title of the file
	 * @param content
	 *            the content of the webpage
	 * @param path
	 *            path to save txts
	 * @return true-has been make false-already exist or catch some exception
	 * @throws IOException
	 */
	private static boolean make(String title, String content, String path)
			throws IOException {
		try {
			File file = new File(path + title + ".htm");
			if (file.exists()||title == null||title == "")
				return false;
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Some Error with the title");
			return false;
		} catch (IOException e) {
			System.out.println("Some IO Error");
			return false;
		} 
	}


}
