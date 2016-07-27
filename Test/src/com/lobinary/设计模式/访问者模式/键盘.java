package com.lobinary.设计模式.访问者模式;

public class 键盘  implements 电脑接口 {

	   @Override
	   public void 访问(电脑使用者接口 电脑使用者) {
	      电脑使用者.使用键盘(this);
	   }

}
