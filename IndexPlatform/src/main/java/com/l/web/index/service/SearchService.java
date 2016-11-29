package com.l.web.index.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.l.web.index.dto.SearchResultDto;
import com.l.web.index.dto.SubSearchResultDto;
import com.l.web.index.util.DataUtil;
import com.l.web.index.web.controller.IndexController;

@Service
public class SearchService {
	
	private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	public SearchResultDto executeSearch(String s) {
		if(StringUtils.isEmpty(s)) return SearchResultDto.assembleError("搜索内容为空");
		
		SearchResultDto searchResultDto = SearchResultDto.newInstance();
		searchResultDto.setSearchObj(s);
		
		List<SubSearchResultDto> searchStore = DataUtil.getSearchStore();
		
		if(s.equals("全部数据")){
			searchResultDto.setSubSearchResultList(searchStore);
			return searchResultDto;
		}
		
		for (SubSearchResultDto subSearchResultDto : searchStore) {
			if(subSearchResultDto.match(s)){
				searchResultDto.addSubSearchResultDto(subSearchResultDto);
			}
		}
		
		logger.info("筛选字段['{}']，共筛选出结果{}条",s,searchResultDto.getResultNum());
		return searchResultDto;
	}
	
	

}
