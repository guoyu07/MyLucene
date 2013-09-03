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
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.DAO.PageDAO;
import com.gs.extractor.ContentExtractor;
import com.gs.extractor.TitleExtractor;
import com.gs.io.ContentWriter;
import com.gs.model.Page;

/**
 * <strong>Remember to close!<strong>
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class Downloader {
	public static int count = 0;
	private String path;
	private ContentWriter cw; 
	private PageDAO dao;
	private Page p = new Page();
	/**
	 * @param path the file to store docs
	 * @param mergefile to store merge doc
	 */
	public Downloader(String path,String mergefile){
		this.path = path;
		cw = new ContentWriter(mergefile);
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		dao = (PageDAO) ctx.getBean("pageDAO");
		//dao.clear(); Can't do clear like this!
	}
	/**
	 * @param u
	 *            the url of the page which you want to down
	 * @param path
	 *            the path to save the webpage
	 */
	public void down(URL u) {
		try {
			//String title = TitleExtractor.extractor(u.url);
			String title = String.valueOf(count);
			/*ContentExtractorThread thread = new ContentExtractorThread();
			thread.setUrl(u.url);
			thread.start();
			int time = 10000;
			long start = System.currentTimeMillis();
			int use =0;
			while(use < time ){
				long now = System.currentTimeMillis();
				use = (int) (now - start);
				if (use%1000 == 0) {
					System.out.println("USE TIME:" + use);
				}
				if(!thread.isAlive()){break;}
			}
			if (thread.isAlive()) {
				thread.stop();
				return;
			}
			thread.join();
			String content = thread.getResult();*/
			String content = ContentExtractor.extractor(u.url);
			cw.write(content);
			p.setEndoffset(cw.endoffset);
			p.setId(count);
			p.setStartoffset(cw.startoffset);
			p.setUrl(u.url);
			dao.save(p);
			if (make(title, content))
				System.out.println("Downloading   " + title);
		} catch (IOException e) {
			e.printStackTrace();
		}
		count++;
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
	private boolean make(String title, String content)
			throws IOException {
		try {
			File file = new File(path + title);
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
