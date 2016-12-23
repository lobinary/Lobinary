package com.l.test.web.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.l.test.web.dto.UserInfo;
import com.l.test.web.mapper.UserMapper;

public class TestDao {

	public static void main(String[] args) throws IOException {
		String resource = "classpath:mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			UserInfo blog = mapper.getUserInfoById(1);
		} finally {
		  session.close();
		}
	}

}
