package com.lobinary.test.pp.wechatapp.pay;

import java.util.HashMap;
import java.util.Map;

import com.lobinary.工具类.http.HU;

public class 微信APP支付下订单接口调试 {

	/**
	 * 字段名 			变量名 			必填 		类型 				示例值 									描述 
		应用ID 		appid 			 是		String(32) 		wxd678efh567hg6787 						微信开放平台审核通过的应用APPID 
		商户号 		mch_id 			 是 		String(32) 		1230000109 								微信支付分配的商户号 
		设备号 		device_info 	否 		String(32) 		013467007045764 						终端设备号(门店号或收银设备ID)，默认请传"WEB" 
		随机字符串 		nonce_str 		是 		String(32) 		5K8264ILTKCH16CQ2502SI8ZNMTM67VS 		随机字符串，不长于32位。推荐随机数生成算法 
		签名 			sign 			是 		String(32) 		C380BEC2BFD727A4B6845133519F3AD6 		签名，详见签名生成算法 
		签名类型 		sign_type 		否 		String(32) 		HMAC-SHA256 							签名类型，目前支持HMAC-SHA256和MD5，默认为MD5 
		商品描述 		body 			是		String(128) 	腾讯充值中心-QQ会员充值 						商品描述交易字段格式根据不同的应用场景按照以下格式：
																									APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
		 
		商品详情 detail 否 String(8192) {
		"goods_detail":[
		{
		"goods_id":"iphone6s_16G",
		"wxpay_goods_id":"1001",
		"goods_name":"iPhone6s 16G",
		"quantity":1,
		"price":528800,
		"goods_category":"123456",
		"body":"苹果手机"
		},
		{
		"goods_id":"iphone6s_32G",
		"wxpay_goods_id":"1002",
		"goods_name":"iPhone6s 32G",
		"quantity":1,
		"price":608800,
		"goods_category":"123789",
		"body":"苹果手机"
		}
		]
		} 商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
		
		goods_detail 服务商必填 []：
		└ goods_id String 必填 32 商品的编号
		└ wxpay_goods_id String 可选 32 微信支付定义的统一商品编号
		└ goods_name String 必填 256 商品名称
		└ quantity Int 必填 商品数量
		└ price Int 必填 商品单价，单位为分
		└ goods_category String 可选 32 商品类目ID
		└ body String 可选 1000 商品描述信息
		 
		附加数据 attach 否 String(127) 深圳分店 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据 
		商户订单号 out_trade_no 是 String(32) 20150806125346 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号 
		货币类型 fee_type 否 String(16) CNY 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型 
		总金额 total_fee 是 Int 888 订单总金额，单位为分，详见支付金额 
		终端IP spbill_create_ip 是 String(16) 123.12.12.123 用户端实际ip 
		交易起始时间 time_start 否 String(14) 20091225091010 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 
		交易结束时间 time_expire 否 String(14) 20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
		
		注意：最短失效时间间隔必须大于5分钟 
		商品标记 goods_tag 否 String(32) WXG 商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠 
		通知地址 notify_url 是 String(256) http://www.weixin.qq.com/wxpay/pay.php 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 
		交易类型 trade_type 是 String(16) APP 支付类型 
		指定支付方式 limit_pay 否 String(32) no_credit no_credit--指定不能使用信用卡支付 

	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("准备开始");
		
		String requestUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Map<String, String> params = new HashMap<String, String>();
		
		String resp = HU.sendPostRequest(requestUrl, params);
		System.out.println(resp);
	}

}
