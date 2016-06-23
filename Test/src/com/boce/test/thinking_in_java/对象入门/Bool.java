package com.boce.test.thinking_in_java.对象入门;

import java.util.Random;

	public class Bool {
		  public static void main(String[] args) {
		    Random rand = new Random();
		    int i = rand.nextInt() % 100;
		    int j = rand.nextInt() % 100;
		    prt("i = " + i);
		    prt("j = " + j);
		    prt(i +">"+ j+" is " + (i > j));
		    prt("i < j is " + (i < j));
		    prt("i >= j is " + (i >= j));
		    prt("i <= j is " + (i <= j));
		    prt("i == j is " + (i == j));
		    prt("i != j is " + (i != j));

		    // Treating an int as a boolean is 
		    // not legal Java
		//! prt("i && j is " + (i && j));
		//! prt("i || j is " + (i || j));
		//! prt("!i is " + !i);

		    prt("(i < 10) && (j < 10) is "
		       + ((i < 10) && (j < 10)) );
		    prt("(i < 10) || (j < 10) is "
		       + ((i < 10) || (j < 10)) );
		    
		    Random r = new Random();
		    System.out.println(r.nextInt()%100);//正负100
		    
		  }
		  static void prt(String s) {
		    System.out.println(s);
		  }
}
