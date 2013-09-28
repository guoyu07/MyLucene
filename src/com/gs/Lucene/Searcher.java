package com.gs.Lucene;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.Document;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gs.DAO.DAO;

/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class Searcher {
	private Logger logger = Logger.getLogger(this.getClass());
	private String indexField = "D:\\Test\\index";
	private String encoding = "GB2312";
	private List<String> list;

	/*
	 * 创建Directory创建IndexReader根据IndexReader创建IndexSearcher创建搜索的Query
	 * 创建parse来创建要搜索的内容根据Searcher搜索并返回TopDocs的文档根据TopDocs获取SocoreDocs对象
	 * 根据Searcher和SocoreDocs获取具体的Document对象根据Document对象获取需要的值关闭Reader
	 */

	/**
	 * @param indexField
	 *            the indexfile
	 * @param queryString
	 *            the queryString
	 * @return list a list of url
	 */
	public List<String> search(String indexField, String queryString) {
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
					"beans.xml");
			/* PageDAO pd = (PageDAO) ctx.getBean("pageDAO"); */
			DAO pd;// TODO change
			list = new LinkedList();
			this.indexField = indexField;
			File path = new File(indexField);
			Directory directory = FSDirectory.open(path);
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher seacher = new IndexSearcher(reader);
			QueryParser query = new QueryParser(Version.LUCENE_35, "content",
					new IKAnalyzer());
			Query q = query.parse(queryString);
			TopDocs td = seacher.search(q, 10);
			ScoreDoc[] sds = td.scoreDocs;
			FileUtils fu = new FileUtils();
			for (ScoreDoc sd : sds) {
				Document d = seacher.doc(sd.doc);
				/*
				 * list.add(pd.loadPage(Integer.parseInt(d.get("filename")))
				 * .getUrl());
				 */// TODO change to DAO
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

}
