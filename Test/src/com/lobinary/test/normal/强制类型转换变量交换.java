package com.lobinary.test.normal;

/**
 * 
 * <pre>
 * 	通过强制转型将对象变量交换-----经验证，不可行，报强制转型错误异常
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年2月3日 上午10:03:01
 * @version V1.0.0 描述 : 创建文件强制类型转换变量交换
 * 
 *         
 *
 */
public class 强制类型转换变量交换 {
	
	public static void main(String[] args) {
		Person p = new Person();
		p.setName("personName");
		p.setAge("personAge");
		People pp = People.class.cast(p);
		System.out.println(pp.getName());
	}

	static class Person {
		public String name;
		public String age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
	}
	
	static class People {
		public String id;
		public String name;
		public String age;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
	}
	
}
