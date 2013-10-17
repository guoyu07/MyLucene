/**
 * GS
 */
package com.gs.TFIDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author GaoShen
 * @packageName com.gs.TFIDF
 */
/**
 * @author GaoShen
 * @packageName com.gs.TFIDF
 */
public class CorpusIDF {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * process the corpus Calculate the idf then write the map to disk
	 * 
	 * @param corpusDirectory
	 *            the Directory which the corpus puts
	 * @param mapOutputFile
	 * @return
	 */
	public Map<String, Double> idf(File corpusDirectory, String mapOutputFile) {
		Map<String, Integer> map = null;
		Map<String, Double> idfmap = new HashMap<String, Double>();
		KeyWordsExtractor e = new KeyWordsExtractor();
		String[] extensions = { "txt" };
		map = new HashMap<String, Integer>();
		Iterator<File> iterateFiles = FileUtils.iterateFiles(corpusDirectory,
				extensions, true);
		int countofFiles = 0;
		while (iterateFiles.hasNext()) {
			countofFiles++;
			File current = iterateFiles.next();
			for (String key : e.extractSingle(current).keySet()) {
				int freq = (Integer) map.get(key) == null ? 0 : (Integer) map
						.get(key);
				map.put(key, freq == 0 ? 1 : freq + 1);
			}
		}

		for (String key : map.keySet()) {
			idfmap.put(key,
					Math.log10((double) countofFiles / (double) map.get(key)));
		}

		try {
			FileOutputStream os = new FileOutputStream(mapOutputFile);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(idfmap);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		return idfmap;
	}

	public Map<String, Double> idf(File corpusDirectory) {
		Map<String, Integer> map = null;
		KeyWordsExtractor e = new KeyWordsExtractor();
		String[] extensions = { "txt" };
		map = new HashMap<String, Integer>();
		int countofFiles = 0;
		Iterator<File> iterateFiles = FileUtils.iterateFiles(corpusDirectory,
				extensions, true);
		while (iterateFiles.hasNext()) {
			File current = iterateFiles.next();
			countofFiles++;
			for (String key : e.extractSingle(current).keySet()) {
				int freq = (Integer) map.get(key) == null ? 0 : (Integer) map
						.get(key);
				map.put(key, freq == 0 ? 1 : freq + 1);
			}
		}
		Map<String, Double> idfmap = new HashMap<String, Double>();
		for (String key : map.keySet()) {
			idfmap.put(key,
					Math.log10((double) countofFiles / (double) map.get(key)));
		}
		return idfmap;
	}

	/**
	 * read the map from the file
	 * 
	 * @param idfFile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Double> idfReader(String idfFile) {
		Map<String, Double> map = new HashMap<String, Double>();
		try {
			FileInputStream is = new FileInputStream(idfFile);
			ObjectInputStream ois = new ObjectInputStream(is);
			map = (Map<String, Double>) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return map;
	}
}
