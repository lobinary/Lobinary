package com.lobinary.书籍.thinking_in_java.初始化与清除;

import java.util.Random;

public class 数组 {
	  public static void main(String[] args) {
	    int[] a1 = { 1, 2, 3, 4, 5 };
	    int[] a2;
	    a2 = a1;
	    for(int i = 0; i < a1.length; i++)
		      prt("a1[" + i + "] = " + a1[i]);
	    System.out.println("====================================");
	    for(int i = 0; i < a2.length; i++)
	      a2[i]++;
	    for(int i = 0; i < a1.length; i++)
	      prt("a1[" + i + "] = " + a1[i]);
	    

	    System.out.println("====================================");
	    s[] sa1 = {new s("6"),new s("8")};
	    s[] sa = sa1;
	    for (s s : sa1) {
			System.out.println(s);
		}
	    for (int i = 0; i < sa.length; i++) {
			sa[i] = new s(""+i);
		}
	    System.out.println("====================================");
	    for (s s : sa1) {
	    	System.out.println(s);
		}

	    System.out.println("====================================");
	    int[][] a = {
	    		{ 1, 2, 3, },
	    		{ 4, 5, 6, },
	    		};
	    System.out.println(a);
	    


	    System.out.println("======多维随机素组==============================");
	    
	    int[][][] a3 = new int[pRand(7)][][];
	    for(int i = 0; i < a3.length; i++) {
	      a3[i] = new int[pRand(5)][];
	      for(int j = 0; j < a3[i].length; j++)
	        a3[i][j] = new int[pRand(5)];
	    }
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  static void prt(String s) {
	    System.out.println(s);
	  } 
	  static Random rand = new Random();
	  static int pRand(int mod) {
		    return Math.abs(rand.nextInt()) % mod + 1;
	  }
}

class s{
	String s;

	public s(String s) {
		super();
		this.s = s;
	}

	@Override
	public String toString() {
		return "s [s=" + s + "]";
	}
}