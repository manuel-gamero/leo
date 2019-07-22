package com.mg.web.jobs;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimeZone;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.tools.webcrawler.BasicCrawlerSitemap;
import com.mg.tools.webcrawler.CrawlerUrl;
import com.mg.util.communication.Mail;
import com.mg.web.util.ConnectionWebUtils;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.W3CDateFormat.Pattern;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class GenerateSitemap {

	private static Logger log = LogManager.getLogger(GenerateSitemap.class);
	private static File myDir;
	private static String urlSitemap = "http://latelierdeleo.local.com";
	private static ConfigService configService;
	private static final String subject = "GenerateSitemap Error - " + ConnectionWebUtils.getHostName();

	public static void main(String[] args) {
		try {
			log.info( ">>> Start GenerateSitemap" );
			configService = ServiceLocator.getService(ConfigServiceImpl.class);
			log.debug(" +++ +++ initializeProperties " );
			configService.initializeProperties();
			
			String url = configService.getSitemapUrl();
			log.info(" +++ +++ Sitemap url: " + url );
			urlSitemap = url;
			
			myDir = new File( configService.getRootPathWeb());
			Queue<CrawlerUrl> urlQueue = new LinkedList<CrawlerUrl>();
			urlQueue.add(new CrawlerUrl(url, 0));
			BasicCrawlerSitemap crawler = new BasicCrawlerSitemap(urlQueue, 2000, 3, 1000L, "", urlSitemap);
			crawler.crawl();
			crawler.displayUrl();

			// https://github.com/dfabulich/sitemapgen4j
			W3CDateFormat dateFormat = new W3CDateFormat(Pattern.MONTH);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

			//WebSitemapGenerator wsg = new WebSitemapGenerator(url, myDir);
			WebSitemapGenerator wsg = WebSitemapGenerator.builder(url, myDir).dateFormat(dateFormat).build();
			Collections.sort(crawler.getUrlSitemap());
			for (CrawlerUrl crawlerUrl : crawler.getUrlSitemap()) {
				WebSitemapUrl urlSitemap = new WebSitemapUrl.Options(
						crawlerUrl.getURL()).lastMod(new Date()).priority(1.0)
						.changeFreq(ChangeFreq.MONTHLY).build();
				wsg.addUrl(urlSitemap);
			}
			wsg.write();
			wsg.writeSitemapsWithIndex();

			log.info( "<<< End monitoring" );
		} catch (Exception e) {
			Mail.send(configService.getMailErrors(), subject , "Exception: " + e.getMessage());
			log.error(e.getMessage(), e);
		}
	}
}
