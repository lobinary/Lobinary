package com.l.web.index.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <pre>
 * 搜索结果对象
 * </pre>
 *
 * @ClassName: SearchResultDto
 * @author 919515134@qq.com
 * @date 2016年11月3日 上午9:46:01
 * @version V1.0.0
 */
public class SearchResultDto implements Serializable {
	
	private final static Logger logger = LoggerFactory.getLogger(SearchResultDto.class);

	private SearchResultDto() {
		super();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1998372158416631084L;

	/**
	 * 搜索字符串
	 */
	private Object searchObj;
	/**
	 * 结果数量
	 */
	private long resultNum;
	/**
	 * 开始位置
	 */
	private long start;
	/**
	 * 结束位置
	 */
	private long end;
	/**
	 * 错误信息
	 */
	private String errorMsg;

	private List<SubSearchResultDto> subSearchResultList;

	public long getResultNum() {
		return resultNum;
	}

	public void setResultNum(long resultNum) {
		this.resultNum = resultNum;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public List<SubSearchResultDto> getSubSearchResultList() {
		return subSearchResultList;
	}

	public void setSubSearchResultList(List<SubSearchResultDto> subSearchResultList) {
		this.subSearchResultList = subSearchResultList;
	}

	public Object getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(Object searchObj) {
		this.searchObj = searchObj;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 装配错误信息
	 * @param errorMsg
	 * @return
	 */
	public static SearchResultDto assembleError(String errorMsg){
		return assembleError(errorMsg,null);
	}
	/**
	 * 装配错误信息
	 * @param errorMsg
	 * @return
	 */
	public static SearchResultDto assembleError(String errorMsg,Throwable t){
		logger.error(errorMsg,t);
		SearchResultDto searchResultDto = new SearchResultDto();
		searchResultDto.setErrorMsg(errorMsg);
		return searchResultDto;
	}
	
	public static SearchResultDto newInstance(){
		SearchResultDto searchResultDto = new SearchResultDto();
		searchResultDto.setSubSearchResultList(new ArrayList<SubSearchResultDto>());
		return searchResultDto;
	}

	public void addSubSearchResultDto(SubSearchResultDto subSearchResultDto){
		this.subSearchResultList.add(subSearchResultDto);
		resultNum++;
	}

}
