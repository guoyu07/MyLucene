/**
 * 
 */
package com.gs.test;

import static org.junit.Assert.*;

import org.htmlparser.parserapplications.LinkExtractor;
import org.junit.Test;

import sun.org.mozilla.javascript.internal.ast.NewExpression;

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
		/*LinkExtractor l  = new LinkExtractor();*/
		String[] s  = new String[7];
		s[0] = "http://news.qq.com";
		LinkExtractor.main(s);
	}
	//<a href="http://mail.qq.com" target="_blank">
	@Test
	public void testExtractor(){
		SubLink sub = new SubLink();
		System.out.println(sub.sub("<a href=\"http://news.qq.com/a/20130821/007507.htm\" target=\"_blank\" class=\"hotPicStyle2\" bosszone=\"pic4\"><img src=\"http://img1.gtimg."));
	}

	@Test
	public void testString(){
		System.out.println("<a href=\"http://mail.qq.com\" target=\"_blank\">");
		String s = "<a href=\"http://mail.qq.com\" target=\"_blank\">";
		System.out.println(s.substring(0, 13));
	}
	
	@Test
	public void testExt(){
		MyLinkExtractor m = new MyLinkExtractor();
		m.extractor("http://news.qq.com");
	}
	
	@Test
	public void testTileExt(){
		TitleExtractor t = new TitleExtractor();
		t.extractor("http://news.qq.com/a/20130821/006472.htm");
	}
	
	@Test
	public void testAll(){
		MyLinkExtractor linkEx = new MyLinkExtractor();
		TitleExtractor titleEx = new TitleExtractor();
		
	}
	
}
