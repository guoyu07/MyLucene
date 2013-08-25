/**
 * GS
 */
package com.gs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author GaoShen
 * @packageName com.gs.model
 */
@Entity
@Table(name = "page")
@org.hibernate.annotations.Proxy(lazy = false)
public class Page {
	private String content;
	private long startoffset;
	private long endoffset;
	private String url;
	private String path;
	private int id;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	
	public void setId(int id) {
		this.id = id;
	}

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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
}
