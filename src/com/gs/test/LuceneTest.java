package com.gs.test;

import org.junit.Test;

import com.gs.Lucene.Indexer;
import com.gs.Lucene.Searcher;

public class LuceneTest {

	@Test
	public void testIndex() {
		Indexer indexer = new Indexer();
		indexer.index();
	}

	@Test
	public void testSeracher() {
		Searcher searcher = new Searcher();
		searcher.search("����");
	}

	@Test
	public void testio() {

	}

}
