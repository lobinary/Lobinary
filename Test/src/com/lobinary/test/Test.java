package com.lobinary.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	
	private String a;
	private String b;

	public static void main(String[] args) throws Exception {
		
//		String r = "^(?!.*(\\.js|\\.css|receive|access3|\\.png|\\.ico|\\.jpg|\\.img)).*$";
//		System.out.println("http://localhost:8080/wechat-app/access3".matches(r));
		
		/*
		 * 测试Calendar的设置功能set(Calendar.YEAR, 2015);
		 * 结论是Calendar初始化是以当前时间初始化的，当我设置了年份之后，他就会给年份改了
		 */
		Calendar startInstance = Calendar.getInstance();
		startInstance.set(Calendar.YEAR, 2015);
		startInstance.set(Calendar.DAY_OF_YEAR, 364);
		System.out.println(startInstance.getTime().toLocaleString());
		
		/**
		 * 测试汉字大写
		 */
		String s = "a我爱你bCDeF";
		System.out.println(s.toUpperCase());
		
		
		/**
		 *测试时间格式
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-ddHHmmss");
		String format = sdf.format(new Date());
		System.out.println(format);
		
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
		String ss = "http://bj.lianjia.com/ershoufang/101100879074.html\" target=\"_blank\" data-log_index=\"1\"   data-el=\"ershoufang\"  data-sl=\"\">东亚五环国际 不限购开间 可注册公司 低总价</a><span class=\"yezhushuo tagBlock\">房主自荐</span></div><div class=\"address\"><div class=\"houseInfo\"><span class=\"houseIcon\"></span><a href=\"http://bj.lianjia.com/xiaoqu/1111047687491/\" target=\"_blank\" data-log_index=\"1\" data-el=\"region\">东亚五环国际 </a> | 1室0厅 | 34.8平米 | 西 | 精装 | 有电梯</div></div><div class=\"flood\"><div class=\"positionInfo\"><span class=\"positionIcon\"></span>中楼层(共20层)2014年建塔楼  -  <a href=\"http://bj.lianjia.com/ershoufang/jiugong1/\" target=\"_blank\">旧宫</a></div></div><div class=\"followInfo\"><span class=\"starIcon\"></span>111人关注 / 共52次带看 / 2个月以前发布</div><div class=\"tag\"><span class=\"five\">房本满两年</span><span class=\"haskey\">随时看房</span><span class=\"is_restriction\">不限购</span></div><div class=\"priceInfo\"><div class=\"totalPrice\"><span>125</span>万</div><div class=\"unitPrice\" data-hid=\"101100879074\" data-rid=\"1111047687491\" data-price=\"35920\"><span>单价35920元/平米</span></div></div></div><div class=\"listButtonContainer\"><div class=\"btn-follow followBtn\" data-hid=\"101100879074\"><span class=\"follow-text\">关注</span></div><div class=\"compareBtn LOGCLICK\" data-hid=\"101100879074\" log-mod=\"101100879074\" data-log_evtid=\"10230\">加入对比</div></div></li><li class=\"clear\"><a class=\"img \" href=\"http://bj.lianjia.com/ershoufang/101100591721.html\" target=\"_blank\"data-log_index=\"2\" data-el=\"ershoufang\" data-sl=\"\"><img class=\"lj-lazy\" src=\"http://s1.ljcdn.com/feroot/pc/asset/img/blank.gif?_v=20170119161557\" data-original=\"http://image1.ljcdn.com/110000-inspection/80ab76cc-1204-4ae1-87bc-116ee402030d.jpg.232x174.jpg\" alt=\"地铁6号线 民水民电LOFT 有燃气\"></a>";
		System.out.println(ss);
		System.out.println("网址:"+ss.substring(0, ss.indexOf("\"")));
		System.out.println("唯一标识:"+ss.substring(ss.indexOf("ershoufang")+11, ss.indexOf("html")-1));
		System.out.println("标题:"+ss.substring(ss.indexOf("data-sl=\"\">")+11, ss.indexOf("</a>")));
		String xx = ss.substring(ss.indexOf("region")+8,ss.indexOf("flood"));
		String[] sp = xx.split("\\|");

		System.out.println("小区:"+sp[0].substring(0,sp[0].length()-5).trim());
		System.out.println("户型:"+sp[1].trim());
		System.out.println("总面积:"+sp[2].trim());
		System.out.println("朝向:"+sp[3].trim());
		System.out.println("装修:"+sp[4].trim());
		if(sp.length>5){
			if(sp[5].contains("有电梯")){
				System.out.println("电梯:"+"有电梯");
			}else{
				System.out.println("未知属性："+sp[5]);
				throw new Exception();
			}
			
		}
		String 楼层 = ss.substring(ss.indexOf("positionIcon")+21, ss.indexOf("followInfo")-28);
		System.out.println(楼层);
		String 建筑类型 = "";
		if(楼层.contains("年")){
			建筑类型 = 楼层.substring(楼层.indexOf("年建")+2, 楼层.indexOf("-")-2);
		}else{
			建筑类型 = 楼层.substring(楼层.indexOf(")")+1, 楼层.indexOf("-")-2);
			if(!建筑类型.contains("楼")){
				throw new Exception("未知建筑类型:"+建筑类型);
			}
		}
		System.out.println("建筑类型:"+建筑类型);
		String 总楼层 = 楼层.substring(楼层.indexOf("(")+2,楼层.indexOf(")")-1);
		System.out.println("总楼层:"+总楼层);
		int 总楼层I = Integer.parseInt(总楼层);
		int 所在楼层I = 0;
		if(楼层.contains("低楼层")){
			 所在楼层I = 总楼层I/3/2;
		}else if(楼层.contains("中楼层")){
			 所在楼层I = 总楼层I/2;
		}else if(楼层.contains("高楼层")){
			 所在楼层I = 总楼层I-(总楼层I/3/2);
		}else {
			System.out.println("未知楼层："+楼层);
			throw new Exception();
		}
		System.out.println("所在楼层："+ 所在楼层I);
		System.out.println("所在地点:"+楼层.substring(楼层.indexOf("blank")+7));
		String 建房年份 = 楼层.substring(楼层.indexOf(")")+1,楼层.indexOf("年"));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(建房年份));
		c.set(Calendar.MONTH,0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		System.out.println("建房年份:"+sdf.format(c.getTime()));
		String 关注人数 = ss.substring(ss.indexOf("followInfo")+42,ss.indexOf("发布"));
		String[] 关注人数A = 关注人数.split("/");
		System.out.println("关注人数："+关注人数A[0].substring(0, 关注人数A[0].indexOf("人")));
		System.out.println("带看次数："+关注人数A[1].substring(2, 关注人数A[1].indexOf("次")));
		String 发布时间 = 关注人数A[2];
		if(发布时间.contains("月")){
			String 月份 = 发布时间.substring(0, 发布时间.indexOf("个"));
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			int m1 = Integer.parseInt(月份.trim());
			c1.add(Calendar.MONTH, (-1*m1));
			System.out.println("发布时间："+sdf.format(c1.getTime()));
		}else if(发布时间.contains("天")){
			String 天数 = 发布时间.substring(0, 发布时间.indexOf("个"));
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			int m1 = Integer.parseInt(天数.trim());
			c1.add(Calendar.DAY_OF_MONTH, (-1*m1));
			System.out.println("发布时间："+sdf.format(c1.getTime()));
		}else {
			System.out.println("未知发布时间："+发布时间);
			throw new Exception();
		}
		String zjg = ss.substring(ss.indexOf("totalPrice")+18,ss.indexOf("unitPrice")-26);
		int 总价格 = Integer.parseInt(zjg);
		System.out.println("总价格："+总价格);
		
		String mpmjg = ss.substring(ss.indexOf("单价")+2,ss.indexOf("/平米")-1);
		int 每平米价格 = Integer.parseInt(mpmjg);
		System.out.println("每平米价格："+每平米价格 );
		
		String 备注 = ss.substring(ss.indexOf("class=\"tag\""),ss.indexOf("priceInfo"));
		String[] 备注A = 备注.split("<span");
		System.out.println("备注："+备注);
		备注 = "";
		for (String l : 备注A) {
			if(l.contains(">")&&l.contains("<")){
				String 备注T = l.substring(l.indexOf(">")+1,l.indexOf("<"));
				备注 += 备注T+";" ;
			}
		}
		System.out.println("备注："+备注);
		
		
		String s1 = "5层2006年建板楼 - 北七家";
		System.out.println(s1.substring(0,s1.indexOf("层")));
		System.out.println(s1.substring(s1.indexOf("层")+1,s1.indexOf("年")));
		System.out.println(s1.substring(s1.indexOf("年")+1,s1.indexOf("-")-1));
		System.out.println(s1.substring(s1.indexOf("-")+2));
	}
	
	

	@Override
	public String toString() {
		return ga(a,b);
	}

	private static String ga(String c ,String d) {
		return c + d;
	}	
	
	

}
