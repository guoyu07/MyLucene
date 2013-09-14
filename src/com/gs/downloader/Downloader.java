/**
 * GS
 */
package com.gs.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.DAO.DAO;
import com.gs.extractor.ContentExtractor;
import com.gs.io.ContentWriter;
import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class Downloader {
	private ContentWriter cw;
	private DAO dao = new DAO();
	private Page p = new Page();
	private String path;
	private Status status;
	private DownloaderFactory factory;

	public Downloader(String docpath, String mergefile,DownloaderFactory factory) {
		
		cw = new ContentWriter();
		this.factory = factory;
		this.path = docpath;
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
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.status = Status.Finished;
		report2Factory();
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
			File file = new File(path + "//" + title);
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

	/**
	 * @param proceeding
	 */
	private void setStatus(Status status) {
		this.status = status;
	}
	
	private void report2Factory(){
		factory.releaseDownloader(this);
	}
	public void test(){
		this.status = Status.Proceeding;
		System.out.println("Downloading");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		this.status = Status.Finished;
		report2Factory();
	}

	/**
	 * @param conf
	 */
	public void down(DownConf conf) {
		down(conf.url, conf.path, conf.count);
	}

}
