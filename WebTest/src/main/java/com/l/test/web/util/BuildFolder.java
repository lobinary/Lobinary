package com.l.test.web.util;

import java.io.File;
import java.io.IOException;

public class BuildFolder {

    public static void main(String[] args) throws IOException {
        System.out.println("hello");
        File from = new File("C:/");
        File to = new File("D:/");
        File[] ff = from.listFiles();
        for(File f : ff){
            if(f.getName().contains("$")){
                continue;
            }
            String newName = f.getAbsolutePath().replaceAll("C:\\\\", "D:\\\\");
//                System.out.println(newName);
//            System.out.println(f.getName());
            if(f.isDirectory()){
                File tf = new File(newName);
                if(!tf.exists()){
                    tf.mkdirs();
                }

                for(File f1 : f.listFiles()){
                    if(f1.getName().contains("$")){
                        continue;
                    }
//            System.out.println(f.getName());
                    String newName1 = f1.getAbsolutePath().replaceAll("C:\\\\", "D:\\\\");
//                System.out.println(newName);
                    if(f1.isDirectory()){
                        File tf1 = new File(newName1);
                        if(!tf1.exists()){
                            tf1.mkdirs();
                        }
                    }else if(f1.length()<1000){
                        File tf1 = new File(newName1);
                        tf1.createNewFile();
                    }
                }

            }else if(f.length()<1000){
                File tf = new File(newName);
                tf.createNewFile();
            }
        }
//        System.out.println("===================");
//        for(File f :to.listFiles()){
//            System.out.println(f.getName());
//        }
    }

}
