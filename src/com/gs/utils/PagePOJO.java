/**
 * GS
 */
package com.gs.utils;
import java.io.Serializable;

import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.utils
 */
public class PagePOJO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass());
	public long startoffset;
	public long endoffset;
	public String url;
	public int id;
	public String mergepath;
	
	@Override
	public String toString() {
		return "PagePOJO [" + (logger != null ? "logger=" + logger + ", " : "")
				+ "startoffset=" + startoffset + ", endoffset=" + endoffset
				+ ", " + (url != null ? "url=" + url + ", " : "") + "id=" + id
				+ ", " + (mergepath != null ? "mergepath=" + mergepath : "")
				+ "]";
	}
}
