package com.boce.test.设计模式.解释器模式.表达式;

import com.boce.test.设计模式.解释器模式.环境变量;

public abstract class 表达式抽象类 {
    /**
     * 以环境为准，本方法解释给定的任何一个表达式
     */
    public abstract boolean interpret(环境变量 ctx);
    /**
     * 检验两个表达式在结构上是否相同
     */
    public abstract boolean equals(Object obj);
    /**
     * 返回表达式的hash code
     */
    public abstract int hashCode();
    /**
     * 将表达式转换成字符串
     */
    public abstract String toString();
}
