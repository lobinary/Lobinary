package com.lobinary.test.normal;

import java.util.HashMap;
import java.util.Map;

public class 加密算法 {
    
    /**
     * 常文:A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
密文:Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
     * 
     * @since add by bin.lv 2017年10月24日 下午3:36:50
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String[] x = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String[] y = {"Z","Y","X","W","V","U","T","S","R","Q","P","O","N","M","L","K","J","I","H","G","F","E","D","C","B","A"};
        String s = "F963UF91XXF59Y9F6211F6765F4V86";
        byte[] ba = s.getBytes();
        for(byte b : ba) {
            for (int i = 0; i < y.length; i++) {
                String l = ""+(char)b;
                try {
                    int pi = Integer.parseInt(l);
                    System.out.print(pi);
                    break;
                } catch (Exception e) {
                }
                if(y[i].equals(l)){
                    System.out.print(x[i]);
                }
            }
        }
        
        //\U963F\U91CC\U59B9\U6211\U6765\U4E86
        //阿里妹我来了
    }
    

}
