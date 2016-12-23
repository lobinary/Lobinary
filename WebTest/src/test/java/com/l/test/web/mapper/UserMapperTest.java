package com.l.test.web.mapper;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.l.test.web.dto.UserInfo;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring/spring-application.xml"})  
public class UserMapperTest {  
   private static Logger logger = LoggerFactory.getLogger(UserMapperTest.class);  
// private ApplicationContext ac = null;  
   @Resource(name="userMapper")
   private UserMapper userMapper;
 
// @Before  
// public void before() {  
//     ac = new ClassPathXmlApplicationContext("applicationContext.xml");  
//     userService = (IUserService) ac.getBean("userService");  
// }  
 
   @Test  
   public void test1() {
	   UserInfo user = userMapper.getUserInfoById(1);
	   System.out.println(user);
	   logger.info(user.toString());
   }  
}  