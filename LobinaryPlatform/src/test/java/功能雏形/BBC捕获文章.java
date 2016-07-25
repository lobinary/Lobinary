package 功能雏形;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.lobinary.platform.util.HttpUtil;

public class BBC捕获文章 {
	
	public static void main(String[] args) throws Exception {
		System.out.println("hello");
		String reqURL = "http://www.bbc.com/news/business-36834477";
		Map<String, String> params = null;
		String resp = HttpUtil.sendPostRequest(reqURL, params, "UTF-8", "UTF-8");
//		System.out.println(resp);
		String[] contextArray = resp.split("\n");
		String title = "";
		boolean startCatchContext = false;
		for (String context:contextArray) {
//			System.out.println("行："+context);
			if(context.contains("story-body__h1")){
				startCatchContext = true;
				String startIndex = "<h1 class=\"story-body__h1\">";
				title = context.substring(context.indexOf(startIndex)+startIndex.length(),context.indexOf("</h1>"));
//				System.out.println("捕获到标题：" + title);
			}
			if(context.contains("Share this story")){
				startCatchContext = false;
			}
			if(startCatchContext&&hasText(context)){
				catchText(context);
			}else if(startCatchContext&&context.trim().length()!=0&&!context.contains("<")&&!context.contains(">")){
				System.out.println("直接内容："+context);
			}
		}
		
	}

	
	private static String catchText(String context) {
		char[] contextArray = context.toCharArray();
		boolean signStart = false;
		StringBuilder sb = new StringBuilder();
//		System.out.println("***"+context);
		for (char c : contextArray) {
//			System.out.print("#"+c);
			if(signStart){
				if(c=='<'){
					signStart = false;
					if(sb.toString().trim().length()!=0){
						System.out.println("捕获到内容："+sb.toString());
						sb = new StringBuilder();
					}
				}else{
					sb.append(c);
				}
			}else{
				if(c=='>'){
					signStart = true;
				}
			}
		}
		System.out.println();
		return null;
	}


	private static boolean hasText(String context) {
		Pattern pattern = Pattern.compile(".*>.+<.*");
		return pattern.matcher(context.replace(" ", "")).matches();
	}

}
