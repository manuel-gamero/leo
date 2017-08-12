package com.mg.tools.webcrawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.communication.Mail;
import com.mg.web.util.ConnectionWebUtils;

public class BasicCrawlerSitemap extends NaiveCrawler {
	
	private static Logger log = Logger.getLogger(BasicCrawlerSitemap.class);
	private List<CrawlerUrl> urlSitemap = null;
	private long max = 0;
	private long min  = 9999999999l;
	private long sum = 0;
	private static String uri = "http://localhost" ;

	public BasicCrawlerSitemap(Queue<CrawlerUrl> urlQueue, int maxNumberUrls,
			int maxDepth, long delayBetweenUrls, String regexpSearchPattern, String uri)
			throws Exception {
		super(urlQueue, maxNumberUrls, maxDepth, delayBetweenUrls, regexpSearchPattern);
		BasicCrawlerSitemap.uri = uri;
	}

	public void crawl() throws Exception {
		log.info(">>>>> Start BasicCrawlerSitemap.");
		boolean sendMail = ServiceLocator.getService(ConfigServiceImpl.class).isDebugSendEmail();
		String subject = "BasicCrawlerSitemap Error ";
		urlSitemap = new ArrayList<CrawlerUrl>();
		while (continueCrawling()) {
			CrawlerUrl url = getNextUrl();
			if( url != null ){
				log.debug("Processing url: " + url.getUrlString() + " depth: " + url.getDepth() );
				if (url != null) {
					printCrawlInfo();
					long start = System.currentTimeMillis();
					String content = getContent(url);
					long elapsed = System.currentTimeMillis() - start;
					if( min >= elapsed){
						min = elapsed;
					}
					if( max <= elapsed ){
						max = elapsed;
					}
					sum = sum + elapsed;
					if( validURrl(url.getUrlString()) ){
						urlSitemap.add(url);
					}
					if(elapsed > ConnectionWebUtils.CONNECTIONTIMEMAX && sendMail){
						Mail.send(ServiceLocator.getService(ConfigServiceImpl.class).getMailErrors(), subject , "url: " + url + " slow. ");
					}
					Collection<String> urlStrings = extractUrls(content, url);
					addUrlsToUrlQueue(url, urlStrings);
					Thread.sleep(this.delayBetweenUrls);
				}
			}
		}
		closeOutputStream();
		log.info("<<<<<< End BasicCrawlerSitemap.");
	}
	
	private boolean validURrl(String urlString) {
		if(urlString.contains("action")){
			return false;
		}
		if( urlString.contains(uri)){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			Queue<CrawlerUrl> urlQueue = new LinkedList<CrawlerUrl>();
			urlQueue.add(new CrawlerUrl(uri, 0));
			BasicCrawlerSitemap crawler = new BasicCrawlerSitemap(urlQueue, 2000, 3, 1000L, "", "http://localhost");
			crawler.crawl();
			crawler.displayUrl();
		} catch (Exception e) {
			System.out.println( "ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void displayUrl(){
		log.info(" Urls: " + urlSitemap.size());
		log.info(" Max: " + max);
		log.info(" Min: " + min);
		log.info(" Averg: " +  (sum/urlSitemap.size()) );
		
		if(log.isDebugEnabled()){
			for (CrawlerUrl item : urlSitemap) {
				log.debug(" Url: " + item.getUrlString());
			}
		}
	}

	public List<CrawlerUrl> getUrlSitemap() {
		return urlSitemap;
	}

}
