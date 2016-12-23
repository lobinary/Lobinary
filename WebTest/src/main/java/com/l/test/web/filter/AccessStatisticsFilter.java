package com.l.test.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <pre>
 * 访问统计过滤器
 * </pre>
 *
 * @ClassName: KaptchaFilter
 * @author 919515134@qq.com
 * @date 2016年8月26日 下午5:39:02
 * @version V1.0.0
 */
public class AccessStatisticsFilter implements Filter {



	private static final Logger logger = LoggerFactory.getLogger(AccessStatisticsFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		logger.info("收到请求:{}",req.getRequestURL().toString());
		chain.doFilter(request, response);
	}

}
