/*
 * @(#)高并发测试.java     V1.0.0      @下午5:14:27
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
package com.lobinary.test.normal;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年7月28日 下午5:14:27
 * @version V1.0.0 描述 : 创建文件高并发测试
 * 
 *         
 * 
 */
public class 高并发测试 {
	
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10000; i++) {
			final int j = i;
			new Thread(){
				@Override
				public void run() {
					super.run();
					高并发测试对象 a = new 高并发测试对象();
//					a.out("" + j);
				}
				
			}.start();
		}
		Thread.sleep(10000);
		System.out.println("高并发测试对象总数" + 高并发测试对象.list.size());
	}

}
