package com.lobinary.test.pp.status;

import com.lobinary.工具类.http.HU;

public class 检测状态线程 extends Thread{
	
	private String name;
	private String url;
	
	public 检测状态线程(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}


	@Override
	public void run() {
		String r = HU.doGetNoExceptionTrace(url);
		if(r!=null&&r.contains("hello everyone")){
			System.out.println(name+"\t成功");
		}else{
			System.out.println(name+"\t\t失败");
		}
	}

}
