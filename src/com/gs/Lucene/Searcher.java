package com.gs.Lucene;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gs.DAO.DAO;
import com.gs.TFIDF.CorpusIDF;
import com.gs.TFIDF.TFIDF;
import com.gs.crawler.Property;
import com.gs.io.ContentReader;
import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class Searcher {
	private Logger logger = Logger.getLogger(this.getClass());
	private String indexField = "D:\\Test\\index";
	private List<Hit> list;

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
	public Page[] search(Property property, String queryString) {
		try {
			DAO pd = new DAO(property);
			list = new LinkedList<Hit>();
			this.indexField = property.Indexfile;
			File path = new File(indexField);
			Directory directory = FSDirectory.open(path);
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher seacher = new IndexSearcher(reader);
			QueryParser query = new QueryParser(Version.LUCENE_35, "content",
					new IKAnalyzer());
			Query q = query.parse(queryString);
			TopDocs td = seacher.search(q, 10);
			ScoreDoc[] sds = td.scoreDocs;
			TFIDF t = new TFIDF();
			CorpusIDF c = new CorpusIDF();
			Map<String, Double> map = c.idfReader(new File("D://Test//map.txt"));
			Page p;
			ContentReader cr = new ContentReader();
			for (ScoreDoc sd : sds) {
				Document d = seacher.doc(sd.doc);
				p = pd.loadPage(Integer.parseInt(d.get("filename")));
				Hit h = new Hit(t.count(queryString, cr.read(p.getPath(), p.getStartoffset(), p.getEndoffset()), map),p);
				list.add(h);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//sort the list by TF-IDF
		Page[] re = new Page[list.size()];
		int i=0;
		while (!list.isEmpty()) {
			double max=0;
			Hit maxScoreHit = null;
			for(Hit h : list){
				if(h.score>max)
					maxScoreHit = h;
			}
			list.remove(maxScoreHit);
			re[i] = maxScoreHit.page;
			i++;
		}
		return re;
	}
	class Hit{
		public Hit(double score, Page page) {
			this.score = score;
			this.page = page;
		}
		double score;
		Page page;
	}

}
