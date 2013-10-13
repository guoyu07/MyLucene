/**
 * GS
 */
package com.gs.model;

import com.gs.DAO.Entity;
import com.gs.DAO.PK;


/**
 * @author GaoShen
 * @packageName com.gs.model
 */
@Entity
public class Page {
	/**
	 * 
	 */
	public Page() {
	}

	/**
	 * @param startoffset
	 * @param endoffset
	 * @param url
	 * @param id
	 * @param path
	 */
	public Page(long startoffset, long endoffset, String url, int id,
			String path) {
		this.startoffset = startoffset;
		this.endoffset = endoffset;
		this.url = url;
		this.id = id;
		this.path = path;
	}

	private long startoffset;
	private long endoffset;
	private String url;
	private int id;
	private String path;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the id
	 */
	@PK
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the startoffset
	 */
	public long getStartoffset() {
		return startoffset;
	}

	/**
	 * @param startoffset
	 *            the startoffset to set
	 */
	public void setStartoffset(long startoffset) {
		this.startoffset = startoffset;
	}

	/**
	 * @return the endoffset
	 */
	public long getEndoffset() {
		return endoffset;
	}

	/**
	 * @param endoffset
	 *            the endoffset to set
	 */
	public void setEndoffset(long endoffset) {
		this.endoffset = endoffset;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "URL = " + url + " Start = " + startoffset + " End = "
				+ endoffset + " Id = " + id;
	}

}
