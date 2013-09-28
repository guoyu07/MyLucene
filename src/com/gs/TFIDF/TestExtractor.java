/**
 * GS
 */
package com.gs.TFIDF;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
				"D://Lucene//docs//txts//�����ز�(000031)������Դ���ز��������޹�˾���.txt"));
	}

	@Test
	public void test2() {
		TFIDF t = new TFIDF();
		System.out.println("�������Ͽ�"+new Date(System.currentTimeMillis()).toLocaleString());
		Map<String, Double> map = new CorpusIDF().idfReader(new File("D://Test//map.txt"));
		System.out.println("�������"+new Date(System.currentTimeMillis()).toLocaleString());
		t.count("��ѧ��", new File(
				"D://Lucene//docs//chineneDocs//����ڡ��ֳ���ǿ��3���� ����5����Ů����ǿ��.txt"),
				map);
		System.out.println(new Date(System.currentTimeMillis()).toLocaleString());
		t.count("����", new File(
				"D://Lucene//docs//txts//���̸�Ⱦ����������.txt"),
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
		c.idfReader(new File("D://Test//map.txt"));
	}
	
	@Test
	public void testIDF(){
		CorpusIDF c = new CorpusIDF();
		c.idf(new File("D://Lucene//docs"), new File("D://Test//map.txt"));
	}
}
