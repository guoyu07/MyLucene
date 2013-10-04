/**
 * GS
 */
package com.gs.TFIDF;

import java.io.File;
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
		System.out.println("IDF = " + idf);
		Map<String, Integer> m = e.extractSingle(target);
		double tf = (double) m.get(key) / m.size();
		System.out.println("TF = " + tf);
		System.out.println("TF-IDF = " + idf * tf);
		return idf * tf;
	}
	
	public double count(String key, String target, Map<String,Double> corpusIDF) {
		KeyWordsExtractor e = new KeyWordsExtractor();
		double idf;
		try {
			idf = corpusIDF.get(key);
		} catch (NullPointerException e1) {
			return 0;
		}
		System.out.println("IDF = " + idf);
		Map<String, Integer> m = e.extractString(target);
		double tf = (double) m.get(key) / m.size();
		System.out.println("TF = " + tf);
		System.out.println("TF-IDF = " + idf * tf);
		return idf * tf;
	}

}
