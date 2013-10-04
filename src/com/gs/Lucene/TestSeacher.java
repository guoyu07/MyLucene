/**
 * GS
 */
package com.gs.Lucene;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gs.DAO.DAO;
import com.gs.crawler.Property;
import com.gs.model.Page;
/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class TestSeacher {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		Searcher s = new Searcher();
		Property p = new Property("D://Test//conf.xml");
		String q = "习近平";
		Page[] pages = s.search(p, q);
		if(pages.length ==0 ){System.out.println("没有找到"+q);return;}
		for (int i = 0; i < pages.length; i++) {
			System.out.println(pages[i].getUrl());
		}
	}
	
	@Test
	public void test1(){
		Indexer indexer = new Indexer();
		indexer.index("D://Test//index", "D://Test//docs");
	}
	
	@Test
	public void testSave(){
		Property p = new Property("D://Test//conf.xml");
		DAO d = new DAO(p);
		Page page = new Page();
		page.setEndoffset(11111);
		page.setId(838);
		page.setPath("D://Test//merge0.txt");
		page.setStartoffset(999);
		page.setUrl("Http://ww");
		d.save(page);
	}
	
	@Test
	public void testPath(){
		Property p = new Property("D://Test//conf.xml");
		System.out.println(p.mergefile);
	}
	
}
