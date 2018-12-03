package com.l.test.springboot.config;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
        BigDecimal start=null;
        BigDecimal last=null;
        boolean s = false;
        for (int i = 0; i < 100000; i++) {
            BigDecimal b = new BigDecimal(""+i).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal x = b.multiply(new BigDecimal("0.019"));
            BigDecimal y = b.multiply(new BigDecimal("0.02"));
            if(x.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()==y.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()) {
                double v = b.doubleValue();

                if(!s){
                    start = b;
                    last = b;
                    s = true;
                }
//                System.out.println(b.subtract(last));
                if(!(b.subtract(last).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() <=0.01)){
                    s = false;
//                    System.out.println(start + "~" + v);
                }else{

                }
                last = b;
                System.out.println(v);
            }else{
//                System.out.println("not:"+b.doubleValue());
            }
        }
    }

}
