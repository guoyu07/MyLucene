package com.gs.crawler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author gaoshen
 * @package com.gs.crawler
 */
public class Property {
	public String url;
	public int deepth;
	public int topN;
	public OS os;
	public String path;
	public String Indexfile;
	public String docfile;
	public String mergefile;
	public boolean needsIndex;
	public String[] seeds;

	/**
	 * crawl only one url
	 * 
	 * @param url
	 *            the url which want to be crawled
	 * @param deepth
	 *            deepth
	 * @param topN
	 *            max links of one page
	 * @param os
	 *            current operate system
	 * @param path
	 *            crawl files apth
	 * @param needsIndex
	 *            true - it will index the docs after crawl<br>
	 *            false - it will NOT index the docs after crawl
	 */
	@Deprecated
	public Property(String url, int deepth, int topN, OS os, String path,
			boolean needsIndex) {
		this.url = url;
		this.deepth = deepth;
		this.topN = topN;
		this.os = os;
		this.path = path;
		this.needsIndex = needsIndex;
		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path + "//index//";
		} else if (os == OS.Linux) {
			this.docfile = path + "/docs/";
			this.Indexfile = path + "/index/";
		}
		File doc = new File(docfile);
		File ind = new File(Indexfile);
		if (!doc.exists()) {
			doc.mkdir();
		}
		if (!ind.exists()) {
			ind.mkdir();
		}
	}

	/**
	 * crawl many urls
	 * 
	 * @param urls
	 *            the array of urls which to be crawled
	 * @param deepth
	 *            deepth
	 * @param topN
	 *            max links of one page
	 * @param os
	 *            current operate system
	 * @param path
	 *            crawl files apth
	 * @param needsIndex
	 *            true - it will index the docs after crawl<br>
	 *            false - it will NOT index the docs after crawl
	 * 
	 */
	public Property(String[] urls, int deepth, int topN, OS os, String path,
			boolean needsIndex) {
		this.seeds = urls;
		this.deepth = deepth;
		this.topN = topN;
		this.os = os;
		this.path = path;
		this.needsIndex = needsIndex;
		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path + "//index//";
			this.mergefile = path + "//merge//";
		} else if (os == OS.Linux) {
			this.docfile = path + "/docs/";
			this.Indexfile = path + "/index/";
			this.mergefile = path+"/merge/";
		}
		File doc = new File(docfile);
		File ind = new File(Indexfile);
		File mer = new File(mergefile);
		if (!doc.exists()) {
			doc.mkdir();
		}
		if (!ind.exists()) {
			ind.mkdir();
		}
		if (!mer.exists()) {
			mer.mkdir();
		}
	}

	/**
	 * crawl many urls<br>
	 * it will get urls from "path//url.txt"
	 * 
	 * @param deepth
	 *            deepth
	 * @param topN
	 *            max links of one page
	 * @param os
	 *            current operate system
	 * @param path
	 *            crawl files apth
	 * @param needsIndex
	 *            true - it will index the docs after crawl<br>
	 *            false - it will NOT index the docs after crawl
	 * 
	 */
	public Property(int deepth, int topN, OS os, String path, boolean needsIndex) {
		this.deepth = deepth;
		this.topN = topN;
		this.os = os;
		this.path = path;
		this.needsIndex = needsIndex;
		this.seeds = read(path + "//");
		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path + "//index//";
			this.mergefile = path + "//merge//";
		} else if (os == OS.Linux) {
			this.docfile = path + "/docs/";
			this.Indexfile = path + "/index/";
			this.mergefile = path + "/merge/";
		}
		File doc = new File(docfile);
		File ind = new File(Indexfile);
		File mer = new File(mergefile);
		if (!doc.exists()) {
			doc.mkdir();
		}
		if (!ind.exists()) {
			ind.mkdir();
		}
		if (!mer.exists()) {
			mer.mkdir();
		}
	}

	/**
	 * get urls from file
	 * 
	 * @param path
	 * @return
	 */
	private String[] read(String path) {
		String[] seeds = null;
		try {
			File file = new File(path + "urls.txt");
			Object[] obj = FileUtils.readLines(file).toArray();
			seeds = new String[obj.length];
			for (int i = 0; i < obj.length; i++) {
				seeds[i] = (String) obj[i];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return seeds;
	}
}
