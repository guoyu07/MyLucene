/**
 * GS
 */
package com.gs.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.DAO.DAO;
import com.gs.crawler.Property;
import com.gs.extractor.ContentExtractor;
import com.gs.extractor.TencentNewsContentExtractor;
import com.gs.io.ContentWriter;
import com.gs.model.Page;
import com.gs.utils.Status;

/**
 * @author GaoShen
 * @packageName com.gs.downloader
 */
public class Downloader {
	private Logger logger = Logger.getLogger(this.getClass());
	private ContentWriter cw;
	private DAO dao;
	private Page p = new Page();
	private String path;
	private Status status; // the status of this downloader
	private DownloaderFactory factory;
	private long startTime;
	private int id;
	private Property property;

	/**
	 * @param docpath
	 * @param mergefile
	 * @param factory
	 *            a connect to factory
	 */
	public Downloader(Property property, DownloaderFactory factory, int id) {
		dao = new DAO(property);
		this.id = id;
		cw = new ContentWriter();
		this.factory = factory;
		this.path = property.docfile;
		this.property = property;
	}

	/**
	 * @param pop
	 * @param path
	 * @param count
	 *            given by factory
	 */
	public boolean down(String url, int count) {
		try {
			// String title = TitleExtractor.extractor(u.url); //the extractor
			// of title
			this.status = Status.Proceeding;
			this.startTime = System.currentTimeMillis();
			String title = String.valueOf(count);
			ContentExtractor tencentNewsContentExtractor = new TencentNewsContentExtractor();
			String content = tencentNewsContentExtractor.extract(url);
			if (content == null || content.equals("")) {
				this.status = Status.Finished;
				recycle();
				return false;
			}
			cw.write(content, property.mergefile + id); // merge
			p.setEndoffset(cw.endoffset);
			p.setId(count);
			p.setStartoffset(cw.startoffset);
			p.setUrl(url);
			p.setPath(property.mergefile + id);
			dao.save(p);
			if (make(title, content)) // make docs
				logger.info("Downloading   " + title);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		this.status = Status.Finished;
		recycle();
		return true;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * make docs
	 * 
	 * @param title
	 * @param content
	 * @return
	 * @throws IOException
	 */
	private boolean make(String title, String content) throws IOException {
		try {
			File file = new File(path + title);
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			return true;
		} catch (FileNotFoundException e) {
			logger.error("Some Error with the title");
			logger.error(e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error("Some IO Error");
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * @param proceeding
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * tell the factory to move this downloader into freequeue
	 */
	public void recycle() {
		factory.releaseDownloader(this);
	}

	/**
	 * @param conf
	 */
	public void down(DownConf conf) { // for dwon thread
		down(conf.url, conf.count);
	}

	/**
	 * @return
	 */
	public long getStartTime() {
		return startTime;
	}

	public void destory() {
		if (this.factory.getProceedingQueue().contains(this)) {
			this.factory.getProceedingQueue().remove(this);
		}
		if (this.factory.getFreequeue().contains(this)) {
			this.factory.getFreequeue().remove(this);
		}
		dao.destory();
		cw = null;
	}

}
