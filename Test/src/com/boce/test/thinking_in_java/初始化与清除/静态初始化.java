package com.boce.test.thinking_in_java.初始化与清除;

/**
 * 
  *
  * 整体执行顺序是这样的
  * 一  	当新建一个含有静态变量、普通变量、构造方法的对象时
  *	1.初始化静态变量(该静态变量未曾被创建过，【静态变量只创建一次，以后再次创建该对象时，不会在执行初始化】)
  *	2.初始化普通变量
  *	3.执行构造方法
  *
  *	二	直接调用静态方法不会调用构造函数，不会初始化普通对象
  * 仅仅初始化静态参数之后，直接执行静态方法
  * 详见下方代码
  * @see 静态对象初始化2.java
  *	
 */
class Bowl {
	  Bowl(String marker) {
	    System.out.println(marker+"---Bowl构造方法");
	  }
	  void f(String desc) {
	    System.out.println(desc+"---Bowl普通方法");
	  }
	}

	class Table {
	  static Bowl b1 = new Bowl("Table初始化Static 变量b1");
	  Table(String desc) {
	    System.out.println(desc+"---Table构造方法");
	    b2.f("Table Static对象b2调用Bowl方法f");
	  }
	  void f2(String marker) {
	    System.out.println(marker+"---Table普通方法f2");
	  }
	  static Bowl b2 = new Bowl("Table初始化Static 对象b2");
	}

	class Cupboard {
	  Bowl b3 = new Bowl("Cupboard 初始化普通变量b3");
	  static Bowl b4 = new Bowl("Cupboard 初始化 Static 变量b4");
	  Cupboard(String desc) {
	    System.out.println(desc+"---Cupboard构造方法");
	    b4.f("Cupboard Static对象b2调用Bowl方法f");
	  }
	  void f3(String marker) {
	    System.out.println(marker+"---Cupboard普通方法");
	  }
	  static Bowl b5 = new Bowl("Cupboard初始化 Static 变量b5");
	}

	public class 静态初始化 {
	  public static void main(String[] args) {
	    new Cupboard("主方法新建Cupboard");
	    new Cupboard("主方法新建Cupboard");
	    t2.f2("主方法 通过t2 调用 方法f2");
	    t3.f3("主方法 通过t2 调用 方法f3");
	  }
	  static Table t2 = new Table("主方法 初始化Static变量 Table t2");
	  static Cupboard t3 = new Cupboard("主方法 初始化Static变量 Cupboard t3");
	} 
