package com.l.web.house.util;

import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.*;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import sun.misc.BASE64Encoder;

/**
 * Demonstrates usage of the REST Binding API with HTTPS. Creates a project
 * according to a given XML file.
 */
public class RESTBindingAPIExampleHTTPS {
    public static class MyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            // verification of hostname is switched off
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        // connection and authentication
        //https://bj.lianjia.com/ershoufang/pg1p2p1p4p3/
        Map paramNameToValue = new HashMap(); // parameter name to value map
        String URL_BASE = "https://";
        String method = "POST";
        String userName = "admin";
        String password = "admin";
        String authentication = userName + ':' + password;
        String host = "bj.lianjia.com";
        String port = "80";
        final String HTTP_MODE_POST = "POST";
        // command
        String xmlFile = "CreateNewProject.xml";
        String command = "create";
        // construct URL
        StringBuffer params = new StringBuffer();
        if (paramNameToValue.keySet().size() > 0) {
            boolean isFirstParam = true;
            for (Iterator paramIter = paramNameToValue.keySet().iterator(); paramIter.hasNext();) {
                String paramStr = (String) paramIter.next();
                if (isFirstParam) {
                    params.append("?" + paramStr);
                    isFirstParam = false;
                } else {
                    params.append("&" + paramStr);
                }
                params.append("=" + URLEncoder.encode((String) paramNameToValue.get(paramStr), "UTF-8"));
            }
        }
        URL url = null;
        if (method.equals(HTTP_MODE_POST))
            url = new URL(URL_BASE + host + ':' + port + "/ershoufang/pg1p2p1p4p3/" + command);
        else
            url = new URL(URL_BASE + host + ':' + port + "/ershoufang/pg1p2p1p4p3/" + command + params.toString());
        // open HTTPS connection
        HttpURLConnection connection = null;
        connection = (HttpsURLConnection) url.openConnection();
        ((HttpsURLConnection) connection).setHostnameVerifier(new MyHostnameVerifier());
        connection.setRequestProperty("Content-Type", "text/plain; charset=\"utf8\"");
        connection.setRequestMethod(method);
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode((authentication).getBytes("UTF-8"));
        connection.setRequestProperty("Authorization", "Basic " + encoded);
        // insert XML file
        if (xmlFile != null) {
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
            FileInputStream fileIn = new FileInputStream(xmlFile);
            byte[] buffer = new byte[1024];
            int nbRead;
            do {
                nbRead = fileIn.read(buffer);
                if (nbRead > 0) {
                    out.write(buffer, 0, nbRead);
                }
            } while (nbRead >= 0);
            out.close();
        }
        // execute HTTPS request
        int returnCode = connection.getResponseCode();
        InputStream connectionIn = null;
        if (returnCode == 200)
            connectionIn = connection.getInputStream();
        else
            connectionIn = connection.getErrorStream();
        // print resulting stream
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connectionIn));
        String inputLine;
        while ((inputLine = buffer.readLine()) != null)
            System.out.println(inputLine);
        buffer.close();
    }
}