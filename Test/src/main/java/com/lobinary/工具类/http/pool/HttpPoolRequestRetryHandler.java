package com.lobinary.工具类.http.pool;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class HttpPoolRequestRetryHandler implements HttpRequestRetryHandler {

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        if(true)return false;
        if(exception instanceof ConnectionPoolTimeoutException){
            return false;
        }

        if(exception instanceof ConnectTimeoutException){
//            System.out.println("####################################>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+exception);
            return addNum(executionCount);
        }
        if(exception instanceof SocketTimeoutException){
//            System.out.println("####################################<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+exception);
            return addNum(executionCount);
        }
        if(exception instanceof SocketException){
//            System.out.println("####################################>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+exception);
            return addNum(executionCount);
        }
        if(exception instanceof HttpHostConnectException){
//            System.out.println("####################################>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+exception);
            return addNum(executionCount);
        }

        if(exception instanceof NoHttpResponseException){
//            System.out.println("####################################>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+exception);
            return addNum(executionCount);
        }


        return false;
    }

    public static int retryNum = 0;

    public static int maxExecutionCount = 0;

    private boolean addNum(int executionCount) {
        retryNum++;
        if(executionCount>maxExecutionCount){
            maxExecutionCount = executionCount;
        }
        return true;
    }
}
