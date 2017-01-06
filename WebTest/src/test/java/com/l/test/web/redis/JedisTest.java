package com.l.test.web.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.l.test.web.cache.redis.RedisClientAllTemplate;

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
public class JedisTest {
//	private static Logger logger = LoggerFactory.getLogger(JedisTest.class);

	@Resource
	RedisClientAllTemplate redisClientTemplate;

	@Test
	public void test() {
		redisClientTemplate.setex("redis",5, "redisvalue");//设置5秒过期的值
	}

}