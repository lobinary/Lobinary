package com.boce.test.jvm.面试题;

public class Jvm变量加载顺序 {
	
    public static void main(String[] args) throws Exception{
    	
    	/**
    	 * 解释：
    		
    		从main函数开始，根据JVM对类的加载机制，Singleton.getInstance()主动使用，会触发类的加载，首先会为类的静态变量赋予初始值（程序从上到下执行），
    		即：Singleton singleton = null，counter1 = 0，counter2 = 0.
    		
    		然后会进行类的初始化，即singleton = new Singleton()，会触发构造函数，执行：
    		    counter1 ++;
    		    counter2 ++;
    		后
    		    counter1 = 1，
    		    counter2 = 1
    		
    		最后，类在初始化后，为类的静态变量赋予正确的初始值，为用户赋予的正确值（从上到下）
    		即：
    		
    		    public static int counter1;//无用户赋值
    		    public static long counter2 = 0;//用户赋予初始值0
    		最终结果：
    		    counter1 :1
    		    counter2 :0
    	 */
    	@SuppressWarnings("unused")
		Singleton singleton = Singleton.getInstance();
        System.out.println("counter1 :" + Singleton.counter1);
        System.out.println("counter2 :" + Singleton.counter2);
        
        
        /**
         * 解释：
			
			从main函数开始，根据JVM对类的加载机制，Singleton.getInstance()主动使用，会触发类的加载，首先会为类的静态变量赋予初始值（程序从上到下执行），
			即：Singleton singleton = null，counter1 = 0，counter2 = 0.
			
			然后会进行类的初始化，即
			    public static int counter1;//无用户赋值
			    public static long counter2 = 0;//用户赋予初始值0
			后
			    counter1 = 0，
			    counter2 = 0    
			
			singleton = new
			Singleton()，会触发构造函数，执行：
			counter1 ++;
			counter2 ++;
			
			最终结果：
			    counter1 :1
			    counter2 :1
         */
        Singleton2 singleton2 = Singleton2.getInstance();
        System.out.println("counter1 :" + Singleton2.counter1);
        System.out.println("counter2 :" + Singleton2.counter2);
    }
}

class Singleton{

    private static Singleton singleton = new Singleton();
    public static int counter1;
    public static long counter2 = 0;

    private Singleton(){
        counter1 ++;
        counter2 ++;
    }

    public static Singleton getInstance(){
        return singleton;
    }
}

class Singleton2{

    public static int counter1;
    public static long counter2 = 0;
    private static Singleton2 singleton = new Singleton2();

    private Singleton2(){
        counter1 ++;
        counter2 ++;
    }

    public static Singleton2 getInstance(){
        return singleton;
    }
}
