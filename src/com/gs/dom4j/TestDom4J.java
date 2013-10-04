/**
 * GS
 */
package com.gs.dom4j;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * @author GaoShen
 * @packageName com.gs.dom4j
 */
public class TestDom4J {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		try {
			String s = FileUtils.readFileToString(new File(
					"D://Lucene//SogouCS.reduced//news.sohunews.050806.txt"),
					"GBK");
			String[] ss = s.split("</doc>");
			for (int i = 0; i < ss.length; i++) {
				ss[i] += "</doc>";
				char[] c = ss[i].toCharArray();
				for (int j = 0; j < c.length; j++) {
					if (c[j] == '&') {
						c[j] = '?';
					}
				}
				ss[i] = new String(c);
			}
			ss[ss.length - 1] = null;
			for (String s1 : ss) {
				if (!(s1 == null)) {
					//System.out.println(s1);
					StringReader sr = new StringReader(s1);
					SAXReader reader = new SAXReader();
					Document document = reader.read(sr);
					Element root = document.getRootElement();
					Iterator i = root.elementIterator();
					String docno = null;
					String content = null;
					while (i.hasNext()) {
						Element element = (Element) i.next();
						if(element.getName().equals("docno")){
							docno = element.getText();
							
						}
						if(element.getName().equals("content")){
							content = element.getText();
						}
						System.out.println("docno : "+docno+"  content : "+content);
					}
					if (!(docno == null || content == null)) {
						FileUtils.writeStringToFile(new File(
								"D://Lucene//myDocs//" + docno+".txt"), content);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
