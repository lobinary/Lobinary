package com.lobinary.设计模式.解释器模式.表达式.实现类;

import com.lobinary.设计模式.解释器模式.环境变量;
import com.lobinary.设计模式.解释器模式.表达式.表达式抽象类;

public class Or extends 表达式抽象类 {
    private 表达式抽象类 left,right;

    public Or(表达式抽象类 left , 表达式抽象类 right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Or)
        {
            return this.left.equals(((Or)obj).left) && this.right.equals(((Or)obj).right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean interpret(环境变量 ctx) {
        return left.interpret(ctx) || right.interpret(ctx);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " OR " + right.toString() + ")";
    }

}