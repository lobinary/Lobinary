package com.l.test.web.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * <pre>
 * Redis有两种方案，一种是通过spring redis方案进行配置，另一种是通过
 * </pre>
 *
 * @ClassName: JedisTest
 * @author 919515134@qq.com
 * @date 2017年1月6日 下午2:56:53
 * @version V1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring/spring-application.xml" })
public class JedisSpringTest {
	private static Logger logger = LoggerFactory.getLogger(JedisSpringTest.class);

	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() {
		saveUser();
	}

	public void saveUser() {

		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(redisTemplate.getStringSerializer().serialize("key2"), redisTemplate.getStringSerializer().serialize("val2"));
				return null;
			}
		});
	}

	public String getUser() {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize("key");
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String val = redisTemplate.getStringSerializer().deserialize(value);
					return val;
				}
				return null;
			}
		});
	}

}