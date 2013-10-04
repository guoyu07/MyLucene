/**
 * GS
 */
package com.gs.Lucene;

import org.apache.log4j.Logger;

import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.Lucene
 */
public class Hit {
	private Logger logger = Logger.getLogger(this.getClass());

	public Hit(double score, Page page,String content) {
		this.score = score;
		this.page = page;
		this.content = content;
	}

	public double score;
	public Page page;
	public String content;
}
