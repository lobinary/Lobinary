package com.lobinary.设计模式.前端控制器模式;

public class Dispatcher {

	添加产品JSP 添加产品JSP = new 添加产品JSP();
	删除产品JSP 删除产品JSP = new 删除产品JSP();
	
	public void dispatcher(String requetURL){
		if(requetURL.contains("addProduct")){
			添加产品JSP.反馈给用户();
		}else{
			删除产品JSP.反馈给用户();
		}
	}

}
