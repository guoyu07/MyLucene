package com.gs.crawler;

import java.io.File;
import java.io.IOException;


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
	public String Bloomfile;
	public boolean needsIndex;
	/**
	 * @param url the url which want to be crawled
	 * @param deepth deepth
	 * @param topN max links of one page
	 * @param os current operate system
	 * @param path crawl files apth
	 * @param needsIndex true - it will index the docs after crawl<br>
	 * 					false - it will NOT index the docs after crawl
	 */
	public Property(String url, int deepth, int topN, OS os, String path,boolean needsIndex) {
		this.url = url;
		this.deepth = deepth;
		this.topN = topN;
		this.os = os;
		this.path = path;
		this.Bloomfile = path;
		this.needsIndex = needsIndex;
		if (os == OS.Windows) {
			this.docfile = path + "//docs//";
			this.Indexfile = path+"//index//";
			this.Bloomfile = path+"//";
		}else if(os == OS.Linux){
			this.docfile = path + "/docs/";
			this.Indexfile = path+"/index/";
			this.Bloomfile = path+"/";
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
	
}
