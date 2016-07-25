package com.boce.test.设计模式.解释器模式.表达式.实现类;

import com.boce.test.设计模式.解释器模式.环境变量;
import com.boce.test.设计模式.解释器模式.表达式.表达式抽象类;

public class Not extends 表达式抽象类 {

    private 表达式抽象类 exp;
    
    public Not(表达式抽象类 exp){
        this.exp = exp;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Not)
        {
            return exp.equals(
                    ((Not)obj).exp);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean interpret(环境变量 ctx) {
        return !exp.interpret(ctx);
    }

    @Override
    public String toString() {
        return "(Not " + exp.toString() + ")";
    }

}
