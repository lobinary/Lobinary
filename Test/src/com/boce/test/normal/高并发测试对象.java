/*
 * @(#)高并发测试对象.java     V1.0.0      @下午5:15:07
 *
 * Project: Test
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    lvbin       	2015年7月28日    	Create this file
 * 
 * Copyright Notice:
 *     Copyright (c) 2009-2014 Unicompay Co., Ltd. 
 *     1002 Room, No. 133 North Street, Xi Dan, 
 *     Xicheng District, Beijing ,100032, China 
 *     All rights reserved.
 *
 *     This software is the confidential and proprietary information of
 *     Unicompay Co., Ltd. ("Confidential Information").
 *     You shall not disclose such Confidential Information and shall use 
 *     it only in accordance with the terms of the license agreement you 
 *     entered into with Unicompay.
 */
package com.boce.test.normal;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年7月28日 下午5:15:07
 * @version V1.0.0 描述 : 创建文件高并发测试对象
 * 
 *         
 * 
 */
public class 高并发测试对象 {
	
	public static Map<String,String> list = new HashMap<String,String>();
	
	private 高并发附属对象 s;
	
	public void out(){
		String a = s.getA();
//		System.out.println(s);
		if(list.containsKey(s)){
			System.out.println("发现被串改数据：" + s);
		}
		String b = s.getA();
	}

}
