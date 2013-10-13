package com.gs.crawler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gs.jdom.CrawlerConfReader;

/**
 * @author gaoshen
 * @package com.gs.crawler
 */
/**
 * @author GaoShen
 * @packageName com.gs.crawler
 */
public class Property {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String re = "Deepth : " + deepth + "\nTopN : " + topN + "\nOS : " + os
				+ "\nPath : " + path + "\nNeedsIndex : " + needsIndex+"\nDBName : "+dbname+"\nDBPass : "+dbpass;
		return re;
	}

	private Logger logger = Logger.getLogger(this.getClass());
	public String url;
	public int deepth;
	public int topN;
	public OS os;
	public String path;
	public String Indexfile;
	public String docfile;
	public String mergefile;
	public String dbname;
	public String dbpass;
	public boolean needsIndex;
	public String[] seeds;
	public String map;

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
	@Deprecated
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
	@Deprecated
	public Property(int deepth, int topN, OS os, String path, boolean needsIndex) {
		this.deepth = deepth;
		this.topN = topN;
		this.os = os;
		this.path = path;
		this.needsIndex = needsIndex;

		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path + "//index//";
			this.mergefile = path + "//merge//";
			this.seeds = read(path + "//");
		} else if (os == OS.Linux) {
			this.docfile = path + "/docs/";
			this.Indexfile = path + "/index/";
			this.mergefile = path + "/merge/";
			this.seeds = read(path + "/");
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

	/**
	 * read configure from xml file
	 * @param confXMLPath
	 */
	public Property(String confXMLPath) {
		CrawlerConfReader reader = new CrawlerConfReader(confXMLPath);
		Property p = reader.getProperty();
		this.deepth = p.deepth;
		this.topN = p.topN;
		this.os = p.os;
		this.path = p.path;
		this.needsIndex = p.needsIndex;
		this.dbname = p.dbname;
		this.dbpass = p.dbpass;
		this.map = p.map;
		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path + "//index//";
			this.mergefile = path + "//merge//";
			this.seeds = read(path + "//");
		} else if (os == OS.Linux) {
			this.docfile = path + "/docs/";
			this.Indexfile = path + "/index/";
			this.mergefile = path + "/merge/";
			this.seeds = read(path + "/");
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
	 * @param deepth2
	 * @param topN2
	 * @param os2
	 * @param path2
	 * @param needsIndex2
	 * @param databaseUsername
	 * @param databasePassword
	 */
	public Property(int deepth, int topN, OS os, String path,
			boolean needsIndex, String databaseUsername,
			String databasePassword,String map) {
		this.deepth = deepth;
		this.topN = topN;
		this.os = os;
		this.path = path;
		this.needsIndex = needsIndex;
		this.dbname = databaseUsername;
		this.dbpass = databasePassword;
		this.map = map;
		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path + "//index//";
			this.mergefile = path + "//merge//";
			this.seeds = read(path + "//");
		} else if (os == OS.Linux) {
			this.docfile = path + "/docs/";
			this.Indexfile = path + "/index/";
			this.mergefile = path + "/merge/";
			this.seeds = read(path + "/");
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

	public String getUrl() {
		return url;
	}

	public int getDeepth() {
		return deepth;
	}

	public int getTopN() {
		return topN;
	}

	public OS getOs() {
		return os;
	}

	public String getPath() {
		return path;
	}

	public String getIndexfile() {
		return Indexfile;
	}

	public String getDocfile() {
		return docfile;
	}

	public String getMergefile() {
		return mergefile;
	}

	public String getDbname() {
		return dbname;
	}

	public String getDbpass() {
		return dbpass;
	}

	public boolean isNeedsIndex() {
		return needsIndex;
	}

	public String[] getSeeds() {
		return seeds;
	}

	public String getMap() {
		return map;
	}
}
