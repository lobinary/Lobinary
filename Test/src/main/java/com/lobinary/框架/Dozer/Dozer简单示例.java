package com.lobinary.框架.Dozer;

import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.lobinary.工具类.SpringUtil;

public class Dozer简单示例 {
	
	public static void main(String[] args) {

//		Mapper mapper = new DozerBeanMapper();
		Mapper mapper = SpringUtil.getBean(Mapper.class);
		
		DozerA a = new DozerA();
		a.setId(123);
		a.setName("name");
		a.setDate(new Date());
		DozerB b = mapper.map(a, DozerB.class);
		System.out.println(b);
	}

}
