package com.lobinary.框架.redis.简单使用;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class Redis简单使用 {

	private final static Logger logger = LoggerFactory.getLogger(Redis简单使用.class);

	/**
	 * <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
	 * <dependency> <groupId>redis.clients</groupId>
	 * <artifactId>jedis</artifactId> <version>2.9.0</version> </dependency>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis j = new Jedis("test.l.com");
		logger.info("Connection to server sucessfully");
		// check whether server is running or not
		logger.info("Server is running: " + j.ping());
		j.set("key", "我是value");
		System.out.println(j.get("key"));
		j.close();
	}

}
