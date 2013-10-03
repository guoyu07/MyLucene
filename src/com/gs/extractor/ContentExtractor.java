/**
 * GS
 */
package com.gs.extractor;

import org.apache.log4j.Logger;

/**
 * Extract the main text of an url
 * @author GaoShen
 * @packageName com.gs.extractor
 */
public interface ContentExtractor {
	public String extract(String url);
	public String extractFromHtml(String Html);
	
}