package com.lobinary.test;

import java.io.UnsupportedEncodingException;

public class Temp {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println((int)'刘');
        //0101 0010 0001 1000
        //11 1111
        String s = new String("刘");
        byte[] bs = s.getBytes("ISO-8859-1");
        for(byte b : bs){
            System.out.println(b);
        }
        
    }

}
