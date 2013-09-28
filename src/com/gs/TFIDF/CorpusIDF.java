/**
 * GS
 */
package com.gs.TFIDF;

import java.io.File;
import java.io.IOException;
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
	 * @param corpusDirectory the Directory which the corpus puts
	 * @param mapOutputFile 
	 * @return
	 */
	public Map<String, Integer> idf(File corpusDirectory, File mapOutputFile) {
		Map<String, Integer> map = null;
		try {
			KeyWordsExtractor e = new KeyWordsExtractor();
			String[] extensions = { "txt" };
			map = new HashMap<String, Integer>();
			Iterator<File> iterateFiles = FileUtils.iterateFiles(
					corpusDirectory, extensions, true);
			int countofFiles = 0;
			while (iterateFiles.hasNext()) {
				countofFiles++;
				File current = iterateFiles.next();
				for (String key : e.extractSingle(current).keySet()) {
					int freq = (Integer) map.get(key) == null ? 0
							: (Integer) map.get(key);
					map.put(key, freq == 0 ? 1 : freq + 1);
				}
			}
			if (!mapOutputFile.exists()) {
				mapOutputFile.createNewFile();
			}
			for (String key : map.keySet()) {
				FileUtils.writeStringToFile(mapOutputFile,
						key + "   " + Math.log10((double)countofFiles/(double)map.get(key)) + "\r\n", true);//three space
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return map;
	}
	@Deprecated
	public Map<String, Integer> idf(File corpusDirectory) {
		Map<String, Integer> map = null;
		KeyWordsExtractor e = new KeyWordsExtractor();
		String[] extensions = { "txt" };
		map = new HashMap<String, Integer>();
		Iterator<File> iterateFiles = FileUtils.iterateFiles(corpusDirectory,
				extensions, true);
		while (iterateFiles.hasNext()) {
			File current = iterateFiles.next();
			for (String key : e.extractSingle(current).keySet()) {
				int freq = (Integer) map.get(key) == null ? 0 : (Integer) map
						.get(key);
				map.put(key, freq == 0 ? 1 : freq + 1);
			}
		}
		return map;
	}
	
	/**
	 * read the map from the file
	 * @param idfFile
	 * @return
	 */
	public Map<String,Double> idfReader(File idfFile){
		Map<String,Double> map = new HashMap<String,Double>();
		try {
			List<String> list = FileUtils.readLines(idfFile);
			for (int j = 0; j < list.size(); j++) {
				String s = list.get(j);
				map.put(s.split("   ")[0], Double.valueOf(s.split("   ")[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return map;
	}
}
