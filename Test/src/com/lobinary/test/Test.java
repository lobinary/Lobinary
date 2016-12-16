package com.lobinary.test;

import java.text.ParseException;

import org.springframework.beans.factory.InitializingBean;

public class Test {
	
	private String a;
	private String b;

	public static void main(String[] args) throws ParseException {
		
//		String r = "^(?!.*(\\.js|\\.css|receive|access3|\\.png|\\.ico|\\.jpg|\\.img)).*$";
//		System.out.println("http://localhost:8080/wechat-app/access3".matches(r));

		Test t = new Test();
		t.a = "x";
		t.b = "yu";
		System.out.println(t);
		Test t2 = new Test();
		t2.a = "z";
		System.out.println(t2);
		
		Class s = InitializingBean.class;
	}
	
	

	@Override
	public String toString() {
		return ga(a,b);
	}

	private static String ga(String c ,String d) {
		return c + d;
	}	
	
	

}
