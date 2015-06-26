package com.lobinary.normal.cav.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lobinary.normal.cav.dao.CAVDao;
import com.lobinary.normal.cav.dto.CAVDto;
import com.lobinary.normal.cav.util.HttpUtil;

public class WebService {
	
	public CAVDao cavDao = new CAVDao();
	public int start = 1642;
	public int end = 5244;
	public int threadNum = 1;
	
	public void service() throws SocketTimeoutException{
		
		for(int thread = 1;thread <= threadNum;thread++){
			new Thread(){
				@Override
				public void run() {
					while(true){
						int pageNum = getPageNum();
						boolean isSuccess = false;
						int times = 1;
						while(!isSuccess){
							long startTime = System.currentTimeMillis();
							try {
								isSuccess = go(pageNum);
							} catch (SocketTimeoutException e) {
//								e.printStackTrace();
							}
							long endTime = System.currentTimeMillis();
							System.out.println("第" + pageNum +"页处理,第" + times + "次执行" + (endTime-startTime) + "毫秒,结果为：" + isSuccess);
							times ++;
						}
					}
				}
				
			}.start();
		}
		
//		String test = "alt=\"蜜のめぐり逢い 森下まゆみ\" src=\"http://pics.dmm.co.jp/digital/video/oae00091/oae00091ps.jpg";
//		System.out.println(test.substring(test.indexOf("src=\"") + 5));
//		System.out.println(cl.get(1));
	}
	
	public synchronized int getPageNum(){
		start = start + 1;
		return start - 1;
	}
	
	public boolean go(int i) throws SocketTimeoutException{
		try {
//			Thread.sleep(5000);
			List<String> cl = catchDataFromWebsite(i);
			for(String s:cl){
				boolean result = cavDao.insert(analysisData(s));
			}
			return true;
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}
	
	
	public CAVDto analysisData(String s){
		CAVDto cav = new CAVDto();
//		System.out.println(s);
		String name = s.substring(s.indexOf("title=\"") + 7, s.indexOf("\" target=\"_blank\">"));
		cav.setName(name);
		cav.setNumber(catchStringFromString(s, "番号：</span>", "</li>"));
		cav.setDate(catchStringFromString(s, "发行时间：</span>", "</li>"));
		cav.setTime(catchStringFromString(s, "影片时长：</span>", "</li>"));
		cav.setDirector(catchStringFromString(s, "的所有作品\">", "</a></li>"));
		String makers = catchStringFromString(s, "制作商：", "<li><span class=\"classTitle\">发行商");
		cav.setMakers(catchStringFromString(makers, "的所有作品\">", "</a></li>"));
		String publisher = catchStringFromString(s, "发行商：", "<li><span class=\"classTitle\">系列：");
		cav.setPublisher(catchStringFromString(publisher, "的所有作品\">", "</a></li>"));
		cav.setSeries(catchStringFromString(s, "系列：</span>", "</li>"));
		String type = catchStringFromString(s, "影片类别：</span>", "</li>");
		String[] types = type.split("</a>");
		String tpyeTemp = "";
		for(String t :types){
			if(t.contains(">")){
				tpyeTemp = tpyeTemp + t.substring(t.indexOf(">") + 1) + ";";
			}else{
//				System.out.println("无效t：" + t);
			}
		}
		cav.setType(tpyeTemp);
		String actorTemp = "";
		if(s.contains("#888888;\">")){
			String[] actors = s.split("#888888;\">");
			for(String a :actors){
				if(a.contains("番号")){
//					System.out.println("顶端数据，予以pass");
				}else{
					actorTemp = actorTemp + a.substring(0,a.indexOf("</span>")) + ";";
				}
			}
		}else{
//			System.out.println("未发现演员");
		}
		cav.setActor(actorTemp);
		cav.setDetailurl(s.substring(s.indexOf("<a href=\"") + 9, s.indexOf("\" title=\"")));
		String imageUrl = catchStringFromString(s, "<img", "/></a>").replace("\"", "").replace(" ", "");
		cav.setImageurl(imageUrl.substring(imageUrl.indexOf("src=\"") + 5));
		cav.setImagelocalpath("");
		return cav;
	}
	
	/**
	 * 捕获数据从网站
	 * @return
	 * @throws SocketTimeoutException 
	 */
	public List<String> catchDataFromWebsite(int page) throws SocketTimeoutException{
		List<String> contentsList = new ArrayList<String>();
		Map<String,String> paramMap = new HashMap<String,String>();
		//http://instsee.com/default.aspx?time=published&p1=
		//http://instsee.com/default.aspx?time=published&p1=&page=2
		//http://instsee.com/default.aspx?time=published&p1=&page=3
		String s = HttpUtil.sendPostRequest("http://instsee.com/default.aspx?time=published&p1=&page=" + page, paramMap, "UTF-8", "UTF-8");
		String contentStr = "";
		try {
			contentStr = s.substring(s.indexOf("<div id=\"list_games\">"));
		} catch (Exception e) {
//			System.out.println("发生错误：网站返回数据为：");
//			System.out.println(s);
		}
		contentStr = contentStr.substring(0, contentStr.indexOf("<div id=\"listpager\" class=\"pagination\">"));
		String[] contents = contentStr.split("<div class=\"list_game\">");
//		System.out.println(contents[0]);
//		System.out.println("=========================");
//		System.out.println(contents[19]);
		for(String content:contents){
			if(content.contains("番号")){
				contentsList.add(content);
			}
		}
//		System.out.println("从" + contents.length + "条数据中获取到" + contentsList.size() + "条有效数据");
		return contentsList;
	}
	
	public static void main(String[] args) throws SocketTimeoutException {
		WebService ws = new WebService();
		ws.service();
	}
	
	
	/**
	 * 从字符串中根据前后缀，获取中间字符串
	 * @param pre
	 * @param rear
	 */
	public static String catchStringFromString(String str,String pre,String rear) {
		
		String result = "";
		try {
			int start = str.indexOf(pre);
			int end = start + str.substring(start).indexOf(rear);
			if(start<end){
				result = str.substring(start+pre.length(),end);
			}
		} catch (Exception e) {
			System.out.println("从字符串中捕获参数异常，异常原因为：" + e);
		}
		return result;
	}

}
