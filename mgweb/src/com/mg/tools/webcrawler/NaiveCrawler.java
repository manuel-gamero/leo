package com.mg.tools.webcrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.web.util.ConnectionWebUtils;

public class NaiveCrawler {

	private static Logger log = LogManager.getLogger(NaiveCrawler.class);
	private static final String USER_AGENT = "User-agent:";
	private static final String DISALLOW = "Disallow:";
	public static final String REGEXP_HTTP = "<a href=[\"|\']http://(.)*[\"|\']>";
	public static final String REGEXP_RELATIVE = "<a href=[\"|\'](.)*[\"|\']>";
	private int maxNumberUrls;
	protected long delayBetweenUrls;
	private int maxDepth;
	private Pattern regexpSearchPattern;
	private Pattern httpRegexp;
	private Pattern relativeRegexp;
	private Map<String, CrawlerUrl> visitedUrls = null;
	private Map<String, Collection<String>> sitePermissions = null;
	private Queue<CrawlerUrl> urlQueue = null;
	private BufferedWriter crawlOutput = null;
	private BufferedWriter crawlStatistics = null;
	private int numberItemsSaved = 0;

	public NaiveCrawler(Queue<CrawlerUrl> urlQueue, int maxNumberUrls,
			int maxDepth, long delayBetweenUrls, String regexpSearchPattern)
			throws Exception {

		this.urlQueue = urlQueue;
		this.maxNumberUrls = maxNumberUrls;
		this.delayBetweenUrls = delayBetweenUrls;
		this.maxDepth = maxDepth;
		this.regexpSearchPattern = Pattern.compile(regexpSearchPattern);
		this.visitedUrls = new HashMap<String, CrawlerUrl>();

		this.sitePermissions = new HashMap<String, Collection<String>>();
		this.httpRegexp = Pattern.compile(REGEXP_HTTP);
		this.relativeRegexp = Pattern.compile(REGEXP_RELATIVE);
		crawlOutput = new BufferedWriter(new FileWriter("crawl.txt"));
		crawlStatistics = new BufferedWriter(new FileWriter(
				"crawlStatistics.txt"));
	}

	public void crawl() throws Exception {
		log.info(">>>>> Start carwl.");
		while (continueCrawling()) {
			CrawlerUrl url = getNextUrl();
			if (url != null) {
				//System.out.println(" Url: " + url.getUrlString() + " depth: " + url.getDepth());
				printCrawlInfo();
				String content = getContent(url);
				if (isContentRelevant(content, regexpSearchPattern)) {
					saveContent(url, content);
					Collection<String> urlStrings = extractUrls(content, url);
					addUrlsToUrlQueue(url, urlStrings);
				} else {
					log.debug(url + " is not relevant ignoring ...");
				}
				Thread.sleep(this.delayBetweenUrls);
			}
		}
		closeOutputStream();
		log.info("<<<<<< End carwl.");
	}

	protected boolean continueCrawling() {
		return ((!urlQueue.isEmpty()) && (getNumberOfUrlsVisited() < this.maxNumberUrls));
	}

	protected CrawlerUrl getNextUrl() throws IOException {
		CrawlerUrl nextUrl = null;
		while ((nextUrl == null) && (!urlQueue.isEmpty())) {
			CrawlerUrl crawlerUrl = this.urlQueue.remove();
			if (doWeHavePermissionToVisit(crawlerUrl)
					&& (!isUrlAlreadyVisited(crawlerUrl))
					&& isDepthAcceptable(crawlerUrl)) {
				nextUrl = crawlerUrl;
			}
		}
		return nextUrl;
	}

