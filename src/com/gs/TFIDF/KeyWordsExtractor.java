/**
 * GS
 */
package com.gs.TFIDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.wltea.analyzer.core.IKSegmenter;

import com.gs.visitor.BloomFilter;

/**
 * @author GaoShen
 * @packageName com.gs.TFIDF
 */
public class KeyWordsExtractor {
	private Logger logger = Logger.getLogger(this.getClass());
	private BloomFilter filter;

	/**
	 * 
	 */
	public KeyWordsExtractor() {
		filter = new BloomFilter(0.1, 99999);
		File stopwords = new File("D://Test//ChineseStopwords.txt");
		List<String> stops = null;
		try {
			stops = FileUtils.readLines(stopwords);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		for (int i = 0; i < stops.size(); i++) {
			filter.add(stops.get(i));
		}
	}

	/**
	 * extract the words from a direactory
	 * @param directory
	 * @return 
	 */
	public Set<String> extractDirectory(File directory){
		String[] extensions = {"txt"};
		Iterator<File> iterateFiles = FileUtils.iterateFiles(directory, extensions, true);
		Set<String> list = null;
		while (iterateFiles.hasNext()) {
			File current = iterateFiles.next();
			FileReader d = null;
			list = new HashSet<String>();// sped aritical
			try {
				d = new FileReader(current);
				IKSegmenter ik = new IKSegmenter(d, true);
				while (true) {
					try {
						String a = ik.next().getLexemeText();
						if (!filter.contains(a) && a.length() > 1 && a.matches(".*[\u4e00-\u9faf].*")) {
							list.add(a);
						}
					} catch (NullPointerException e) {
						break;
					} catch(ArrayIndexOutOfBoundsException e){
						System.out.println("he%%%%%%%%%%%%%%%%%%%%");
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			Map<String, Integer> m = new HashMap<String, Integer>();
			for (String word : list) {
				int freq = (Integer) m.get(word) == null ? 0 : (Integer) m
						.get(word);
				m.put(word, freq == 0 ? 1 : freq + 1);
			}
			//sort
			/*Set<String> keySet = m.keySet();
			System.out.println(current.getName());
			for (int i = 0; i < 10; i++) {
				int a = 0;
				String MaxKey=null;
				for (String key : keySet) {
					if (m.get(key)>a) {
						a = m.get(key);
						MaxKey = key;
					}
				}
				System.out.println(MaxKey+"   "+((double)a/list.size())*100+"%");
				m.remove(MaxKey);
			}
			
			System.out.println("-------------------");*/
			
		}
		
		return list;
		
		
	}
	/**
	 * extract the words from a single file
	 * @param current
	 * @return
	 */
	public Map<String, Integer> extractSingle(File current){
		FileReader d = null;
		List<String> list = new LinkedList();// sped aritical
		try {
			d = new FileReader(current);
			IKSegmenter ik = new IKSegmenter(d, true);
			while (true) {
				try {
					String a = ik.next().getLexemeText();
					if (!filter.contains(a) && a.length() > 1 && a.matches(".*[\u4e00-\u9faf].*")) {
						list.add(a);
					}
				} catch (NullPointerException e) {
					break;
				} catch(ArrayIndexOutOfBoundsException e){
					//System.out.println("he%%%%%%%%%%%%%%%%%%%%");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		Map<String, Integer> m = new HashMap();
		for (String word : list) {
			int freq = (Integer) m.get(word) == null ? 0 : (Integer) m
					.get(word);
			m.put(word, freq == 0 ? 1 : freq + 1);
		}
		//sort
		/*Map<String, Integer> m2 = m;
		Set<String> keySet = m2.keySet();
		System.out.println(current.getName());
		for (int i = 0; i < m2.size(); i++) {
			int a = 0;
			String MaxKey=null;
			for (String key : keySet) {
				if (m2.get(key)>a) {
					a = m2.get(key);
					MaxKey = key;
				}
			}
			System.out.println(MaxKey+"   "+((double)a/list.size())*100+"%");
			m2.remove(MaxKey);
		}*/

		return m;
	}
	
	public Map<String, Integer> extractSingle(File current,boolean needSort){
		FileReader d = null;
		List<String> list = new LinkedList();// sped aritical
		try {
			d = new FileReader(current);
			IKSegmenter ik = new IKSegmenter(d, true);
			while (true) {
				try {
					String a = ik.next().getLexemeText();
					if (!filter.contains(a) && a.length() > 1 && a.matches(".*[\u4e00-\u9faf].*")) {
						list.add(a);
					}
				} catch (NullPointerException e) {
					break;
				} catch(ArrayIndexOutOfBoundsException e){
					//System.out.println("he%%%%%%%%%%%%%%%%%%%%");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		Map<String, Integer> m = new HashMap();
		for (String word : list) {
			int freq = (Integer) m.get(word) == null ? 0 : (Integer) m
					.get(word);
			m.put(word, freq == 0 ? 1 : freq + 1);
		}
		if (needSort) {
			//sort
			Map<String, Integer> m2 = m;
			Set<String> keySet = m2.keySet();
			System.out.println(current.getName());
			for (int i = 0; i < m2.size(); i++) {
				int a = 0;
				String MaxKey = null;
				for (String key : keySet) {
					if (m2.get(key) > a) {
						a = m2.get(key);
						MaxKey = key;
					}
				}
				System.out.println(MaxKey + "   " + ((double) a / list.size())
						* 100 + "%");
				m2.remove(MaxKey);
			}
		}
		return m;
	}
	
	public Map<String, Integer> extractString(String page){
		List<String> list = new LinkedList();// sped aritical
		try {
			StringReader r = new StringReader(page);
			IKSegmenter ik = new IKSegmenter(r, true);
			while (true) {
				try {
					String a = ik.next().getLexemeText();
					if (!filter.contains(a) && a.length() > 1 && a.matches(".*[\u4e00-\u9faf].*")) {
						list.add(a);
					}
				} catch (NullPointerException e) {
					break;
				} catch(ArrayIndexOutOfBoundsException e){
					//System.out.println("he%%%%%%%%%%%%%%%%%%%%");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		Map<String, Integer> m = new HashMap();
		for (String word : list) {
			int freq = (Integer) m.get(word) == null ? 0 : (Integer) m
					.get(word);
			m.put(word, freq == 0 ? 1 : freq + 1);
		}
		//sort
		/*Set<String> keySet = m.keySet();
		System.out.println(current.getName());
		for (int i = 0; i < m.size(); i++) {
			int a = 0;
			String MaxKey=null;
			for (String key : keySet) {
				if (m.get(key)>a) {
					a = m.get(key);
					MaxKey = key;
				}
			}
			System.out.println(MaxKey+"   "+((double)a/list.size())*100+"%");
			m.remove(MaxKey);
		}*/

		return m;
	}
	

}
