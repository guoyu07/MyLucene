/**
 * GS
 */
package com.gs.TFIDF;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
/**
 * @author GaoShen
 * @packageName com.gs.TFIDF
 */
public class TestIO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
			try {
				CorpusIDF c = new CorpusIDF();
				Map<String, Double> idfmap = c.idfReader("D://Test//docs//IDF.txt");
				FileOutputStream os = new FileOutputStream("D://Test//IDF.txt"); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(idfmap);
				FileInputStream is = new FileInputStream("D://Test//IDF.txt"); 
				ObjectInputStream ois = new ObjectInputStream(is);
				Map<String, Double> idfmapcopy = (Map<String, Double>) ois.readObject();
				System.out.println(idfmapcopy);
				System.out.println(idfmap);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
	}
	
	@Test
	public void test1(){
		TFIDF t = new TFIDF();
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//交通"));
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//教育"));
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//艺术"));
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//政治"));
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//体育"));
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//经济"));
		t.getTFFromDirectory(new File("D://Lucene//docs//训练分类用文本//军事"));
	}
	
	@Test
	public void test2(){
		File f = new File("D://Lucene");
		String s[] = f.list();
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
	}
}
