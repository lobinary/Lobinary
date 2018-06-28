package com.lobinary.书籍.thinking_in_java.类再生;


class Art {
	  Art() {
	    System.out.println("Art constructor");
	  }
	}

	class Drawing extends Art {
	  Drawing() {
	    System.out.println("Drawing constructor");
	  }
	}

	public class 继承类初始化构造顺序 extends Drawing {
	  继承类初始化构造顺序() {
	    System.out.println("Cartoon constructor");
	  }
	  public static void main(String[] args) {
	    继承类初始化构造顺序 x = new 继承类初始化构造顺序();
	  }
	}