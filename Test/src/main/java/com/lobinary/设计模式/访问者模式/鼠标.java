package com.lobinary.设计模式.访问者模式;

public class 鼠标  implements 电脑接口 {

	   @Override
	   public void 访问(电脑使用者接口 computerPartVisitor) {
	      computerPartVisitor.使用鼠标(this);
	   }

}