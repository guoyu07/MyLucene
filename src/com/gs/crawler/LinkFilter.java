package com.gs.crawler;

public interface LinkFilter {
	public boolean accept(String url);
}
