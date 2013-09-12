/**
 * GS
 */
package com.gs.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.DAO.PageDAO;
import com.gs.crawler.Schedular;
import com.gs.extractor.ContentExtractor;
import com.gs.io.ContentWriter;
import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class Downloader {
	private ContentWriter cw;
	private PageDAO dao;
	private Page p = new Page();
	private String path;
	private Status status;

	public Downloader(String path, String mergefile) {
		cw = new ContentWriter();
		this.path = path;
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		dao = (PageDAO) ctx.getBean("pageDAO");
		// dao.clear(); //Can't clear the database like this!
	}

	/**
	 * @param pop
	 * @param path
	 * @param count
	 */
	public boolean down(String url, String path, int count) {
		try {
			// String title = TitleExtractor.extractor(u.url); //the extractor
			// of title
			this.status = Status.Proceeding;
			String title = String.valueOf(count);
			String content = ContentExtractor.extractor(url);
			cw.write(content, path);
			p.setEndoffset(cw.endoffset);
			p.setId(count);
			p.setStartoffset(cw.startoffset);
			p.setUrl(url);
			p.setPath(path);
			dao.save(p);
			if (make(title, content))
				System.out.println("Downloading   " + title);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.status = Status.Done;
		return true;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	private boolean make(String title, String content) throws IOException {
		try {
			File file = new File(path + "//"+title);
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
