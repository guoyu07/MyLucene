package com.gs.crawler;

import java.util.Set;

public class MyCrawler {
	/**
	 * 使用种子初始化URL 队列
	 * 
	 * @return
	 * @param seeds
	 *            种子URL
	 */
	
	private static int NumO2Crawl = 200;//抓取数量
	private void initCrawlerWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++)
			LinkQueue.addUnvisitedUrl(seeds[i]);
	}

	/**
	 * 抓取过程
	 * 
	 * @return
	 * @param seeds
	 */
	public void crawling(String[] seeds) { // 定义过滤器，提取以http://www.lietu.com
											// 开头的链接
		LinkFilter filter = new LinkFilter() {
			public boolean accept(String url) {
				if (url.startsWith("http://news"))
					return true;
				else
					return false;
			}
		};
		// 初始化URL 队列
		initCrawlerWithSeeds(seeds);
		// 循环条件：待抓取的链接不空且抓取的网页不多于NumO2Crawl
		while (!LinkQueue.unVisitedUrlsEmpty()
				&& LinkQueue.getVisitedUrlNum() <= NumO2Crawl) {
			// 队头URL 出队列
			String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
			if (visitUrl == null)
				continue;
			DownLoadFile downLoader = new DownLoadFile();
			// 下载网页
			downLoader.downloadFile(visitUrl);
			// 该URL 放入已访问的URL 中
			LinkQueue.addVisitedUrl(visitUrl);
			// 提取出下载网页中的URL
			Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
			// 新的未访问的URL 入队
			for (String link : links) {
				LinkQueue.addUnvisitedUrl(link);
			}
		}
	}

	// main 方法入口
	public static void main(String[] args) {
		MyCrawler crawler = new MyCrawler();
		NumO2Crawl = Integer.parseInt(args[0]);
		String[] seeds = new String[args.length];
		for(int i=1;i<args.length;i++){
			seeds[i-1] = args[i];
		}
		crawler.crawling(seeds);
		System.out.println("Finished!");
	}
}