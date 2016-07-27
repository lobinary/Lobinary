package com.lobinary.设计模式.解释器模式.表达式.实现类;

import com.lobinary.设计模式.解释器模式.环境变量;
import com.lobinary.设计模式.解释器模式.表达式.表达式抽象类;

public class 常量 extends 表达式抽象类{
    
    private boolean value;

    public 常量(boolean value){
        this.value = value;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if(obj != null && obj instanceof 常量){
            return this.value == ((常量)obj).value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean interpret(环境变量 ctx) {
        
        return value;
    }

    @Override
    public String toString() {
        return new Boolean(value).toString();
    }
    
}