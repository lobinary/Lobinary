package com.lobinary.java.jdk.jdk16.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class 轻量http服务器实现 {

	public static void main(String[] args) throws Exception{ 
		 HttpServerProvider httpServerProvider = HttpServerProvider.provider(); 
		 InetSocketAddress addr = new InetSocketAddress(7778); 
		 HttpServer httpServer = httpServerProvider.createHttpServer(addr, 1); 
		 httpServer.createContext("/myapp/", new MyHttpHandler()); 
		 httpServer.setExecutor(null); 
		 httpServer.start(); 
		 System.out.println("started"); 
	 } 

	 static class MyHttpHandler implements HttpHandler{ 
		 public void handle(HttpExchange httpExchange) throws IOException {         
			 System.out.println("接收到请求");
			 String response = "Hello world!"; 
			 httpExchange.sendResponseHeaders(200, response.length()); 
			 OutputStream out = httpExchange.getResponseBody(); 
			 out.write(response.getBytes()); 
			 out.close(); 
		 }
	 }
	 
	 //启动后请打开下方网址进行访问
	 //http://127.0.0.1:7778/myapp/
	
}