	protected void printCrawlInfo() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("Queue length = ").append(this.urlQueue.size())
				.append(" visited urls=").append(getNumberOfUrlsVisited())
				.append(" site permissions=")
				.append(this.sitePermissions.size());
		crawlStatistics.append("" + getNumberOfUrlsVisited())
				.append("," + numberItemsSaved)
				.append("," + this.urlQueue.size())
				.append("," + this.sitePermissions.size() + "\n");
		crawlStatistics.flush();
		log.info(sb.toString());
	}

	protected int getNumberOfUrlsVisited() {
		return this.visitedUrls.size();
	}

	protected void closeOutputStream() throws Exception {
		crawlOutput.flush();
		crawlOutput.close();
		crawlStatistics.flush();
		crawlStatistics.close();
	}

	protected boolean isDepthAcceptable(CrawlerUrl crawlerUrl) {
		return crawlerUrl.getDepth() <= this.maxDepth;
	}

	protected boolean isUrlAlreadyVisited(CrawlerUrl crawlerUrl) {
		if ((crawlerUrl.isVisited())
				|| (this.visitedUrls.containsKey(crawlerUrl.getUrlString()))) {
			return true;
		}
		return false;
	}

	public boolean doWeHavePermissionToVisit(CrawlerUrl crawlerUrl) throws IOException {
		if (crawlerUrl == null) {
			return false;
		}
		if (!crawlerUrl.isCheckedForPermission()) {
			crawlerUrl
					.setAllowedToVisit(computePermissionForVisiting(crawlerUrl));
		}
		return crawlerUrl.isAllowedToVisit();
	}

	protected boolean computePermissionForVisiting(CrawlerUrl crawlerUrl) throws IOException {
		URL url = crawlerUrl.getURL();
		boolean retValue = (url != null);
		if (retValue) {
			String host = url.getHost();
			Collection<String> disallowedPaths = this.sitePermissions.get(host);
			if (disallowedPaths == null) {
				disallowedPaths = parseRobotsTxtFileToGetDisallowedPaths(host);
			}
			String path = url.getPath();
			for (String disallowedPath : disallowedPaths) {
				if (path.contains(disallowedPath)) {
					retValue = false;
				}
			}
		}
		return retValue;
	}

	protected Collection<String> parseRobotsTxtFileToGetDisallowedPaths(String host) throws IOException {
		String robotFilePath = getContent("http://" + host + "/robots.txt");
		Collection<String> disallowedPaths = new ArrayList<String>();
		if (robotFilePath != null) {
			Pattern p = Pattern.compile(USER_AGENT);
			String[] permissionSets = p.split(robotFilePath);
			String permissionString = "";
			for (String permission : permissionSets) {
				if (permission.trim().startsWith("*")) {
					permissionString = permission.substring(1);
				}
			}
			p = Pattern.compile(DISALLOW);
			String[] items = p.split(permissionString);
			for (String s : items) {
				disallowedPaths.add(s.trim());
			}
		}
		this.sitePermissions.put(host, disallowedPaths);
		return disallowedPaths;
	}

	protected String getContent(String urlString) throws IOException {
		return getContent(new CrawlerUrl(urlString, 0));
	}

	protected String getContent(CrawlerUrl url) throws IOException {		
		HttpURLConnection connection = ConnectionWebUtils.getHttpConnetion(url.getUrlString());
		
		String text = null;
		try {
			int statusCode = connection.getResponseCode() ;//client.executeMethod(method);
			if (statusCode == 200 /*HttpStatus.SC_OK*/ ) {
				text = readContentsFromStream(new InputStreamReader(connection.getInputStream()));
			}
		} catch (Throwable t) {
			System.out.println(t.toString());
			t.printStackTrace();
		} finally {
			connection.disconnect();
		}
		markUrlAsVisited(url);
		return text;
	}

	protected static String readContentsFromStream(Reader input)
			throws IOException {
		BufferedReader bufferedReader = null;
		if (input instanceof BufferedReader) {
			bufferedReader = (BufferedReader) input;
		} else {
			bufferedReader = new BufferedReader(input);
		}
		String inputLine;
		StringBuilder sb = new StringBuilder();
		//char[] buffer = new char[4 * 1024];
		//int charsRead;
		/*while ((charsRead = bufferedReader.read(buffer)) != -1) {
			sb.append(buffer, 0, charsRead);
		}*/
		while ((inputLine = bufferedReader.readLine()) != null) {
			sb.append(inputLine);
		}
		bufferedReader.close();
		return sb.toString();
	}

	protected void markUrlAsVisited(CrawlerUrl url) {
		this.visitedUrls.put(url.getUrlString(), url);
		url.setIsVisited();
	}

	public List<String> extractUrls(String text, CrawlerUrl crawlerUrl) {
		Map<String, String> urlMap = new HashMap<String, String>();
		extractHttpUrls(urlMap, text);
		extractRelativeUrls(urlMap, text, crawlerUrl);
		return new ArrayList<String>(urlMap.keySet());
	}

	protected void extractHttpUrls(Map<String, String> urlMap, String text) {
		if( text != null){
			Matcher m = httpRegexp.matcher(text);
			while (m.find()) {
				String url = m.group();
				String[] terms = url.split("a href=\"");
				for (String term : terms) {
					if (term.startsWith("http")) {
						int index = term.indexOf("\"");
						if (index > 0) {
							term = term.substring(0, index);
						}
						//urlMap.put(term, term);
						addUrlToMap(urlMap, term);
					}
				}
				terms = url.split("a href=\'");
				for (String term : terms) {
					if (term.startsWith("http")) {
						int index = term.indexOf("\'");
						if (index > 0) {
							term = term.substring(0, index);
						}
						//urlMap.put(term, term);
						addUrlToMap(urlMap, term);
					}
				}
			}
		}
	}

	protected void addUrlToMap(Map<String, String> urlMap, String term) {
		if(!term.contains("jpg")){
			urlMap.put(term, term);
		}
		
	}

	protected void extractRelativeUrls(Map<String, String> urlMap, String text,
			CrawlerUrl crawlerUrl) {
		if( text != null ){
			Matcher m = relativeRegexp.matcher(text);
			URL textURL = crawlerUrl.getURL();
			String host = textURL.getHost();
			while (m.find()) {
				String url = m.group();
				String[] terms = url.split("a href=\"");
				for (String term : terms) {
					if (term.startsWith("/")) {
						int index = term.indexOf("\"");
						if (index > 0) {
							term = term.substring(0, index);
						}
						String s = "http://" + host + term;
						//urlMap.put(s, s);
						addUrlToMap(urlMap, s);
					}
				}
				
				terms = url.split("a href=\'");
				for (String term : terms) {
					if (term.startsWith("/")) {
						int index = term.indexOf("\'");
						if (index > 0) {
							term = term.substring(0, index);
						}
						String s = "http://" + host + term;
						//urlMap.put(s, s);
						addUrlToMap(urlMap, s);
					}
				}
			}
		}
	}

	protected void addUrlsToUrlQueue(CrawlerUrl url, Collection<String> urlStrings) {
		int depth = url.getDepth() + 1;
		for (String urlString : urlStrings) {
			if (!this.visitedUrls.containsKey(urlString)) {
				this.urlQueue.add(new CrawlerUrl(urlString, depth));
			}
		}
	}

	public static boolean isContentRelevant(String content,
			Pattern regexpPattern) {
		boolean retValue = false;
		if (content != null) {
			Matcher m = regexpPattern.matcher(content.toLowerCase());
			retValue = m.find();
		}
		return retValue;
	}

	protected void saveContent(CrawlerUrl url, String content) throws Exception {
		this.crawlOutput.append(url.getUrlString()).append("\n");
		numberItemsSaved++;
	}

	public static void main(String[] args) {
		try {
			Queue<CrawlerUrl> urlQueue = new LinkedList<CrawlerUrl>();
			//String url = "http://www.latelierdeleo.com";
			String url = "http://localhost" ; // "http://localhost/fr/homePage"; //;
			//String regexp = "collective.*intelligence";
			String regexp = "atelier.*";
			urlQueue.add(new CrawlerUrl(url, 0));
			NaiveCrawler crawler = new NaiveCrawler(urlQueue, 2000, 3, 1000L,
					regexp);
			crawler.crawl();
		} catch (Throwable t) {
			System.out.println(t.toString());
		}
	}
}