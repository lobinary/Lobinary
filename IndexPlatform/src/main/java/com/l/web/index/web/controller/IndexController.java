package com.l.web.index.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.l.web.index.dto.SearchResultDto;
import com.l.web.index.service.SearchService;


@Controller
@RequestMapping("/index")
public class IndexController {
	
	private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	SearchService searchService;
	
	/**
	 * 商户信息页面
	 * 
	 * @param merchantNo
	 *            商编
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public SearchResultDto search(String s,Model model, HttpServletRequest request, HttpServletResponse response) {
		SearchResultDto searchResultDto = null;
		try {
			searchResultDto = searchService.executeSearch(s);
			return searchResultDto;
		} catch (Exception e) {
			logger.debug("搜索内容异常",e);
			return SearchResultDto.assembleError("搜索内容异常",e);
		}
	}

}
