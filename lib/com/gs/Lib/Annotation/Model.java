/**
 * GS
 */
package com.gs.Lib.Annotation;

import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.Annotation
 */
@Entity
public class Model {
	private Logger logger = Logger.getLogger(this.getClass());
	private int id;
	private String name;
	
	@PK
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
