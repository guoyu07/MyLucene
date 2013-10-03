/**
 * GS
 */
package com.gs.TFIDF;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;

/**
 * @author GaoShen
 * @packageName com.gs.TFIDF
 */
public class TestExtractor {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		KeyWordsExtractor e = new KeyWordsExtractor();
		e.extractDirectory(new File("D://Lucene//docs"));
	}

	@Test
	public void test1() {
		KeyWordsExtractor e = new KeyWordsExtractor();
		e.extractSingle(new File(
				"D://Lucene//docs//txts//中粮地产(000031)苏州苏源房地产开发有限公司审计.txt"));
	}

	@Test
	public void test2() {
		TFIDF t = new TFIDF();
		System.out.println("加载语料库"+new Date(System.currentTimeMillis()).toLocaleString());
		Map<String, Double> map = new CorpusIDF().idfReader("D://Test//map.txt");
		System.out.println("加载完毕"+new Date(System.currentTimeMillis()).toLocaleString());
		t.count("大学生", new File(
				"D://Lucene//docs//chineneDocs//“涉黑”局长文强涉3罪名 至少5名妇女遭其强奸.txt"),
				map);
		System.out.println(new Date(System.currentTimeMillis()).toLocaleString());
		t.count("艾滋", new File(
				"D://Lucene//docs//txts//艾滋感染者生命感言.txt"),
				map);
		System.out.println(new Date(System.currentTimeMillis()).toLocaleString());
	}

	@Test
	public void test3() throws IOException {
		KeyWordsExtractor e = new KeyWordsExtractor();
		String[] extensions = { "txt" };
		Map<String, Integer> map = new HashMap<String, Integer>();
		Iterator<File> iterateFiles = FileUtils.iterateFiles(new File(
				"D://Lucene//docs"), extensions, true);
		while (iterateFiles.hasNext()) {
			File current = iterateFiles.next();
			System.out.println(current);
			for (String key : e.extractSingle(current).keySet()) {
				int freq = (Integer) map.get(key) == null ? 0 : (Integer) map
						.get(key);
				map.put(key, freq == 0 ? 1 : freq + 1);
			}
		}
		File mapOutput = new File("D://Test//map.txt");
		if (!mapOutput.exists()) {
			mapOutput.createNewFile();
		}
		for(String key : map.keySet()){
			FileUtils.writeStringToFile(mapOutput,key+"   "+map.get(key)+"\r\n",true);//three space
		}
	}
	
	@Test
	public void testIDFReader(){
		CorpusIDF c = new CorpusIDF();
		c.idfReader("D://Test//map.txt");
	}
	
	@Test
	public void testIDF(){
		CorpusIDF c = new CorpusIDF();
		c.idf(new File("D://Lucene//docs"), "D://Test//docs//map.txt");
	}
	
	@Test
	public void testArray(){
		int[][] a = {{2,2},{1,1},{6,6},{4,4}};
		Arrays.sort(a[2]);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(a[i][j]+"  ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void test4(){
		String s = "dskbfkAAADDCjsbfjabd3409209+_+>:';'南方水泥粉in斯蒂芬森年底开始fnsnls[][;;.";
		char[] c = s.toCharArray();
		for(int i=0;i<c.length;i++){
			if((c[i]>='A'&&c[i]<='Z')||(c[i]>='a'&&c[i]<='z')||(c[i]>=33&&c[i]<=47)||(c[i]>=58&&c[i]<=64)||(c[i]>=123&&c[i]<=126)||(c[i]>=91&&c[i]<=96)){
				c[i] = ' ';
			}
		}
		System.out.println(new String(c));
	}
	
	@Test
	public void test5(){
		CorpusIDF c = new CorpusIDF();
		//Map<String,Double> idfmap = c.idf(new File("D://Test//docs"), "D://Test//docs//IDF.txt");
		Map<String,Double> idfmap = c.idfReader("D://Test//docs//IDF.txt");
		TFIDF t = new TFIDF();
		Map<String,Double> tfmap = t.getTF(new File("D://Test//docs//193.txt"));
		Map<String,Double> tfidfmap = new HashMap<String,Double>();
		for (String key : tfmap.keySet()) {
			tfidfmap.put(key, tfmap.get(key)*idfmap.get(key));
		}
		int i = 0;
		System.out.println("-----------");
		while (i<6) {
			String mainkey = null;
			double maxtfidf = 0;
			for (String key : tfidfmap.keySet()) {
				double tfidf = tfidfmap.get(key);
				if (tfidf > maxtfidf) {
					mainkey = key;
					maxtfidf = tfidf;
				}
			}
			tfidfmap.remove(mainkey);
			System.out.println(mainkey);
			i++;
		}
		System.out.println("-----------------");
		KeyWordsExtractor k = new KeyWordsExtractor();
		k.extractSingle(new File("D://Test//docs//193.txt"),true);
	}
}
