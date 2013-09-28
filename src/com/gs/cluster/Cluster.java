/**
 * GS
 */
package com.gs.cluster;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.TFIDF.KeyWordsExtractor;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

/**
 * @author GaoShen
 * @packageName com.gs.cluster
 */
public class Cluster {
	private Logger logger = Logger.getLogger(this.getClass());

	public double cluster(File f1, File f2) {
		KeyWordsExtractor e = new KeyWordsExtractor();
		Map<String, Integer> map1 = e.extractSingle(f1);
		Map<String, Integer> map2 = e.extractSingle(f2);
		Set<String> set1 = map1.keySet();
		Set<String> set2 = map2.keySet();
		Set<String> set = new HashSet<String>();
		set.addAll(set1);
		set.addAll(set2);
		Iterator<String> it = set.iterator();
		int[] v1 = new int[set.size()];
		int[] v2 = new int[set.size()];
		int i = 0;
		String key;
		while (it.hasNext()) {
			key = it.next();
			v1[i] = set1.contains(key) ? map1.get(key) : 0;
			v2[i] = set2.contains(key) ? map2.get(key) : 0;
			i++;
		}
		return getAngle(v1, v2);
	}

	public double getAngle(int[] v1, int[] v2) {
		double fenzi = 0;
		double fenmu1 = 0, fenmu2 = 0;
		for (int i = 0; i < v1.length; i++) {
			fenzi += (double) v1[i] * (double) v2[i];
			fenmu1 += (double) v1[i] * (double) v1[i];
			fenmu2 += (double) v2[i] * (double) v2[i];
		}
		double cos = fenzi/(Math.sqrt(fenmu1)*Math.sqrt(fenmu2));
		double result = (Math.acos(cos)/Math.PI)*180;
		return result;
	}
}
