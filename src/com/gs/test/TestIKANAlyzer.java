/**
 * GS
 */
package com.gs.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import com.gs.visitor.BloomFilter;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestIKANAlyzer {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<String> test() throws IOException {
		BloomFilter filter = new BloomFilter(0.1, 99999);
		File stopwords = new File("D://Test//ChineseStopwords.txt");
		List<String> stops = FileUtils.readLines(stopwords);
		for (int i = 0; i < stops.size(); i++) {
			filter.add(stops.get(i));
		}
		FileReader d = null;
		List<String> list = new LinkedList();
		try {
			d = new FileReader(new File("D://Test//merge//merge0.txt"));
			IKSegmenter ik = new IKSegmenter(d, true);
			while (true) {
				try {
					String a = ik.next().getLexemeText();
					if (!filter.contains(a) && a.length() > 1 && a.matches(".*[\u4e00-\u9faf].*")) {
						list.add(a);
					}
				} catch (NullPointerException e) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return list;
	}

	@Test
	public void test1() throws IOException {
		Map<String, Integer> m = new HashMap();
		List<String> li = test();
		for (String word : test()) {
			int freq = (Integer) m.get(word) == null ? 0 : (Integer) m
					.get(word);
			m.put(word, freq == 0 ? 1 : freq + 1);
		}
		Set<String> keySet = m.keySet();

		/*
		 * List<Item> l = new LinkedList(); for (String key : keySet) {
		 * l.add(new Item(m.get(key),key)); }
		 */
		for (int i = 0; i < 10; i++) {
			int a = 0;
			String MaxKey=null;
			for (String key : keySet) {
				if (m.get(key)>a) {
					a = m.get(key);
					MaxKey = key;
				}
			}
			System.out.println(MaxKey);
			m.remove(MaxKey);
		}
	}
	
	@Test
	public void test2(){
		
	}
	
	public void campare(File x,File y) throws IOException{
		BloomFilter filter = new BloomFilter(0.1, 99999);
		File stopwords = new File("D://Test//ChineseStopwords.txt");
		List<String> stops = FileUtils.readLines(stopwords);
		for (int i = 0; i < stops.size(); i++) {
			filter.add(stops.get(i));
		}
		FileReader d = null;
		List<String> list = new LinkedList();
		try {
			d = new FileReader(new File("D://Test//merge//merge0.txt"));
			IKSegmenter ik = new IKSegmenter(d, true);
			while (true) {
				try {
					String a = ik.next().getLexemeText();
					if (!filter.contains(a) && a.length() > 1 && a.matches(".*[\u4e00-\u9faf].*")) {
						list.add(a);
					}
				} catch (NullPointerException e) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

}

class Item {
	public int freq;
	public String word;

	/**
	 * @param freq
	 * @param word
	 */
	public Item(int freq, String word) {
		super();
		this.freq = freq;
		this.word = word;
	}
}
