package com.lobinary.工具类;

public class TT {
    

    static{
        System.out.println("statuc");
    }
    
    
    public static void run(){
        test();
    }
    
    public static void test(){
        System.out.println(">>>>>>>>>>>>>"+获取调用类名()+"."+获取调用方法名());
    }

    public static String 获取调用类名(){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        StackTraceElement invoker = stack[3];
        String className = invoker.getClassName();
        return className;
    }
    
    public static String 获取调用方法名(){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        StackTraceElement invoker = stack[3];
        for(StackTraceElement ste :stack){
            System.out.println(ste.getClassName()+"."+ste.getMethodName());
        }
        String methodName = invoker.getMethodName();
        return methodName;
    }
    
    public static String 获取调用文件名(){
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        StackTraceElement invoker = stack[3];
        String fileName = invoker.getFileName();
        return fileName;
    }

}
