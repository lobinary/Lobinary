package com.lobinary.书籍.thinking_in_java.初始化与清除;

class Chair {
	  static boolean gcrun = false;
	  static boolean f = false;
	  static int created = 0;
	  static int finalized = 0;
	  int i;
	  Chair() {
	    i = ++created;
	    System.out.println("对象"
	    		+ i+"被创建");
	  }
	  protected void finalize() {
	    if(!gcrun) {
	      gcrun = true;
	      System.out.println(
	        "开始垃圾回收，当前已创建对象数量为："+created);
	    }
	    System.out.println(
		        "对象" + i+
		        "被回收");
	    finalized++;
	    if(finalized >= created)
	      System.out.println(
	        "所有对象均被回收，数量为：" + finalized);
	  }
	}

	public class 垃圾回收机制 {
	  public static void main(String[] args1) {
		  String[] args = {"before"};
	    if(args.length == 0) {
	      System.err.println("Usage: \n" +
	        "java Garbage before\n  or:\n" +
	        "java Garbage after");
	      return;
	    }
	    while(!Chair.f) {
	      new Chair();
	      new String("To take up space");
	    }
	    System.out.println(
	      "After all Chairs have been created:\n" +
	      "total created = " + Chair.created +
	      ", total finalized = " + Chair.finalized);
	    if(args[0].equals("before")) {
	      System.out.println("gc():");
	      System.gc();
	      System.out.println("runFinalization():");
	      System.runFinalization();
	    }
	    System.out.println("bye!");
	    if(args[0].equals("after"))
	      System.runFinalizersOnExit(true);
	  }
	} ///:~