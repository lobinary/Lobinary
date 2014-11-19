package com.lobinary.platform.model;

import java.util.List;

import com.lobinary.platform.util.WebUtil;

/**
 * 
 * 页面参数类
 * 
 */
public class PageParameter {
	
	/**
	 * 数据库起始行数
	 */
	private int startRow = 0;
	/**
	 * 数据库结束行数
	 */
	private int endRow = 20;
	/**
	 * 每页的页面条数
	 */
	private int pageSize = 20;
	/**
	 * 当前页数
	 */
	private int currentPage = 1;
	/**
	 * 页码显示数 如 ：
	 * 《  2 3 4 5 6 》 则 pageWidth为 5 
	 * 《 5 6 7 》 则 pageWidth 为 3
	 */
	private int pageWidth = 5;
	/**
	 * 总行数
	 */
	private int countRows = 20;
	/**
	 * 总页数
	 */
	private int countPage = 1;
	/**
	 * 页码供显示用列表
	 */
	private List<Integer> pageNumList;
	
	
	/**
	 * 初始化参数，防止出现参数异常
	 */
	public PageParameter() {
		super();
		this.startRow = 0;
		this.endRow = 20;
		this.pageSize = 20;
		this.currentPage = 1;
		this.pageWidth = 5;
		this.countRows = 20;
		this.countPage = 20;
//		this.pageNumList = new ArrayList<Integer>(); //暂时不需要提前产生此对象
	}

	/**
	 * 通过页面条数和当前页数，计算出起始行数
	 */
	public void calculateStartRow(){
		this.startRow = this.pageSize * (this.currentPage-1);
		System.out.println("############计算出起始行数：" + startRow );
	} 

	/**
	 * 计算出总页数
	 */
	public void calculateCountPage(){
		this.countPage = ((this.countRows - 1) / this.pageSize) + 1;
		System.out.println("############计算出总页数：" + countPage );
	}
	
	/**
	 * 计算出显示的页码形式
	 */
	public void calculatePageNumList(){
		this.pageNumList = WebUtil.getPageNumList(currentPage, countPage, pageWidth);
		System.out.println("############计算出显示的页码形式：" + pageNumList );
	}
	

	/**
	 * 获取起始行数
	 * @return
	 */
	public int getStartRow() {
		return startRow;
	}
	
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	
	public int getEndRow() {
		return endRow;
	}
	
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	/**
	 * 获取每页的页面条数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		calculateCountPage();
		calculateStartRow();
	}
	
	/**
	 * 获取当前页数
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		calculateStartRow();
		calculatePageNumList();
	}
	
	public int getPageWidth() {
		return pageWidth;
	}
	
	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
		calculatePageNumList();
	}
	
	public List<Integer> getPageNumList() {
		return pageNumList;
	}
	
	public void setPageNumList(List<Integer> pageNumList) {
		this.pageNumList = pageNumList;
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
		calculateCountPage();
		calculatePageNumList();
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
		calculatePageNumList();
	}
	
}
