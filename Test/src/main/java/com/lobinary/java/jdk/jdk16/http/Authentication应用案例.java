package com.lobinary.java.jdk.jdk16.http;

/**
 * 
 * <pre>
 * 	http://www.ibm.com/developerworks/cn/java/j-lo-jse62/index.html
 * </pre>
 *
 * @ClassName: Authentication应用案例
 * @author 919515134@qq.com
 * @date 2016年10月8日 下午12:58:25
 * @version V1.0.0
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * 
 * <pre>
 * 
 * 	http://www.ibm.com/developerworks/cn/java/j-lo-jse62/index.html
 * 
 * 	http://docs.oracle.com/javase/6/docs/technotes/guides/net/http-auth.html
 * 
 * </pre>
 *
 * @ClassName: Authentication应用案例
 * @author 919515134@qq.com
 * @date 2016年10月8日 下午1:26:38
 * @version V1.0.0
 */
public class Authentication应用案例 {

    static final String kuser = "username"; // your account name
    static final String kpass = "password"; // your password for the account

    static class MyAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            // I haven't checked getRequestingScheme() here, since for NTLM
            // and Negotiate, the usrname and password are all the same.
            System.err.println("Feeding username and password for " + getRequestingScheme());
            return (new PasswordAuthentication(kuser, kpass.toCharArray()));
        }
    }

    public static void main(String[] args) throws Exception {
        Authenticator.setDefault(new MyAuthenticator());
        URL url = new URL("https://www.baidu.com");
        InputStream ins = url.openConnection().getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String str;
        while((str = reader.readLine()) != null)
            System.out.println(str);
    }
}

