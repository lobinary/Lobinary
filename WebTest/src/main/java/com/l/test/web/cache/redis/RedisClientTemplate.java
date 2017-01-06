package com.l.test.web.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.l.test.web.cache.redis.datasource.RedisDataSource;

import redis.clients.jedis.ShardedJedis;

//@Repository("redisClientTemplate")
public class RedisClientTemplate {

	private static final Logger log = LoggerFactory.getLogger(RedisClientTemplate.class);

	@Autowired
	private RedisDataSource redisDataSource;

	public void disconnect() {
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		shardedJedis.disconnect();
	}

	/**
	 * 设置单个值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		String result = null;

		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}
		try {
			result = shardedJedis.set(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			redisDataSource.returnResource();
		}
		return result;
	}

	/**
	 * 获取单个值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String result = null;
		ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		if (shardedJedis == null) {
			return result;
		}

		try {
			result = shardedJedis.get(key);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			redisDataSource.returnResource();
		}
		return result;
	}
}