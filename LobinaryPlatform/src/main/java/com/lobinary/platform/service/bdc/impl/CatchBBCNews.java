package com.lobinary.platform.service.bdc.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.platform.model.pojo.bdc.News;
import com.lobinary.platform.service.bdc.CatchNews;
import com.lobinary.platform.util.HttpUtil;

public class CatchBBCNews extends CatchNews{

	static Logger logger = LoggerFactory.getLogger(CatchBBCNews.class);
	
	@Override
	public List<News> catchNews() {
		List<News> newsList = null;
		try {
			Set<String> newsUrlSet = catchNewsUrl();
			newsList = assembleNews(newsUrlSet);
		} catch (Exception e) {
			logger.error("捕获BBC新闻发生异常,异常原因如下",e);
			newsList = new ArrayList<News>();
		}
		return newsList;
	}

	/**
	 * 装配新闻
	 * @param newsUrlSet
	 * @return
	 */
	private List<News> assembleNews(Set<String> newsUrlSet) {
		return null;
	}

	/**
	 * 捕获新闻网址
	 * @return
	 * @throws Exception
	 */
	private Set<String> catchNewsUrl() throws Exception {
		String reqURL = "http://www.bbc.com/news";
		Map<String, String> params = null;
		String resp = HttpUtil.sendPostRequest(reqURL, params, "UTF-8", "UTF-8");
		//System.out.println(resp);
		String[] contextArray = resp.split("\n");
		String s = "<a href=\"/news/";
		Pattern pattern = Pattern.compile(".*[0-9]+.*");
		Set<String> newsUrlSet = new HashSet<String>();
		for (int i = 0; i < contextArray.length; i++) {
			String context = contextArray[i];
			if(context.contains(s)&&pattern.matcher(context).matches()){
//				System.out.println(context);
				String newsUrl = null;
				try {
					newsUrl = context.substring(context.indexOf("/news/"), context.indexOf("\" class="));
				} catch (Exception e) {
//					System.out.println("捕获异常："+e.getMessage()+"，准备进行其他方式的捕获");
					newsUrl = context.substring(context.indexOf("/news/"), context.indexOf("\">"));
				}
				newsUrlSet.add(newsUrl);
			}
		}
//		for (String newsUrl : newsUrlSet) {
//			System.out.println("捕获到新闻链接：http://www.bbc.com"+newsUrl);
//		}
		return newsUrlSet;
	}

}
