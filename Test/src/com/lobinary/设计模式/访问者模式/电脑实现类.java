package com.lobinary.设计模式.访问者模式;


public class 电脑实现类 implements 电脑接口 {
	
	   电脑接口[] 组件;

	   public 电脑实现类(){
	      组件 = new 电脑接口[] {new 鼠标(), new 键盘()};		
	   } 


	   @Override
	   public void 访问(电脑使用者接口 电脑使用者) {
	      for (int i = 0; i < 组件.length; i++) {
	         组件[i].访问(电脑使用者);
	      }
	      电脑使用者.使用电脑(this);
	   }
	   
}