package com.lobinary.设计模式.备忘录模式.宽接口;

public class 备忘对象保存库宽接口 {

    private 普通对象 memento;
    /**
     * 备忘录取值方法
     */
    public 普通对象 获取备忘录对象(){
        return memento;
    }
    /**
     * 备忘录赋值方法
     */
    public void 保存备忘录对象(普通对象 memento){
        this.memento = memento;
    }
}