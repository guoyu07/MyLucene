package com.gs.Lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.util.Version;

public class Searcher {
	public void search(){
		/*
		 *创建Directory
		 *创建IndexReader
		 *根据IndexReader创建IndexSearcher
		 *创建搜索的Query 创建parse来创建要搜索的内容
		 *根据Searcher搜索并返回TopDocs的文档
		 *根据TopDocs获取SocoreDocs对象
		 *根据Searcher和SocoreDocs获取具体的Document对象
		 *根据Document对象获取需要的值
		 *关闭Reader
		 */
		
		try {
			File path = new File("D:\\Lucene\\indexes");
			Directory directory  = FSDirectory.open(path);
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher seacher  =new IndexSearcher(reader);
			QueryParser query = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			Query q = query.parse("习");
			TopDocs td  = seacher.search(q, 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
