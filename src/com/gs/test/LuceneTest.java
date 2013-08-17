package com.gs.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.gs.Lucene.Indexer;

public class LuceneTest {

	@Test
	public void test() {
		Indexer indexer = new Indexer();
		indexer.index();
	}

	public void search(){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("«Î ‰»ÎA:"); 
			String a=br.readLine();
			System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
