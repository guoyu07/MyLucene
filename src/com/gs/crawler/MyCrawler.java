package com.gs.crawler;

import java.util.Set;

public class MyCrawler {
	/**
	 * ʹ�����ӳ�ʼ��URL ����
	 * 
	 * @return
	 * @param seeds
	 *            ����URL
	 */
	
	private int NumO2Crawl = 200;//ץȡ����
	private void initCrawlerWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++)
			LinkQueue.addUnvisitedUrl(seeds[i]);
	}

	/**
	 * ץȡ����
	 * 
	 * @return
	 * @param seeds
	 */
	public void crawling(String[] seeds) { // �������������ȡ��http://www.lietu.com
											// ��ͷ������
		LinkFilter filter = new LinkFilter() {
			public boolean accept(String url) {
				if (url.startsWith("http://news.qq.com"))
					return true;
				else
					return false;
			}
		};
		// ��ʼ��URL ����
		initCrawlerWithSeeds(seeds);
		// ѭ����������ץȡ�����Ӳ�����ץȡ����ҳ������NumO2Crawl
		while (!LinkQueue.unVisitedUrlsEmpty()
				&& LinkQueue.getVisitedUrlNum() <= NumO2Crawl) {
			// ��ͷURL ������
			String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
			if (visitUrl == null)
				continue;
			DownLoadFile downLoader = new DownLoadFile();
			// ������ҳ
			downLoader.downloadFile(visitUrl);
			// ��URL �����ѷ��ʵ�URL ��
			LinkQueue.addVisitedUrl(visitUrl);
			// ��ȡ��������ҳ�е�URL
			Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
			// �µ�δ���ʵ�URL ���
			for (String link : links) {
				LinkQueue.addUnvisitedUrl(link);
			}
		}
	}

	// main �������
	public static void main(String[] args) {
		MyCrawler crawler = new MyCrawler();
		crawler.crawling(new String[] { "http://news.qq.com" });
		System.out.println("Finished!");
	}
}