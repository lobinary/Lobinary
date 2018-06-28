package com.lobinary.设计模式.备忘录模式.宽接口;

public class 备忘对象宽接口 {

    private String state;
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
        System.out.println("赋值状态：" + state);
    }
    /**
     * 工厂方法，返还一个新的备忘录对象
     */
    public 普通对象 创建备忘录对象(){
        return new Memento(state);
    }
    /**
     * 发起人恢复到备忘录对象记录的状态
     */
    public void 从备忘对象保存库恢复数据(普通对象 memento){
        this.setState(((Memento)memento).getState());
    }
    
    private class Memento implements 普通对象{
        
        private String state;
        /**
         * 构造方法
         */
        private Memento(String state){
            this.state = state;
        }
        
        private String getState() {
            return state;
        }
        private void setState(String state) {
            this.state = state;
        }
    }
}
