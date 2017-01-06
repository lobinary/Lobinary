package com.l.test.web.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class R {

	private final static Logger logger = LoggerFactory.getLogger(R.class);

	
	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
	  RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() .master("mymaster")
	  .sentinel("127.0.0.1", 26379) .sentinel("127.0.0.1", 26380);
	  return new JedisConnectionFactory(sentinelConfig);
	}
	
	
	
}
