package com.boce.test.内存分析;

/**
 * OOMHeapTest class
 * @author rosen jiang
 */


public class 内存溢出测试{
    public static void main(String[] args){
        oom();
    }
    
    private static void oom(){
    	 Object[] array = new Object[10000000];
         for(int i=0; i<10000000; i++){
             String d = String.valueOf(i).intern();
             array[i]=d;
         }
    }
}