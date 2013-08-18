package com.gs.Lucene;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class Indexer {

	private String indexField = "D:\\Lucene\\indexes\\chineseIndexes";
	private String docsField = "D:\\Lucene\\docs\\chineneDocs";

	/**
	 * @auth GaoShen
	 */
	public void index() {
		try {
			// 锟斤拷锟斤拷Directory
			Directory directory = FSDirectory.open(new File(indexField));
			// 锟斤拷锟斤拷IndexWriter
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35,
					new MMSegAnalyzer(new File(
							"D:\\Tools\\mmseg4j-all-1.8.5-with-dic\\data")));
			IndexWriter writer = new IndexWriter(directory, conf);
			// 锟斤拷锟斤拷Document锟斤拷锟斤拷
			File f = new File(docsField);
			Document doc;
			for (File file : f.listFiles()) {
				System.out.println(file.getName());
				doc = new Document();
				doc.add(new Field("content", new FileUtils().readFileToString(
						file, "UTF-8"), Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("path", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				writer.addDocument(doc);
			}
			writer.close();
			// 为Document锟斤拷锟紽iled锟斤拷锟斤拷
			// 通锟斤拷IndexWriter锟斤拷锟斤拷牡锟斤拷锟斤拷锟斤拷锟�
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
