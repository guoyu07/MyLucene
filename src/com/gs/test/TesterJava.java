/**
 * GS
 */
package com.gs.test;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gs.extractor.IDFactory;
/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TesterJava {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
//		TestJava t = new TestJava();
//		System.out.println(t.getId());
//		TestJava t1 = new TestJava();
//		System.out.println(t1.getId());
//		System.out.println(TestJava.getId());
		new TestJava();

		
	}
	
	@Test
	public void testGC(){
		TestJava t1 = new TestJava(1);
		TestJava t2 = new TestJava(2);
		TestJava t3 = new TestJava(3);
		TestJava t4 = new TestJava(4);
		t1 = null;
		t2 = null;
		t3 = null;
		t4 = null;
		System.gc();
	}
	
	@Test
	public void tseID(){
		System.out.println(IDFactory.getID());
		System.out.println(IDFactory.getID());
		System.out.println(IDFactory.getID());
		System.out.println(IDFactory.getID());
	}
}
