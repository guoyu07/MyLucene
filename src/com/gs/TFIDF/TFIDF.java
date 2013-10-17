/**
 * GS
 */
package com.gs.TFIDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.TFIDF
 */
public class TFIDF {
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * @param key
	 * @param target target file
	 * @param corpusIDF the corpus idf map
	 * @return the TF-IDF of the key in the target
	 */
	public double count(String key, File target, Map<String,Double> corpusIDF) {
		KeyWordsExtractor e = new KeyWordsExtractor();
		double idf = corpusIDF.get(key);
		logger.debug("IDF = " + idf);
		Map<String, Integer> m = e.extractSingle(target);
		double tf = (double) m.get(key) / m.size();
		logger.debug("TF = " + tf);
		logger.debug("TF-IDF = " + idf * tf);
		return idf * tf;
	}
	
	/**
	 * @param key
	 * @param target
	 * @param corpusIDF
	 * @return
	 */
	public double count(String key, String target, Map<String,Double> corpusIDF) {
		KeyWordsExtractor e = new KeyWordsExtractor();
		double idf;
		try {
			idf = corpusIDF.get(key);
		} catch (NullPointerException e1) {
			return 0;
		}
		logger.debug("IDF = " + idf);
		Map<String, Integer> m = e.extractString(target);
		double tf = (double) m.get(key) / m.size();
		logger.debug("TF = " + tf);
		logger.debug("TF-IDF = " + idf * tf);
		return idf * tf;
	}
	
	public Map<String,Double> getTF(File target){
		KeyWordsExtractor e = new KeyWordsExtractor();
		Map<String, Integer> m = e.extractSingle(target);
		Map<String, Double> tfmap = new HashMap<String,Double>();
		for (String key : m.keySet()) {
			double tf = (double) m.get(key) / m.size();
			tfmap.put(key, tf);
		}
		return tfmap;
		
	}
	
	public Map<String,Double> getTFFromDirectory(File Directory){
		Map<String, Double> map = null;
		KeyWordsExtractor e = new KeyWordsExtractor();
		String[] extensions = { "txt","TXT" };
		map = new HashMap<String, Double>();
		int countofFiles = 0;
		Iterator<File> iterateFiles = FileUtils.iterateFiles(Directory,
				extensions, true);
		while (iterateFiles.hasNext()) {
			File current = iterateFiles.next();
			countofFiles++;
			for (String key : e.extractSingle(current).keySet()) {
				double freq = (double) (map.get(key) == null ? 0 : (double) map
						.get(key));
				map.put(key, freq == 0 ? 1 : freq + 1);
			}
		}
		
		try {
			FileOutputStream os = new FileOutputStream(Directory+"//map");
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(map);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		//logger.info(map);
		return map;
	}

}
