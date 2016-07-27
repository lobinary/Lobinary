package com.lobinary.书籍.thinking_in_java.初始化与清除;

/**
 * 
  *
  *	直接调用静态方法不会调用构造函数，不会初始化普通对象
  * 仅仅初始化静态参数之后，直接执行静态方法	（注意：不会执行静态代码块）
  *
  * 执行看一眼 直接就明白了，胜过一切解释
 */
public class 静态对象初始化2 {
	
	public static void main(String[] args) {
		静态对象.静态方法();
		
		System.out.println("=================================");
		
		new 静态对象();
	}

}

class 静态对象{
	对象 普通变量 = new 对象("普通对象");
	static 对象 静态对象 = new 对象("静态对象");
	
	静态对象(){
		System.out.println("静态对象构造函数");
	}
	{
		System.out.println("静态代码块");
		静态对象 = null;
	}
	
	static void 静态方法(){
		System.out.println("静态方法");
	}
	
	void 普通方法(){
		System.out.println("普通方法");
	}
}

class 对象{
	
	对象(String s){
		System.out.println("对象("+s+")");
	}
}