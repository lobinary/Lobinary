package com.boce.test.源码分析.spring.core;

public class Person {
    
    private String name;
    private int age;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void info(){
        System.out.println("name:"+getName()+" age:"+getAge());
    }
}