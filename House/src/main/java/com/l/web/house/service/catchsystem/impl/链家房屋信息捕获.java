package com.l.web.house.service.catchsystem.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.l.web.house.mapper.房屋信息数据库;
import com.l.web.house.model.房屋交易信息;
import com.l.web.house.model.房屋基本信息;
import com.l.web.house.model.房屋户型信息;
import com.l.web.house.model.房屋照片信息;
import com.l.web.house.service.catchsystem.房屋信息捕获基类;
import com.l.web.house.util.HttpUtil;

@Service
public class 链家房屋信息捕获 extends 房屋信息捕获基类{

	private final static Logger logger = LoggerFactory.getLogger(链家房屋信息捕获.class);
	
	private final static String 房屋信息来源 = "链家";
	static int index = 0;
	@Resource
	SqlSessionFactory sqlSessionFactory;
	
	@Resource
	房屋信息数据库 房屋信息数据库;
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-ddHHmmss");
	
	public static void main(String[] args) throws Exception {
		房屋基本信息 f = getBaseInfo();
		房屋交易信息 j = new 房屋交易信息();
		f.网址 =  "http://bj.lianjia.com/ershoufang/101101067412.html";
		String rs = HttpUtil.doGet(f.网址);
//		System.out.println(rs);
		try {  
            Parser parser = new Parser(rs);  
            // TextExtractingVisitor visitor = new TextExtractingVisitor();  
            // parser.visitAllNodesWith(visitor);  
            // String textInPage = visitor.getExtractedText();  
            // System.out.println(textInPage);  
            //查找含有filter-strip__list样式的元素  
            String 标题 = 捕获数据(parser, new HasAttributeFilter("class", "main"));  
            System.out.println("标题:"+标题);
    		parser.reset();
            String 子标题 = 捕获数据(parser, new HasAttributeFilter("class", "sub"));  
            f.子标题 = 子标题;
            System.out.println("子标题"+子标题);
    		parser.reset();
    		String 首付S = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "tax")).elementAt(0).getChildren().elementAt(1).toPlainTextString().replace("首付", "").replace("万", "");
    		double 首付 = (Double.parseDouble(首付S))*10000;
    		j.首付 = 首付;
    		System.out.println("首付："+首付);
    		parser.reset();
    		String 税费S = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "tax")).elementAt(0).getChildren().elementAt(3).toPlainTextString().replace("税费", "").replace("万", "").replace("(仅供参考)", "");
    		double 税费 = (Double.parseDouble(税费S))*10000;
    		j.税费 = 税费;
    		System.out.println("税费："+税费);
    		parser.reset();
    		String 所在小区编号 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "communityName")).elementAt(0).getChildren().elementAt(6).getText().replace("a href=\"/xiaoqu/", "").replace("/\" target=\"_blank\" class=\"info\"", "");
    		f.所在小区编号 = 所在小区编号;
    		System.out.println("小区编号:"+所在小区编号);
    		parser.reset();
    		String 所在区县 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "areaName")).elementAt(0).getChildren().elementAt(6).getChildren().elementAt(0).toPlainTextString();
    		f.所在区县 = 所在区县;
    		System.out.println("所在区县:"+所在区县);
    		parser.reset();
    		String 所在地点 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "areaName")).elementAt(0).getChildren().elementAt(6).getChildren().elementAt(2).toPlainTextString();
    		f.所在地点 = 所在地点;
    		System.out.println("所在位置："+所在地点);
    		parser.reset();
    		String 所在环数 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "areaName")).elementAt(0).getChildren().elementAt(6).getChildren().elementAt(3).toPlainTextString().replace("&nbsp;", "");
    		f.所在环数 = 所在环数;
    		System.out.println("所在环数:"+所在环数);
    		parser.reset();
    		String 联系人 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "brokerName")).elementAt(0).getChildren().elementAt(1).toPlainTextString();
    		f.联系人 = 联系人;
    		System.out.println("联系人:"+联系人);
    		parser.reset();
    		String 联系信息 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "phone")).elementAt(1).toPlainTextString().replace("\n", "").replace(" ", "");
    		f.联系信息 = 联系信息;
    		System.out.println("联系信息:"+联系信息);
    		parser.reset();
    		System.out.println("==============================房屋基本属性=============================================");
    		Map<String,String> 属性Map = new HashMap<String,String>();
    		NodeList 基本属性 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "base")).elementAt(0).getChildren().elementAt(3).getChildren().elementAt(1).getChildren();
    		for (int i = 0; i < 基本属性.size(); i++) {
    			Node 单个基本属性 = 基本属性.elementAt(i);
    			if(单个基本属性.toPlainTextString().trim().length()==0)continue;
    			String 属性键 = 单个基本属性.getChildren().elementAt(0).toPlainTextString();
				String 属性值 = 单个基本属性.getChildren().elementAt(1).toPlainTextString();
				System.out.println(i+":"+属性键+":"+属性值);
    			属性Map.put(属性键, 属性值);
    		}
    		parser.reset();
    		NodeList 交易属性 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "transaction")).elementAt(0).getChildren().elementAt(3).getChildren().elementAt(1).getChildren();
    		for (int i = 0; i < 交易属性.size(); i++) {
    			Node 单个交易属性 = 交易属性.elementAt(i);
//    			System.out.println(单个交易属性.toPlainTextString());
    			if(单个交易属性.toPlainTextString().trim().length()==0)continue;
    			String 属性键 = 单个交易属性.getChildren().elementAt(0).toPlainTextString();
				String 属性值 = 单个交易属性.getChildren().elementAt(1).toPlainTextString();
				System.out.println(i+":"+属性键+":"+属性值);
    			属性Map.put(属性键, 属性值);
    		}
    		f.户型 = 属性Map.remove("房屋户型");
    		
    		//f.所在楼层 = 
    				属性Map.remove("所在楼层");//所在楼层:低楼层 (共6层)
    		f.建筑面积 = Double.parseDouble(属性Map.remove("建筑面积").replace("㎡", ""));
    		f.户型结构 = 属性Map.remove("户型结构");
    		f.实用面积 = Double.parseDouble(属性Map.remove("套内面积").replace("㎡", ""));
    		f.建筑类型 = 属性Map.remove("建筑类型");
    		f.朝向 = 属性Map.remove("房屋朝向");
    		f.建筑结构 = 属性Map.remove("建筑结构");
    		f.装修 = 属性Map.remove("装修情况");
    		f.梯户比例 = 属性Map.remove("梯户比例");
    		f.供暖 = 属性Map.remove("供暖方式");
    		f.电梯 = 属性Map.remove("配备电梯");
    		f.产权年限 = Integer.parseInt(属性Map.remove("产权年限").replace("年", ""));
    		SimpleDateFormat sdf挂牌时间 = new SimpleDateFormat("YYYY-MM-dd");
    		f.挂牌时间 = sdf挂牌时间.parse(属性Map.remove("挂牌时间"));
    		f.交易权属 = 属性Map.remove("交易权属");
    		f.上次交易时间 = sdf挂牌时间.parse(属性Map.remove("上次交易"));
    		f.房屋用途 = 属性Map.remove("房屋用途");
    		String 房本年限S = 属性Map.remove("房本年限");//满五年 满两年 不满两年
    		if(房本年限S.equals("满五年")){
    			f.房本年限 = 5;
    		}else if(房本年限S.equals("满两年")){
    			f.房本年限 = 2;
    		}else if(房本年限S.equals("不满两年")){
    			f.房本年限 = 1;
    		}else{
    			throw new Exception("未知房本年限");
    		}
    		
    		f.产权所属 = 属性Map.remove("产权所属");
    		f.抵押信息 = 属性Map.remove("抵押信息");
    		f.房本备注 = 属性Map.remove("房本备件");
    		if(属性Map.size()>0){
    			System.out.println("============发现未知基本属性============");
    			for (String k : 属性Map.keySet()) {
					System.out.println(k + ":" + 属性Map.get(k));
				}
    			System.out.println("============发现未知基本属性============");
    			throw new Exception("发现未知基本属性");
    		}
    		System.out.println("==============================================房源特色=============================================");
    		parser.reset();
    		Map<String,String> 房源特色Map = new HashMap<String, String>();
    		NodeList 房源特色 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "introContent showbasemore")).elementAt(0).getChildren();
    		for (int i = 0; i < 房源特色.size(); i++) {
    			Node 单个特色属性 = 房源特色.elementAt(i);
    			if(单个特色属性.toPlainTextString().trim().length()==0)continue;
    			String 单个特色属性S = 单个特色属性.toPlainTextString().replace("\n", "");
//    			System.out.println(单个特色属性S);
    			String[] 单个特色属性拆分 = 单个特色属性S.split(" ");
    			List<String> 特色属性list = new ArrayList<String>();
    			for (int jj = 0; jj < 单个特色属性拆分.length; jj++) {
    				String 单个特色属性SS = 单个特色属性拆分[jj];
    				if(单个特色属性SS.length()==0)continue;
    				特色属性list.add(单个特色属性SS);
//					System.out.println(i+":"+j+":"+单个特色属性SS);
				}
    			if(特色属性list.size()>=2){
    				String 特色属性拼合值 = "";
    				for (String 特色属性单个值 : 特色属性list) {
    					特色属性拼合值+=特色属性单个值+",";
					}
    				System.out.println(特色属性list.get(0)+":"+ 特色属性拼合值);
					房源特色Map.put(特色属性list.get(0), 特色属性拼合值);
    			}
    		}
    		String 递增备注 = "";
    		for (String k : 房源特色Map.keySet()) {
    			递增备注 += k + ":" + 房源特色Map.get(k) + ";";
			}
    		f.备注 += 递增备注;
    		System.out.println("==============================================房屋布局=============================================");
    		parser.reset();
    		NodeList 户型分布 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "list")).elementAt(0).getChildren().elementAt(1).getChildren();
    		for (int i = 0; i < 户型分布.size(); i++) {
    			Node 单个户型属性 = 户型分布.elementAt(i);
    			if(单个户型属性.getText().trim().length()==0)continue;
    			String 房间类型 = 单个户型属性.getChildren().elementAt(1).toPlainTextString();
    			double 房间面积 = Double.parseDouble(单个户型属性.getChildren().elementAt(3).toPlainTextString().replace("平米", ""));
    			String 有无窗户 = 单个户型属性.getChildren().elementAt(7).toPlainTextString();
    			String 窗户朝向 = 单个户型属性.getChildren().elementAt(5).toPlainTextString();
    			System.out.println("房间类型:"+ 房间类型);
    			System.out.println("房间面积:"+ 房间面积);
    			System.out.println("有无窗户:"+ 有无窗户);
    			System.out.println("窗户朝向:"+ 窗户朝向);
    			房屋户型信息 房屋户型信息 = new 房屋户型信息();
    			房屋户型信息.房屋基本信息id = f.id;
    			房屋户型信息.窗户朝向 = 窗户朝向;
    			房屋户型信息.房间类型 = 房间类型;
    			房屋户型信息.房间面积 = 房间面积;
    			System.out.println("===========================");
    		}
    		
    		
    		
    		//content-wrapper housePic
    		System.out.println("==============================================房屋照片=============================================");
    		parser.reset();
    		NodeList 房间照片主节点 = parser.extractAllNodesThatMatch( new HasAttributeFilter("class", "content-wrapper housePic"))
    				.elementAt(0).getChildren().elementAt(3).getChildren().elementAt(1).getChildren();
    		for (int i = 0; i < 房间照片主节点.size(); i++) {
    			Node 房间照片子节点 = 房间照片主节点.elementAt(i);
    			if(房间照片子节点.getText().trim().length()==0||!房间照片子节点.getText().contains("data-index"))continue;
    			String 房间照片未截取前字符串 = 房间照片子节点.getChildren().elementAt(1).getText();
//				System.out.println(房间照片未截取前字符串);
				String 照片URL = 房间照片未截取前字符串.substring(9,房间照片未截取前字符串.indexOf("jpg")+3);
				String 照片位置 = 房间照片未截取前字符串.substring(房间照片未截取前字符串.indexOf("alt=")+5,房间照片未截取前字符串.lastIndexOf("\""));
				System.out.println("照片URL:"+照片URL);
				System.out.println("照片位置:"+照片位置);
				房屋照片信息 房屋照片信息 = new 房屋照片信息();
				房屋照片信息.id = f.id + 照片位置 + i;
				房屋照片信息.房屋基本信息id = f.id;
				房屋照片信息.照片所属位置 = 照片位置;
				房屋照片信息.照片网络地址 = 照片URL;
				房屋照片信息.照片类型 = "jpg";
				System.out.println("=============================");
    		}
    		
    		parser.reset();
    		String 是否唯一 = rs.substring(rs.indexOf("isUnique")+10, rs.indexOf("registerTime")-9);
    		f.是否唯一 = 是否唯一;
    		System.out.println("是否唯一："+是否唯一);
    		if(!(是否唯一.equals("唯一住宅")||是否唯一.equals("不唯一"))){
    			throw new Exception("未知的是否唯一属性"+是否唯一);
    		}
        } catch (ParserException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
	}

	private static String 捕获数据(Parser parser, NodeFilter filter) throws ParserException {
		NodeList nodes = parser.extractAllNodesThatMatch(filter);  
		System.out.println("------抓取到" + nodes.size() + "条符合条件记录------");  
		for (int i = 0; i < nodes.size(); i++) {  
		    Node node = nodes.elementAt(i);  
//                System.out.println();  
//                System.out.println(node.getText());
		    NodeList cnode = node.getChildren();  
		    for (int j = 0; j < cnode.size(); j++) {  
		        Node tnode = cnode.elementAt(j);  
//		        System.out.println(tnode.toPlainTextString());  
		        return tnode.toPlainTextString();
		    }
//                System.out.println();  
//                System.out.println(node.getText());  
		}
		return null;
	}
	
	public void 捕获房屋坐标(){
		String 捕获坐标接口网址 = "http://bj.lianjia.com/ershoufang/housestat?hid=101101049486&rid=1111027379370";
	}

	@Override
	public void 捕获房屋信息() throws Exception {
		String 二手房筛选网址 = null;
		try {
			logger.info("准备捕获链家房屋信息");
			logger.info("准备获取总数据");
			int 最大页数 = 100;
			for (int i = 1; i <= 最大页数; i++) {
				二手房筛选网址 = "http://bj.lianjia.com/ershoufang/pg"+i+"l1l2p1p2/";//l1一室  l2两室  p1 200w内 p2 200~250w
				String rs = HttpUtil.doGet(二手房筛选网址);
				//h2 class="total fl">共找到<span> 1688 </span>套北京二手房<
//			System.out.println(rs);
				String 最大页数S = rs.substring(rs.indexOf("total fl")+20, rs.indexOf("套北京二手房")-7);
				最大页数 = Integer.parseInt(最大页数S.trim());
				最大页数 = (最大页数/30) + 1;
				rs = rs.substring(rs.indexOf("class=\"content"),rs.indexOf("bigImgList"));
				logger.info("========================================================================================================================");
				logger.info(rs);
				logger.info("==========================================================================子数据开始==============================================");
				String[] rsArray = rs.split("<div class=\"info clear\"><div class=\"title\"><a class=\"\" href=\"");
				for (String s : rsArray) {
					if(s.contains("data-log_index")&&!s.contains("左侧内容")){
						logger.info("========================================================================================================================");
						房屋概要信息捕获(s);
					}
				}
				logger.info("==========================================================================子数据结束==============================================");
				System.out.println("共有"+ 最大页数 +"页数据，当前为第"+i+"页数据");
				Thread.sleep(1000);
			}
			logger.info("总数据获取完毕，共计：20条数据，准备依次解析单个数据");
			logger.info("单个数据解析完毕，解析结果：成功19条，失败1条");
		} catch (Exception e) {
			System.out.println("当前访问网址为:"+二手房筛选网址);
			throw e;
		}
	}
	
	
	public static 房屋基本信息 getBaseInfo(){
		//		SqlSession session = sqlSessionFactory.openSession();
		房屋基本信息 房屋基本信息 = new 房屋基本信息();
		房屋基本信息.id = "20170204";
		房屋基本信息.房屋信息来源 = 房屋信息来源;

		房屋基本信息.id = "id1";
		房屋基本信息.房屋信息来源 = 房屋信息来源;
		房屋基本信息.房屋唯一标识 = "房屋唯一标识";//用来判断当价格变动情况下是唯一房屋的标识
		房屋基本信息.房屋类型 = "房屋类型";//一手房、二手房
		房屋基本信息.名称 = "名称";//自命名
		房屋基本信息.标题 = "标题";
		房屋基本信息.网址 = "网址";
		房屋基本信息.所在城市 = "北京";
		房屋基本信息.所在地点 = "";
		房屋基本信息.所在区县 = "";
		房屋基本信息.所在小区 = "所在小区";
		房屋基本信息.坐标 = "坐标";
		房屋基本信息.户型 = "户型";
		房屋基本信息.建筑面积 = 0d;
		房屋基本信息.实用面积 = 0d;
		房屋基本信息.朝向 = "朝向";
		房屋基本信息.装修 = "装修";
		房屋基本信息.供暖 = "供暖";
		房屋基本信息.产权年限 = 0;
		房屋基本信息.所在楼层 = 0;
		房屋基本信息.总楼层 = 0;
		房屋基本信息.户型结构 = "户型结构";
		房屋基本信息.建筑类型 = "建筑类型";
		房屋基本信息.建筑结构 = "建筑结构";
		房屋基本信息.梯户比例 = "梯户比例";
		房屋基本信息.电梯 = "电梯";
		房屋基本信息.用电类型 = "用电类型";
		房屋基本信息.用水类型 = "用水类型";
		房屋基本信息.燃气价格 = "燃气价格";
		
		房屋基本信息.挂牌时间 = null;
		房屋基本信息.上次交易时间 = null;
		房屋基本信息.房本年限 = 0;
		房屋基本信息.抵押信息 = "抵押信息";
		房屋基本信息.交易权属 = "交易权属";
		房屋基本信息.房屋用途 = "房屋用途";
		房屋基本信息.产权所属 = "产权所属";
		房屋基本信息.房本备注 = "房本备注";
		房屋基本信息.联系人 = "联系人";
		房屋基本信息.联系信息 = "联系信息";
//		房屋信息数据库.添加房屋基本信息(房屋基本信息);
		return 房屋基本信息;
	}
	
	public void test2(){
		/**
		 * 全北京概要数据
			min_price:150
			max_price:200
			room_count:2
			:
			city_id:110000
			callback:jQuery111106464018460828811_1486092353339
			_:1486092353480
		 */
		String 最小价格 = "150";//最小总价
		String 最大总价 = "200";//最大总价
		String 卧室数量 = "1";//房间格局  一室：1  两室 : 2
		String 城市代码="110000";
		String url = "http://ajax.lianjia.com/ajax/mapsearch/area/district?&"
				+ "min_price="+最小价格+"&"
				+ "max_price="+最大总价+"&"
				+ "room_count="+卧室数量+"&&"
				+ "city_id="+城市代码+"&"
				+ "callback=";
		String responseStr = HttpUtil.doGet(url);
//		responseStr = responseStr.substring(1,(responseStr.length()-1));
//		JSONObject jasonObject = JSONObject.fromObject(responseStr);
//		@SuppressWarnings("unchecked")
//		Map<Object,Object> map = (Map<Object,Object>)jasonObject;
//		List<JSONObject> 汇总数据 = (List<JSONObject>)map.get("data");
//		for (JSONObject k:汇总数据) {
////			logger.info(""+k);
//			logger.info(""+k.get("house_count"));
//			logger.info(""+k.getString("position_border").split(";").length);
//		}
		logger.info(responseStr);
	}
	
	public 房屋基本信息 房屋概要信息捕获(String ss) throws Exception{
		/**
		 * 
		 * http://bj.lianjia.com/ershoufang/101100879074.html" 
		 * target="_blank" data-log_index="1"   
		 * data-el="ershoufang"  data-sl="">东亚五环国际 不限购开间 可注册公司 低总价</a>
		 * <span class="yezhushuo tagBlock">房主自荐</span></div>
		 * <div class="address"><div class="houseInfo"><span class="houseIcon"></span>
		 * <a href="http://bj.lianjia.com/xiaoqu/1111047687491/" target="_blank" data-log_index="1" data-el="region">东亚五环国际 </a>
		 *  | 1室0厅 | 34.8平米 | 西 | 精装 | 有电梯</div></div>
		 *  <div class="flood"><div class="positionInfo"><span class="positionIcon"></span>中楼层(共20层)2014年建塔楼  -  
		 *  <a href="http://bj.lianjia.com/ershoufang/jiugong1/" target="_blank">旧宫</a></div></div>
		 *  <div class="followInfo"><span class="starIcon"></span>111人关注 / 共52次带看 / 2个月以前发布</div>
		 *  <div class="tag"><span class="five">房本满两年</span><span class="haskey">随时看房</span>
		 *  <span class="is_restriction">不限购</span></div><div class="priceInfo"><div class="totalPrice">
		 *  <span>125</span>万</div><div class="unitPrice" data-hid="101100879074" data-rid="1111047687491" data-price="35920">
		 *  <span>单价35920元/平米</span></div></div></div><div class="listButtonContainer">
		 *  <div class="btn-follow followBtn" data-hid="101100879074"><span class="follow-text">关注</span></div>
		 *  <div class="compareBtn LOGCLICK" data-hid="101100879074" log-mod="101100879074" data-log_evtid="10230">加入对比</div></div></li>
		 *  <li class="clear"><a class="img " href="http://bj.lianjia.com/ershoufang/101100591721.html" target="_blank"data-log_index="2" data-el="ershoufang" data-sl="">
		 *  <img class="lj-lazy" src="http://s1.ljcdn.com/feroot/pc/asset/img/blank.gif?_v=20170119161557" 
		 *  data-original="http://image1.ljcdn.com/110000-inspection/80ab76cc-1204-4ae1-87bc-116ee402030d.jpg.232x174.jpg" alt="地铁6号线 民水民电LOFT 有燃气"></a>
		*/
//		String ss = "http://bj.lianjia.com/ershoufang/101100879074.html\" target=\"_blank\" data-log_index=\"1\"   data-el=\"ershoufang\"  data-sl=\"\">东亚五环国际 不限购开间 可注册公司 低总价</a><span class=\"yezhushuo tagBlock\">房主自荐</span></div><div class=\"address\"><div class=\"houseInfo\"><span class=\"houseIcon\"></span><a href=\"http://bj.lianjia.com/xiaoqu/1111047687491/\" target=\"_blank\" data-log_index=\"1\" data-el=\"region\">东亚五环国际 </a> | 1室0厅 | 34.8平米 | 西 | 精装 | 有电梯</div></div><div class=\"flood\"><div class=\"positionInfo\"><span class=\"positionIcon\"></span>中楼层(共20层)2014年建塔楼  -  <a href=\"http://bj.lianjia.com/ershoufang/jiugong1/\" target=\"_blank\">旧宫</a></div></div><div class=\"followInfo\"><span class=\"starIcon\"></span>111人关注 / 共52次带看 / 2个月以前发布</div><div class=\"tag\"><span class=\"five\">房本满两年</span><span class=\"haskey\">随时看房</span><span class=\"is_restriction\">不限购</span></div><div class=\"priceInfo\"><div class=\"totalPrice\"><span>125</span>万</div><div class=\"unitPrice\" data-hid=\"101100879074\" data-rid=\"1111047687491\" data-price=\"35920\"><span>单价35920元/平米</span></div></div></div><div class=\"listButtonContainer\"><div class=\"btn-follow followBtn\" data-hid=\"101100879074\"><span class=\"follow-text\">关注</span></div><div class=\"compareBtn LOGCLICK\" data-hid=\"101100879074\" log-mod=\"101100879074\" data-log_evtid=\"10230\">加入对比</div></div></li><li class=\"clear\"><a class=\"img \" href=\"http://bj.lianjia.com/ershoufang/101100591721.html\" target=\"_blank\"data-log_index=\"2\" data-el=\"ershoufang\" data-sl=\"\"><img class=\"lj-lazy\" src=\"http://s1.ljcdn.com/feroot/pc/asset/img/blank.gif?_v=20170119161557\" data-original=\"http://image1.ljcdn.com/110000-inspection/80ab76cc-1204-4ae1-87bc-116ee402030d.jpg.232x174.jpg\" alt=\"地铁6号线 民水民电LOFT 有燃气\"></a>";
		System.out.println(ss);
		
		String 房屋唯一标识 = ss.substring(ss.indexOf("ershoufang")+11, ss.indexOf("html")-1);
		System.out.println("唯一标识:"+房屋唯一标识);

		String 网址  = ss.substring(0, ss.indexOf("\""));
		System.out.println("网址:"+网址);
		String 标题 = ss.substring(ss.indexOf("data-sl=\"\">")+11, ss.indexOf("</a>"));
		System.out.println("标题:"+标题);
		String xx = ss.substring(ss.indexOf("region")+8,ss.indexOf("flood"));
		if(xx.contains("独栋别墅")){
			xx = xx.replace(" | 独栋别墅", "");
		}
		String[] sp = xx.split("\\|");

		String 所在小区 = sp[0].substring(0,sp[0].length()-5).trim();
		System.out.println("小区:"+所在小区);
		String 户型 = sp[1].trim();
		System.out.println("户型:"+户型);
		String 建筑面积S = sp[2].trim().replace("平米", "");
		double 建筑面积 = Double.parseDouble(建筑面积S);
		System.out.println("总面积:"+建筑面积);
		String 朝向 = sp[3].trim().replace(" ", "");
		System.out.println("朝向:"+朝向);
		String 装修 = null;
		if(sp.length>4){
			String zx = sp[4].trim();
			装修 = zx.contains("<")?zx.substring(0, sp[4].indexOf("<")).replace("<", ""):zx;
			boolean b = 装修.contains("其他")||装修.contains("毛坯")||装修.contains("简装")||装修.contains("精装");
			if(!b){
				throw new Exception("位置装修类型："+装修);
			}
		}
		System.out.println("装修:"+装修);
		String 电梯 = "无电梯";
		if(sp.length>5){
			if(sp[5].contains("有电梯")){
				电梯 = "有电梯";
				System.out.println("电梯:"+电梯);
			}else if(sp[5].contains("无电梯")){
				电梯 = "无电梯";
				System.out.println("电梯:"+电梯);
			} else{
				System.out.println("未知属性："+sp[5]);
				throw new Exception();
			}
			
		}
		String 楼层 = ss.substring(ss.indexOf("positionIcon")+21, ss.indexOf("followInfo")-28);
		/**
		 * 中楼层(共16层)2008年建板楼 - 燕郊城区
		 * 5层2006年建板楼 - 北七家
		 * 2002年建板塔结合 - 木樨园
		 */
		Date 建房时间 = null;
		String 建筑类型 = null;
		String 所在地点;
		int 总楼层 = 0;
		int 所在楼层 = 0;
		if(楼层.contains("(")){//中楼层(共16层)2008年建板楼 - 燕郊城区
			String 总楼层Str = 楼层.substring(楼层.indexOf("(")+2,楼层.indexOf(")")-1);
			System.out.println("总楼层:"+总楼层Str);
			总楼层 = Integer.parseInt(总楼层Str);
			if(楼层.contains("低楼层")){
				 所在楼层 = 总楼层/3/2;
			}else if(楼层.contains("中楼层")){
				 所在楼层 = 总楼层/2;
			}else if(楼层.contains("高楼层")){
				 所在楼层 = 总楼层-(总楼层/3/2);
			}else if(楼层.contains("地下室")){
				 所在楼层 = -1;
			}else if(楼层.contains("未知楼层")){
				 所在楼层 = 0;
			}else {
				System.out.println("未知楼层："+楼层);
				throw new Exception();
			}
			System.out.println("所在楼层："+ 所在楼层);
			if(楼层.contains("年")){
				String 建房年份S = 楼层.substring(楼层.indexOf(")")+1,楼层.indexOf("年"));
				Calendar c = Calendar.getInstance();
				c.set(Calendar.YEAR, Integer.parseInt(建房年份S));
				c.set(Calendar.MONTH,0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				建房时间 = c.getTime();
				System.out.println("建房时间:" + sdf.format(建房时间));
				建筑类型 = 楼层.substring(楼层.indexOf("年建")+2, 楼层.indexOf("-")-2);
			}else{
				建筑类型 = 楼层.substring(楼层.indexOf(")")+1, 楼层.indexOf("-")-2);
				if(建筑类型.contains("楼")||建筑类型.contains("板塔")){
					boolean b = 建筑类型.contains("楼")||建筑类型.contains("板塔");
					if(!b){
						建筑类型 = "";
						throw new Exception("未知建筑类型:"+建筑类型);
					}
				}
			}
			System.out.println("建筑类型："+建筑类型);
			System.out.println("建房时间"+建房时间);
		}else if(楼层.contains("层")){//5层2006年建板楼 - 北七家
//			System.out.println(楼层.substring(0,楼层.indexOf("层")));
//			System.out.println(楼层.substring(楼层.indexOf("层")+1,楼层.indexOf("年")));
//			System.out.println(楼层.substring(楼层.indexOf("年")+1,楼层.indexOf("-")-1));
//			System.out.println(楼层.substring(楼层.indexOf("-")+2));
			String 总楼层Str = 楼层.substring(0,楼层.indexOf("层"));
			System.out.println("总楼层:"+总楼层Str);
			总楼层 = Integer.parseInt(总楼层Str);
			
			if(楼层.contains("年")){
				String 建房年份S = 楼层.substring(楼层.indexOf("层")+1,楼层.indexOf("年"));
				Calendar c = Calendar.getInstance();
				c.set(Calendar.YEAR, Integer.parseInt(建房年份S));
				c.set(Calendar.MONTH,0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				建房时间 = c.getTime();
				System.out.println("建房时间:" + sdf.format(建房时间));
				建筑类型 = 楼层.substring(楼层.indexOf("年")+1,楼层.indexOf("-")-1);
			}else{
				建筑类型 = 楼层.substring(楼层.indexOf(")")+1, 楼层.indexOf("-")-2);
				if(!建筑类型.contains("楼")){
					throw new Exception("未知建筑类型:"+建筑类型);
				}
			}
			System.out.println("建房时间"+建房时间);
		}else{//2002年建板塔结合 - 木樨园
			if(楼层.contains("年")){
				String 建房年份S = 楼层.substring(楼层.indexOf("年")-4,楼层.indexOf("年"));
				Calendar c = Calendar.getInstance();
				c.set(Calendar.YEAR, Integer.parseInt(建房年份S));
				c.set(Calendar.MONTH,0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				建房时间 = c.getTime();
				System.out.println("建房时间:" + sdf.format(建房时间));
				建筑类型 = 楼层.substring(楼层.indexOf("年")+1,楼层.indexOf("-")-1);
			}
			System.out.println("建房时间"+建房时间);
		}
		所在地点 = 楼层.substring(楼层.indexOf("blank")+7);
		System.out.println("所在地点:"+所在地点);
		String 关注人数AS = ss.substring(ss.indexOf("followInfo")+42,ss.indexOf("发布"));
		String[] 关注人数A = 关注人数AS.split("/");
		String 关注人数S = 关注人数A[0].substring(0, 关注人数A[0].indexOf("人"));
		int 关注人数 = Integer.parseInt(关注人数S);
		System.out.println("关注人数："+关注人数);
		String 带看次数S = 关注人数A[1].substring(2, 关注人数A[1].indexOf("次"));
		int 看房人数 = Integer.parseInt(带看次数S);
		System.out.println("带看次数："+看房人数);
		String 发布时间S = 关注人数A[2];
		Date 发布时间 = null;
		if(发布时间S.contains("月")){
			String 月份 = 发布时间S.substring(0, 发布时间S.indexOf("个"));
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			int m1 = Integer.parseInt(月份.trim());
			c1.add(Calendar.MONTH, (-1*m1));
			发布时间 = c1.getTime();
			System.out.println("发布时间："+发布时间);
		}else if(发布时间S.contains("天")){
			String 天数 = 发布时间S.substring(0, 发布时间S.indexOf("天"));
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			int m1 = Integer.parseInt(天数.trim());
			c1.add(Calendar.DAY_OF_MONTH, (-1*m1));
			发布时间 = c1.getTime();
			System.out.println("发布时间："+sdf.format(c1.getTime()));
		}else if(发布时间S.contains("一年")){
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			c1.add(Calendar.YEAR, (-1*1));
			发布时间 = c1.getTime();
			System.out.println("发布时间："+sdf.format(c1.getTime()));
		}else if(发布时间S.contains("刚刚")){
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			发布时间 = c1.getTime();
			System.out.println("发布时间："+sdf.format(c1.getTime()));
		}else {
			System.out.println("未知发布时间："+发布时间S);
			throw new Exception();
		}
		String zjg = ss.substring(ss.indexOf("totalPrice")+18,ss.indexOf("unitPrice")-26);
		double 总价 = Double.parseDouble(zjg)*10000;
		System.out.println("总价格："+总价);
		
		String mpmjg = ss.substring(ss.lastIndexOf(">单价")+3,ss.indexOf("/平米")-1);
		int 每平米价格 = Integer.parseInt(mpmjg);
		System.out.println("每平米价格："+每平米价格 );
		
		//备注
		String 备注 = ss.substring(ss.indexOf("class=\"tag\""),ss.indexOf("priceInfo"));
		String[] 备注A = 备注.split("<span");
		备注 = "";
		for (String l : 备注A) {
			if(l.contains(">")&&l.contains("<")){
				String 备注T = l.substring(l.indexOf(">")+1,l.indexOf("<"));
				备注 += 备注T+";" ;
			}
		}
		System.out.println("备注："+备注);
		房屋基本信息 f = getBaseInfo();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		f.setId(sdf.format(new Date()) + 房屋唯一标识);
		f.网址 = 网址;
		f.标题 = 标题;
		f.房屋类型 = "二手房";
		f.房屋信息来源 = 房屋信息来源;
		f.房屋唯一标识 = 房屋唯一标识;
		f.所在小区 = 所在小区;
		f.所在地点 = 所在地点;
		f.户型 = 户型;
		f.建筑面积 = 建筑面积;
		f.建筑类型 = 建筑类型;
		f.发布时间 = 发布时间;
		f.建房时间 = 建房时间;
		f.朝向 = 朝向;
		f.装修 = 装修;
		f.电梯 = 电梯;
		f.所在楼层 = 所在楼层;
		f.总楼层 = 总楼层;
		f.备注 = 备注;
		房屋交易信息 j = new 房屋交易信息();
		j.setId(sdf.format(new Date())+房屋唯一标识);
		j.set房屋基本信息id(f.getId());
		j.set总价(总价);
		j.set关注人数(关注人数);
		j.set每平米价格(每平米价格);
		j.set看房人数(看房人数);
		logger.info(f.toString());
		logger.info(j.toString());
		房屋基本信息 f2 = 房屋信息数据库.查找基本信息根据来源和唯一标识(房屋信息来源, 房屋唯一标识);
		房屋交易信息 j2 = 房屋信息数据库.查找最新交易信息根据来源和唯一标识(房屋信息来源, 房屋唯一标识);
		if(f2==null){
			System.out.println("未发现房屋");
			房屋信息数据库.添加房屋基本信息(f);
			房屋信息数据库.添加房屋交易信息(j);
		}else{
			j.set房屋基本信息id(f2.getId());
			System.out.println("发现已存在房屋:"+f2.toString());
			if(j2.get总价()!=j.总价){
				System.out.println("######################发现价格变动信息##############################################################################################");
				房屋信息数据库.添加房屋交易信息(j);
			}
		}
		return f;
	}
}
