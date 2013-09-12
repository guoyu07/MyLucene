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
 * the downloader has a counter of the number of download pages.So only init it once of a crawl.
 * @author GaoShen
 * @packageName com.gs.MyCrawler
 */
public class Downloader {
	public static int count = 0;
	private String path;
	private ContentWriter cw; 
	private PageDAO dao;
	private Page p = new Page();
	private Schedular scheduler;
	/**
	 * @param path the file to store docs
	 * @param mergefile to store merge doc
	 */
	public Downloader(String path,String mergefile){
		this.path = path;
		cw = new ContentWriter();
		scheduler = new Schedular(mergefile);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		dao = (PageDAO) ctx.getBean("pageDAO");
		//dao.clear(); //Can't clear the database like this!
	}
	/**
	 * @param u
	 *            the url of the page which you want to down
	 * @param path
	 *            the path to save the webpage
	 */
	public void down(URL u) {
		try {
			//String title = TitleExtractor.extractor(u.url); //the extractor of title
			String title = String.valueOf(count);
			String content = ContentExtractor.extractor(u.url);
			String path = scheduler.getPath();
			cw.write(content,path);
			p.setEndoffset(cw.endoffset);
			p.setId(count);
			p.setStartoffset(cw.startoffset);
			p.setUrl(u.url);
			p.setPath(path);
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
