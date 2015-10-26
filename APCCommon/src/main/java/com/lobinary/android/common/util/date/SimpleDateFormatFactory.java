/*
 * @(#)SimpleDateFormatFactory.java     V1.0.0      @2014-6-18
 *
 * Project:unpcommon
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    chenyong       2014-6-18     Create this file
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
package com.lobinary.android.common.util.date;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Description: SimpleDateFormat享元模式
 * 
 * @author: chenyong
 */
public class SimpleDateFormatFactory {
	
	private static final Map<String, SimpleDateFormat>	sdfMap	= new HashMap<String, SimpleDateFormat>();
	
	public static SimpleDateFormat getInstance() {
		return getInstance(DateUtil.DATE_FORMAT);
	}
	
	public static synchronized SimpleDateFormat getInstance(String format) {
		SimpleDateFormat sdf = sdfMap.get(format);
		if (null == sdf) {
			sdf = new SimpleDateFormat(format);
			sdfMap.put(format, sdf);
		}
		return sdf;
	}
}