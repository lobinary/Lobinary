package com.lobinary.java.jdk.jdk16.instrumentation;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.lang.instrument.ClassFileTransformer; 
import java.lang.instrument.IllegalClassFormatException; 
import java.security.ProtectionDomain; 

class Transformer implements ClassFileTransformer { 

   public static final String classNumberReturns2 = "TransClass.class.2"; 

   public static byte[] getBytesFromFile(String fileName) { 
       try { 
           // precondition 
           File file = new File(fileName); 
           InputStream is = new FileInputStream(file); 
           long length = file.length(); 
           byte[] bytes = new byte[(int) length]; 

           // Read in the bytes 
           int offset = 0; 
           int numRead = 0; 
           while (offset <bytes.length 
                   && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) { 
               offset += numRead; 
           } 

           if (offset < bytes.length) { 
               throw new IOException("Could not completely read file "
                       + file.getName()); 
           } 
           is.close(); 
           return bytes; 
       } catch (Exception e) { 
           System.out.println("error occurs in _ClassTransformer!"
                   + e.getClass().getName()); 
           return null; 
       } 
   } 

   public byte[] transform(ClassLoader l, String className, Class<?> c, 
           ProtectionDomain pd, byte[] b) throws IllegalClassFormatException { 
       if (!className.equals("TransClass")) { 
           return null; 
       } 
       return getBytesFromFile(classNumberReturns2); 

   } 
}