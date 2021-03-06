package com.l.web.house.util;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.l.web.house.enumeration.PageEnum;

public class PU {
	private final static Logger logger = LoggerFactory.getLogger(PU.class);
	
	/**
	 * <div class="base">
                <div class="name">基本属性</div>
                <div class="content">
                  <ul>
                                        <li><span class="label">房屋户型</span>1室0厅1厨1卫</li>
                    <li><span class="label">所在楼层</span>低楼层 (共29层)</li>
                    <li><span class="label">建筑面积</span>53㎡</li>
                                          <li><span class="label">户型结构</span>平层</li>
                                        <li><span class="label">套内面积</span>41㎡</li>
                                          <li><span class="label">建筑类型</span>塔楼</li>
                                        <li><span class="label">房屋朝向</span>东</li>
                    <li><span class="label">建筑结构</span>钢混结构</li>
                    <li><span class="label">装修情况</span>简装</li>
                                            <li><span class="label">梯户比例</span>三梯十户</li>
                                                                  <li><span class="label">供暖方式</span>集中供暖</li>
                                                                  <li><span class="label">配备电梯</span>有</li>
                                          <li><span class="label">产权年限</span>70年</li>
                                                            </ul>
                </div>
              </div>
	 * @param args
	 * @throws Exception
	 */
	/**
	 * <div class="areaName">
	 * 		<i></i>
	 * 		<span class="label">所在区域</span>
	 * 		<span class="info">
	 * 			<a href="/ershoufang/yanjiao/" target="_blank">燕郊</a>&nbsp;
	 * 			<a href="/ershoufang/yanshunluguodao/" target="_blank">燕顺路国道</a>&nbsp;
	 * 		</span>
	 * 		<a href="" class="supplement" title="" style="color:#394043;"></a>
	 * </div>
	 */
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		String rs = HttpUtil.doGet("http://bj.lianjia.com/ershoufang/101101143187.html");
//		System.out.println(rs);
		String 基本属性 = PU.parser(rs,1,PU.Attribute("class","price "),PU.Tag("span"));
		System.out.println(基本属性);
		基本属性 = PU.parser(rs,1,PU.Attribute("class","unitPrice"),PU.Tag("span"));
		System.out.println(基本属性);
		
		System.out.println(PU.parser(rs,PU.Attribute("class","panel"),PU.Attribute("class","totalCount")).toHtml());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static Map<String,String> listUL(NodeList nl){
		Map<String,String> map = new HashMap<String,String>();
		nl = nl.elementAt(0).getChildren();
		for (int i = 0; i < nl.size(); i++) {
			Node 单个基本属性 = nl.elementAt(i);
			if(单个基本属性.toPlainTextString().trim().length()==0)continue;
			String 属性键 = 单个基本属性.getChildren().elementAt(0).toPlainTextString();
			String 属性值 = 单个基本属性.getChildren().elementAt(1).toPlainTextString();
//			System.out.println(i+":"+属性键+":"+属性值);
			map.put(属性键, 属性值);
		}
		return map;
	}
	
	public static void out(NodeList ns){
		SimpleNodeIterator elements = ns.elements();
		while (elements.hasMoreNodes()) {
				Node n = elements.nextNode();
				System.out.println(n.getClass());
				System.out.println(n.toHtml());
		}
	}
	
	public static boolean isUl(Node n){
		
		return false;
	}
	
	/**
	 * 通过参数捕获数据，多用于直接捕获文本数据
	 * @param html 
	 * @param stringIndex 元素位置，起始为1
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String parser(String html,int stringIndex, PUFilter... param) throws Exception {
		NodeList result = parser(html,  param);
		return result.elementAt(stringIndex-1).toPlainTextString();
	}
	
	/**
	 * 查找出parser中的数据
	 * @param parser
	 * @param param 标签或类名
	 * @return
	 * @throws Exception 
	 */
	public static NodeList parser(String html, PUFilter... param) throws Exception {
        Parser parser = new Parser(html);
        NodeList 基本属性Node = null;
        for (int i = 0; i < param.length; i++) {
        	PUFilter f = param[i];
        	if(i==0){
        		if(f.types == PageEnum.ATTRIBUTE && f.name.toLowerCase().equals("class")){
        			基本属性Node = parser.extractAllNodesThatMatch( new HasAttributeFilter(f.name, f.value));
        		}else{
        			throw new Exception("暂不支持首选filter为：["+f.types+"]");
        		}

//        		System.out.println(基本属性Node.toHtml());
//        		System.out.println("===============================");
        		continue;
        	}
        	
			try {
				if(f.types == PageEnum.ATTRIBUTE){
					Parser ps = new Parser(基本属性Node.toHtml());
					基本属性Node = ps.extractAllNodesThatMatch( new HasAttributeFilter(f.name, f.value));
				}else if(f.types == PageEnum.TAG){
					Parser ps = new Parser(基本属性Node.toHtml());
					基本属性Node = ps.extractAllNodesThatMatch( new TagNameFilter(f.name));
					if(f.index!=0){
						if(基本属性Node.size()<f.index){
							throw new Exception("标签："+f+"的位置index设置超限，当前为："+基本属性Node.size()+"，最大应为："+f.index);
						}
						Node elementAt = 基本属性Node.elementAt(f.index-1);
						基本属性Node.removeAll();
						基本属性Node.add(elementAt);
					}
				}else{
					throw new Exception("暂不支持的PageEnum["+f.types+"]类型");
				}
			} catch (Exception e) {
				logger.error("====================================================================================================");
				logger.error(基本属性Node.toHtml(),e);
				logger.error("====================================================================================================");
				throw new Exception("捕获数据["+f+"]异常：");
			}
			
    		if(基本属性Node==null){
    			throw new Exception("捕获数据异常，筛选数据为null，当前筛选条件为:"+f);
    		}
//    		System.out.println(基本属性Node.toHtml());
//    		System.out.println("===============================");
		}
		return 基本属性Node;
	}

	public static PUFilter Tag(String tagName) {
		return new PUFilter(PageEnum.TAG,tagName);
	}
	public static PUFilter Tag(String tagName,int index) {
		return new PUFilter(PageEnum.TAG,tagName,index);
	}

	public static PUFilter Attribute(String name,String value){
		return new PUFilter(PageEnum.ATTRIBUTE,name,value);
	}
	
	/**
	 * 过滤器
	 * @author Lobinary
	 *
	 */
	public static class PUFilter{
		@Override
		public String toString() {
			return "PUFilter [types=" + types + ", name=" + name + ", value=" + value + ", index=" + index + "]";
		}
		private PageEnum types;//类型
		private String name;//某些需要的name
		private String value;//值
		private int index=0;//位置 1，2，3，没有0
		public PUFilter(PageEnum types, String name) {
			super();
			this.types = types;
			this.name = name;
		}
		public PUFilter(PageEnum types, String name, String value, int index) {
			super();
			this.types = types;
			this.name = name;
			this.value = value;
			this.index = index;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PageEnum getTypes() {
			return types;
		}
		public int getIndex() {
			return index;
		}
		public PUFilter(PageEnum types, String name, String value) {
			super();
			this.types = types;
			this.name = name;
			this.value = value;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public PUFilter(PageEnum types, String name, int index) {
			super();
			this.types = types;
			this.name = name;
			this.index = index;
		}
		public void setTypes(PageEnum types) {
			this.types = types;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
}
