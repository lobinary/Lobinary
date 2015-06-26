/*
 * @(#)CatchDataFromUrl.java     V1.0.0      @上午9:40:55
 *
 * Project: Test
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    lvbin       	2015年6月23日    	Create this file
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
package com.boce.cav;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import com.unicompayment.fap.common.utils.http.HttpUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年6月23日 上午9:40:55
 * @version V1.0.0 描述 : 创建文件CatchDataFromUrl
 * 
 *         
 * 
 */
public class CavService{
	
	public static void service() throws SocketTimeoutException{
		HashMap<String, String> paramMap = new HashMap<String,String>();
		String returnStr = HttpUtil.sendPostRequest("http://www.baidu.com", paramMap, "UTF-8", "UTF-8");
		System.out.println(returnStr);
	}

}
