/**
 * GS
 */
package com.gs.cluster;
import static org.junit.Assert.*;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Test;
/**
 * @author GaoShen
 * @packageName com.gs.cluster
 */
public class TestCluster {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void testAbgle() {
		Cluster c = new Cluster();
		//System.out.println(c.cluster(new File("D://Lucene//docs//ѵ���������ı�//����//2177.txt"), new File("D://Lucene//docs//ѵ���������ı�//����//2173.txt")));
		System.out.println(c.cluster(new File("D://Test//v1.txt"), new File("D://Test//v2.txt")));
	}
}
