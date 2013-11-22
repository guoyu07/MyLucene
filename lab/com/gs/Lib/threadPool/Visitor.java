/**
 * GS
 */
package com.gs.Lib.threadPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.gs.DAO.DAO;
import com.gs.crawler.Property;
import com.gs.crawler.URL;
import com.gs.extractor.IDFactory;
import com.gs.extractor.LinkExtractor;
import com.gs.extractor.TencentNewsContentExtractor;
import com.gs.extractor.TencentNewsLinkExtractor;
import com.gs.io.ContentWriter;
import com.gs.model.Page;
import com.gs.utils.Status;
import com.gs.visitor.VisitorManager;

/**
 * @author GaoShen
 * @packageName com.gs.visitor
 */
public class Visitor implements Callable<List<URL>>{
	private Logger logger = Logger.getLogger(this.getClass());
	private int topN;
	private com.gs.Lib.threadPool.VisitorFactory factory;
	private VisitorManager manager;
	private int deepth;
	private Status status;
	private long startTime;
	private DAO dao;
	private ContentWriter cw;
	private TencentNewsContentExtractor contentEtractor = new TencentNewsContentExtractor();
	private URL currentURL;
	private Property property;
	private int id;

	/**
	 * @param property
	 * @param factory
	 * @param manager
	 */
	public Visitor(Property property, VisitorFactory factory,
			VisitorManager manager, int id) {
		this.topN = property.topN;
		this.property = property;
		this.id = id;
		this.deepth = property.deepth + 1;
		this.factory = factory;
		this.manager = manager;
		this.dao = new DAO(property);
		this.cw = new ContentWriter();
	}

	/**
	 * @param url
	 * @return a list of urls which the param page content
	 */
	public List<URL> visit(URL url) {
		this.currentURL = url;
		this.startTime = System.currentTimeMillis();
		this.status = Status.Proceeding;
		if (url.level < deepth) {
			List<URL> list = null;
			// DefaultLinkExtractor e = new DefaultLinkExtractor();
			LinkExtractor e = new TencentNewsLinkExtractor();
			list = e.extract(url, topN);
			fetch(e.getHtml());
			if (list.size() == 0) {
				recycle();
				return list;
			}
			for (URL nurl : list) {
				manager.add(nurl);
				logger.info("URL = " + nurl.url + "    Level = " + nurl.level);
			}
			recycle();
			return list;
		} else {
			recycle();
			return new LinkedList<URL>();
		}
	}

	/**
	 * recycle this visitor
	 */
	protected void recycle() {
		this.status = Status.Finished;
		factory.recycle(this);
	}


	private void fetch(String html) {

		String content = contentEtractor.extractFromHtml(html);
		if (content != null ) {
			String id = IDFactory.getID();
			make(id, content);
			cw.write(content, property.mergefile + this.id + ".txt"); // merge
			Page p = new Page();
			p.setEndoffset(cw.endoffset);
			p.setId(Integer.parseInt(id));
			p.setStartoffset(cw.startoffset);
			p.setUrl(currentURL.url);
			p.setPath(property.mergefile + this.id + ".txt");
			dao.save(p);
			p = null;
		}
	}

	/**
	 * make docs
	 * 
	 * @param title
	 * @param content
	 * @return
	 * @throws IOException
	 */
	private boolean make(String title, String content) {
		try {
			File file = new File(property.docfile + title);
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

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public List<URL> call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
