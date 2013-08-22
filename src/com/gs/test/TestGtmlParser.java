/**
 * 
 */
package com.gs.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.htmlparser.parserapplications.LinkExtractor;
import org.junit.Test;

import sun.org.mozilla.javascript.internal.ast.NewExpression;

import com.gs.MyCrawler.BloomFilter;
import com.gs.MyCrawler.Crawler;
import com.gs.MyCrawler.URL;
import com.gs.extractor.MainExtractor;
import com.gs.extractor.MyLinkExtractor;
import com.gs.extractor.SubLink;
import com.gs.extractor.TitleExtractor;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestGtmlParser {

	@Test
	public void test() {
		/* LinkExtractor l = new LinkExtractor(); */
		String[] s = new String[7];
		s[0] = "http://news.qq.com";
		LinkExtractor.main(s);
	}

	// <a href="http://mail.qq.com" target="_blank">
	@Test
	public void testExtractor() {
		SubLink sub = new SubLink();
		System.out
				.println(sub
						.sub("<a href=\"http://news.qq.com/a/20130821/007507.htm\" target=\"_blank\" class=\"hotPicStyle2\" bosszone=\"pic4\"><img src=\"http://img1.gtimg."));
	}

	@Test
	public void testString() {
		System.out.println("<a href=\"http://mail.qq.com\" target=\"_blank\">");
		String s = "<a href=\"http://mail.qq.com\" target=\"_blank\">";
		System.out.println(s.substring(0, 13));
	}

	@Test
	public void testTileExt() {
		TitleExtractor t = new TitleExtractor();
		t.extractor("http://news.qq.com/a/20130821/006472.htm");
	}

	@Test
	public void testAll() {
		MyLinkExtractor linkEx = new MyLinkExtractor();
		TitleExtractor titleEx = new TitleExtractor();

	}

	@Test
	public void testCrawler() {
		Crawler c = new Crawler();
		c.crawl("http://localhost:8080/webpage",9,9);
		//c.crawl("http://news.qq.com",999999,999999);
	}

	@Test
	public void testFilter() {
		BloomFilter b = new BloomFilter();
		System.out.println(b.exist("http://news.qq.com"));
		// b.exsit("http://news.qq.com");
	}

	@Test
	public void testList() {
		List<URL> list = new LinkedList();
		
		for (int i = 0; i <= 3; i++) {
			URL url = new URL();
			url.level = i;
			url.url = String.valueOf(i)+"abcabc";
			list.add(url);
		}
		for(URL url1:list){
			System.out.println(url1.level);
		}
	}
	
	@Test
	public void testStringExt(){
		System.out.println(MainExtractor.extractor("http://news.qq.com"));
		
	}
	
	@Test
	public void testEnd(){
		String s = new String("http://news.qq.com/231.htm");
		System.out.println(s.endsWith(".html"));
	}

}
