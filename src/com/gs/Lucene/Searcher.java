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

import com.gs.Classifier.BayesClassifier;
import com.gs.DAO.DAO;
import com.gs.TFIDF.CorpusIDF;
import com.gs.TFIDF.TFIDF;
import com.gs.cluster.Classifier;
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
	 * ����Directory����IndexReader����IndexReader����IndexSearcher����������Query
	 * ����parse������Ҫ���������ݸ���Searcher����������TopDocs���ĵ�����TopDocs��ȡSocoreDocs����
	 * ����Searcher��SocoreDocs��ȡ�����Document�������Document�����ȡ��Ҫ��ֵ�ر�Reader
	 */

	/**
	 * @param indexField
	 *            the indexfile
	 * @param queryString
	 *            the queryString
	 * @return list a list of url
	 */
	public Hit[] search(Property property, String queryString) {
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
			Map<String, Double> map = c.idfReader(property.map);
			Page p;
			ContentReader cr = new ContentReader();
			for (ScoreDoc sd : sds) {
				Document d = seacher.doc(sd.doc);
				p = pd.loadPage(Integer.parseInt(d.get("filename")));
				Hit h;
				try {
					String content = cr.read(p.getPath(), p.getStartoffset(), p.getEndoffset());
					h = new Hit(t.count(queryString, content, map),p,content);
				} catch (NullPointerException e) {
					continue;
				}
				list.add(h);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//sort the list by TF-IDF
		//TODO:optimize the sort method
		Hit[] re = new Hit[list.size()];
		int i=list.size()-1;
		while (!list.isEmpty()) {
			double max=-1;
			Hit maxScoreHit = null;
			for(Hit h : list){
				if(h.score>max)
					maxScoreHit = h;
			}
			list.remove(maxScoreHit);
			re[i] = maxScoreHit;
			i--;
		}
		return re;
	}
	
	public Hit[] searchWithClassifier(Property property, String queryString) {
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
			Map<String, Double> map = c.idfReader(property.map);
			Page p;
			ContentReader cr = new ContentReader();
			for (ScoreDoc sd : sds) {
				Document d = seacher.doc(sd.doc);
				p = pd.loadPage(Integer.parseInt(d.get("filename")));
				Hit h;
				try {
					String content = cr.read(p.getPath(), p.getStartoffset(), p.getEndoffset());
					h = new Hit(t.count(queryString, content, map),p,content);
				} catch (NullPointerException e) {
					continue;
				}
				list.add(h);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//sort the list by TF-IDF
		//TODO:optimize the sort method
		Hit[] re = new Hit[list.size()];
		/*int i=list.size()-1;
		while (!list.isEmpty()) {
			double max=-1;
			Hit maxScoreHit = null;
			for(Hit h : list){
				if(h.score>max)
					maxScoreHit = h;
			}
			list.remove(maxScoreHit);
			re[i] = maxScoreHit;
			i--;
		}*/
		
		//Classify
		BayesClassifier cl = BayesClassifier.getInstance();
		int i=0;
		System.out.println(cl.classify("�����ͱ�����һ��ʼ����Ƿ�ѣ��������Ϻ���ʦ������ī�ơ��޲�ѷ�����������ھ����й��±���ȡФ����(΢��) ��ڣ�Ӯ��ְҵ���ĵ��߸��������ھ����������������������޹ڵ�ħ�䡣������ԱѲ����EPTC5��ɱ����������ϧ�������-���������Ǿ����˷�ӡ����������������4-3��̭ƽ��˹������4-3��������˹�������ͺ�ϣ��˹��5�ν���Ӯ��4�Σ����������繫����0-5�Ұܡ�"));
		for (Hit h : list) {
			h.clazz = cl.classify(h.content);
			System.out.println("==============="+h.clazz);
			logger.debug(h.content);
			re[i] = h;
			i++;
		}
		return re;
	}
	

}
