package com.l.web.house.util;

/**
 * Number Util
 * @author bin.lv
 * @since create by bin.lv 2017年7月10日 上午11:13:34
 *
 */
public class NU {

    public static double getDouble(Object d) {
        if(d==null)return 0;
        try {
            return Double.parseDouble(d.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getInt(Object i) {
        if(i==null)return 0;
        try {
            return Integer.parseInt(i.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
