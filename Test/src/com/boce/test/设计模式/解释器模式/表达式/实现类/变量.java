package com.boce.test.设计模式.解释器模式.表达式.实现类;

import com.boce.test.设计模式.解释器模式.环境变量;
import com.boce.test.设计模式.解释器模式.表达式.表达式抽象类;

public class 变量 extends 表达式抽象类{

    private String name;

    public 变量(String name){
        this.name = name;
    }
    
    @Override
    public boolean interpret(环境变量 ctx) {
        return ctx.lookup(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if(obj != null && obj instanceof 变量)
        {
            return this.name.equals(
                    ((变量)obj).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
