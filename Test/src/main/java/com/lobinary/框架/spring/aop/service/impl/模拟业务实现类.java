package com.lobinary.框架.spring.aop.service.impl;

import com.lobinary.框架.spring.aop.service.模拟业务类接口;
import org.springframework.stereotype.Service;

/**
 * 用于模拟进行aop实验的service模拟类
 */
@Service
public class 模拟业务实现类 implements 模拟业务类接口 {
    @Override
    public String F1(String R1) {
        System.out.println("11111111111111111111");
        return "return 11111111111";
    }

    @Override
    public void F2() {
        System.out.println("222222222");
    }
}
