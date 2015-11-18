package com.lobinary.platform.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Web网络方向的工具类
 * @author Lobinary
 * @since 2014年10月11日14:21:05
 * 
 */
public class WebUtil {
	

	/**
	 * 分页显示类似 2 3 4 5 6形式的转换方法,可以通过当前页和总页数来计算出当前应该显示的页码
	 * @author 吕斌
	 * @param currentPage
	 *            当前页数
	 * @param totalPage
	 *            总页数
	 * @return
	 */
	public static List<Integer> getPageNumList(int currentPage, int totalPage,int pageWidth) {
		int start = 1;
		int firstPage = 0;
		int lastPage = 0;
		if (currentPage < 1 || currentPage > totalPage || pageWidth < 2 || totalPage < 0) {
			return new ArrayList<Integer>();
		}
		if (totalPage <= pageWidth) {
			firstPage = start;
			lastPage = totalPage;
		} else {
			if (currentPage - pageWidth / 2 <= start) {
				firstPage = start;
				lastPage = start + pageWidth - 1;
			} else if (currentPage + pageWidth / 2 >= totalPage) {
				firstPage = totalPage - pageWidth + 1;
				lastPage = totalPage;
			} else {
				firstPage = currentPage - pageWidth / 2;
				if (pageWidth % 2 == 0) {
					lastPage = currentPage + pageWidth / 2 - 1;
				} else {
					lastPage = currentPage + pageWidth / 2;
				}
			}
		}
		List<Integer> result = new ArrayList<Integer>();
		for (int i = firstPage; i <= lastPage; i++) {
			result.add(i);
		}
		return result;
	}
	
	/**
	 * 通过response返回字符串数据
	 * @param response
	 * @param returnInfo
	 */
	public static void returnStr(HttpServletResponse response, String returnStr) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(returnStr);
		out.flush();
		out.close();
	}

}
