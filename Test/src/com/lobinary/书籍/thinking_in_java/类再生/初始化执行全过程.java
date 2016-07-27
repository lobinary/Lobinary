package com.lobinary.书籍.thinking_in_java.类再生;



/**
 * 
  *
  *	初始化顺序：
  *	当static方法被调用的时候，会先初始化static方法所在类的静态变量，但是当该static所在类有父类时，会先初始化父类static变量，再初始化子类static变量
  * 当初始化普通类时，会先执行static 代码块，同上，先父类再子类，然后是父类变量，子类变量，父类构造，子类构造
 */

class Insect {
	  int i = 9;
	  int j;
	  Insect() {
	    prt("i = " + i + ", j = " + j);
	    j = 39;
	  }
	  static int x1 = 
	    prt("static Insect.x1 initialized");
	  static int prt(String s) {
	    System.out.println(s);
	    return 47;
	  }
	}

	public class 初始化执行全过程 extends Insect {
	  int k = prt("Beetle.k initialized");
	  初始化执行全过程() {
	    prt("k = " + k);
	    prt("j = " + j);
	  }
	  static int x2 =
	    prt("static Beetle.x2 initialized");
	  static int prt(String s) {
	    System.out.println(s);
	    return 63;
	  }
	  public static void main(String[] args) {
	    prt("Beetle constructor");
	    初始化执行全过程 b = new 初始化执行全过程();
	  }
	}