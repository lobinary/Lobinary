package com.lobinary.书籍.thinking_in_java.类再生;

class Soap {
	  private String s;
	  Soap() {
	    System.out.println("Soap()");
	    s = new String("Constructed");
	  }
	  public String toString() { return s; }
	}

	public class 初始化类内部对象方案 {
	  private String 
	    // Initializing at point of definition:
	    s1 = new String("Happy"), 
	    s2 = "Happy", 
	    s3, s4;
	  Soap castille;
	  int i;
	  float toy;
	  初始化类内部对象方案() {
	    System.out.println("Inside Bath()");
	    s3 = new String("Joy");
	    i = 47;
	    toy = 3.14f;
	    castille = new Soap();
	  }
	  void print() {
	    // Delayed initialization:
	    if(s4 == null)
	      s4 = new String("Joy");
	    System.out.println("s1 = " + s1);
	    System.out.println("s2 = " + s2);
	    System.out.println("s3 = " + s3);
	    System.out.println("s4 = " + s4);
	    System.out.println("i = " + i);
	    System.out.println("toy = " + toy);
	    System.out.println("castille = " + castille);
	  }
	  public static void main(String[] args) {
		初始化类内部对象方案 b = new 初始化类内部对象方案();
	    b.print();
	  }
	}