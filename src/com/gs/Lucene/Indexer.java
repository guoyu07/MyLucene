package com.gs.Lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;


public class Indexer {
	
	public void index() {
		try {
			//����Directory
			Directory directory = FSDirectory.open(new File("D:\\Lucene\\indexes"));
			//����IndexWriter
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			IndexWriter writer = new IndexWriter(directory, conf);
			//����Document����
			File f = new File("D:\\Lucene\\docs");
			Document doc;
			for(File file:f.listFiles()){
				System.out.println(file.getName());
				doc = new Document();
				doc.add(new Field("content",new FileReader(file)));
				doc.add(new Field("path",file.getAbsolutePath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				doc.add(new Field("filename",file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				writer.addDocument(doc);
				
			}
			writer.close();
			//ΪDocument���Filed����
			//ͨ��IndexWriter����ĵ�������
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
