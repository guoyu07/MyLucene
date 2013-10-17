/**
 * GS
 */
package com.gs.cluster;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.cluster
 */
public class CoordinateSystem {
	private Logger logger = Logger.getLogger(this.getClass());
	private Set<String> coordinateSystem;
	public CoordinateSystem(Set<String> coordinateSystem){
		this.coordinateSystem = coordinateSystem;
	}
	
	public Map<String, Double> getVector(Map<String,Double> vector){
		Map<String,Double> v = new HashMap<String,Double>();
		for(String key : coordinateSystem){
			v.put(key, vector.containsKey(key)? vector.get(key) : 0);
		}
		return v;
	}
}
