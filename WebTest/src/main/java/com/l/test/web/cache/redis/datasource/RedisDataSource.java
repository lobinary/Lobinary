package com.l.test.web.cache.redis.datasource;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	
	/**
	 *  取得redis的客户端，可以执行命令了。
	 * @return
	 */
    public abstract ShardedJedis getRedisClient();
    /**
     * 将资源返还给pool
     * @param shardedJedis
     */
    public void returnResource();
}