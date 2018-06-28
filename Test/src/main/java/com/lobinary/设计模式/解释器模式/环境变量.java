package com.lobinary.设计模式.解释器模式;

import java.util.HashMap;
import java.util.Map;

import com.lobinary.设计模式.解释器模式.表达式.实现类.变量;

public class 环境变量 {

    private Map<变量,Boolean> map = new HashMap<变量,Boolean>();
    
    public void 装配(变量 var , boolean value){
        map.put(var, new Boolean(value));
    }
    
    public boolean lookup(变量 var) throws IllegalArgumentException{
        Boolean value = map.get(var);
        if(value == null){
            throw new IllegalArgumentException();
        }
        return value.booleanValue();
    }
}
