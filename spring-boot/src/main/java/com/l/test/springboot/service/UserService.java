package com.l.test.springboot.service;


import com.l.test.springboot.mapper.UserMapper;
import com.l.test.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService { //自定义UserDetailsService 接口
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMapper userDao;

    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户
        logger.info("username："+username);
        List<User> users = userDao.selectAll();
        logger.info(users.toString());
        Optional<User> findResult = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
        if (findResult.isPresent()) {
            logger.info("查找到用户："+username);
            return findResult.get();
        } else {
            logger.info("用户不存在："+username);
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }


}
