/**
 * GS
 */
package com.gs.extractor;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;

/**
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public class TestContentExtractor {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		TencentNewsContentExtractor e = new TencentNewsContentExtractor();
		System.out.println(e
				.extract("http://news.qq.com/a/20131004/005603.htm"));
	}

	@Test
	public void testREG() throws IOException {
		String s = FileUtils.readFileToString(new File("D://Test//cont.htm"));
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</div>";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(s);
		mt.find();
		System.out.println(mt.group(1).replaceAll("[a-zA-Z_/\"<>=.:]", ""));
	}
}
