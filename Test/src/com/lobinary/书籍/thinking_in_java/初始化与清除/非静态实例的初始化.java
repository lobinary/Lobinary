package com.lobinary.书籍.thinking_in_java.初始化与清除;

class Mug {
	  Mug(int marker) {
	    System.out.println("Mug(" + marker + ")");
	  }
	  void f(int marker) {
	    System.out.println("f(" + marker + ")");
	  }
	}

	public class 非静态实例的初始化 {
	  Mug c1;
	  Mug c2;
	  {
	    c1 = new Mug(1);
	    c2 = new Mug(2);
	    System.out.println("c1 & c2 initialized");
	  }
	  非静态实例的初始化() {
	    System.out.println("Mugs()");
	  }
	  public static void main(String[] args) {
	    System.out.println("Inside main()");
	    非静态实例的初始化 x = new 非静态实例的初始化();
	  }
	} ///:~
