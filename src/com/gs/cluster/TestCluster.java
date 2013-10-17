/**
 * GS
 */
package com.gs.cluster;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gs.TFIDF.CorpusIDF;
import com.gs.TFIDF.KeyWordsExtractor;
import com.gs.TFIDF.TFIDF;
/**
 * @author GaoShen
 * @packageName com.gs.cluster
 */
public class TestCluster {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void testAbgle() {
		TFIDFCluster c = new TFIDFCluster();
		//System.out.println(c.cluster(new File("D://Lucene//docs//训练分类用文本//艺术//2177.txt"), new File("D://Lucene//docs//训练分类用文本//艺术//2173.txt")));
		System.out.println(c.cluster(new File("D://Test//v1.txt"), new File("D://Test//v2.txt")));
	}
	
	
	@Test
	public void test1(){
		CoordinateSystem c = new CoordinateSystem(new KeyWordsExtractor().extractDirectory(new File("D://Lucene//docs//训练分类用文本")));
		Map<String, Double> v1 = c.getVector(new TFIDF().getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//经济")));
		Map<String, Double> v2 = c.getVector(new TFIDF().getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//军事")));
		Map<String, Double> v = c.getVector(new TFIDF().getTF(new File("D://Lucene//docs//训练分类用文本//军事//8203.TXT")));
		TFIDFCluster cl = new TFIDFCluster();
		System.out.println(cl.getAngle(v, v1)>cl.getAngle(v, v2)?"经济":"军事");
		System.out.println(v1+"\n"+v2+"\n"+v);
	}
	
}
