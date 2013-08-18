package com.gs.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.gs.Lucene.Indexer;
import com.gs.Lucene.Searcher;

public class LuceneTest {

	@Test
	public void testindex() {
		Indexer indexer = new Indexer();
		indexer.index();
	}

	@Test
	public void testSeracher(){
		Searcher searcher = new Searcher();
		searcher.search("from");
	}
	
	@Test
	public void testio() {
		
	}
}
