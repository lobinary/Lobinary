package com.lobinary.test;

import java.text.ParseException;

public class Test {

	public static void main(String[] args) throws ParseException {
		
		String r = "^(?!.*(\\.js|\\.css|receive|access3|\\.png|\\.ico|\\.jpg|\\.img)).*$";
		System.out.println("http://localhost:8080/wechat-app/access3".matches(r));
		
	}	

}
