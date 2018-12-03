package com.l.test.springboot.controller;

import com.l.test.springboot.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userDao;

    @RequestMapping("/login")
    public String login(){
        logger.info("login----------");
        return "login";
    }

    @RequestMapping("/type/list")
    public String  tyepList(Model model) {
        return "card/type-list";
    }

}
