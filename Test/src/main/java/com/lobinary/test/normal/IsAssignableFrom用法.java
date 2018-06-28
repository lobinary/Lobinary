package com.lobinary.test.normal;

public class IsAssignableFrom用法 {
	
	public static void main(String[] args) {
		ImplClass i = new ImplClass();
		ImplClass i1 = new ImplClass();
		ImplClass i2 = new ImplClass();
		ImplClass1 q1 = new ImplClass1();
		ImplClass2 q2 = new ImplClass2();
		System.out.println(Interface1.class.isInstance(i));
		System.out.println(Interface2.class.isInstance(i));
		System.out.println(Interface2.class.isAssignableFrom(i.getClass()));
		System.out.println(i1.getClass().isAssignableFrom(i2.getClass()));
		System.out.println(q1.getClass().isAssignableFrom(q2.getClass()));
		System.out.println(q1.getClass().isInstance(q2));
	}
	
	static class ImplClass1 implements Interface1{

		@Override
		public void iMethod1() {
			System.out.println("this is implClass1");
		}
		
	}	
	static class ImplClass2 implements Interface1{

		@Override
		public void iMethod1() {
			System.out.println("this is implClass1");
		}
		
	}
	
	static class ImplClass implements Interface1,Interface2{

		@Override
		public void iMethod2() {
			System.out.println("this is 2");
		}

		@Override
		public void iMethod1() {
			System.out.println("this is 1");
		}
		
	}

}
