package com.lobinary.设计模式.访问者模式;


public class 电脑使用实现类 implements 电脑使用者接口 {

	   @Override
	   public void 使用电脑(电脑实现类 computer) {
	      System.out.println("Displaying Computer.");
	   }

	   @Override
	   public void 使用鼠标(鼠标 mouse) {
	      System.out.println("Displaying Mouse.");
	   }

	   @Override
	   public void 使用键盘(键盘 keyboard) {
	      System.out.println("Displaying Keyboard.");
	   }

}