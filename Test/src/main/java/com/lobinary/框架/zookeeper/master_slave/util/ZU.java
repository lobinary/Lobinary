package com.lobinary.框架.zookeeper.master_slave.util;

public class ZU {
    
    /**
     * 返回最后的节点名称 
     *  例如： /a/b/c  返回c
     * 
     * @since add by bin.lv 2017年12月6日 下午1:14:54
     * @param path
     * @return
     */
    public static String getLastNodeName(String name){
        if(!name.contains("/"))return name;
        return name.substring(name.lastIndexOf("/")+1);
    }

    public static String beforeFillZero(int taskNum,int length) {
        String s = String.valueOf(taskNum);
        for (int i = s.length(); i < length; i++) {
            s = "0" + s;
        }
        return s;
    }

}
