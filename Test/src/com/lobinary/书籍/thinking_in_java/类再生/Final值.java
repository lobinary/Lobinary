package com.lobinary.书籍.thinking_in_java.类再生;

public class Final值 {
	
	public static void main(String[] args) throws InterruptedException {
		
		final int i;
		
		
		
		
		
		final FC f = new FC();
		final int ii = 1;
		final String ss = "s";
		f.i=5;
//		ii++;//The final local variable ii cannot be assigned. It must be blank and not using a compound assignment
		f.i=4;//对象中的变量是可以改变的
//		f = new FC();//更改对象指向对象是不可以的，The final local variable ii cannot be assigned. It must be blank and not using a compound assignment
		System.out.println("线程执行前f:"+f.i);
		System.out.println("线程执行前ii:"+ii);
		System.out.println("线程执行前ss:"+ss);
		new Thread(){
			FC l = f;
			int iii = ii;
			String sss = ss;
			@Override
			public void run() {
				super.run();
				for (int i = 0; i < 10; i++) {
					l.i++;
					iii++;
					sss += "=";
				}
				System.out.println("l:"+l.i);
				System.out.println("iii:"+iii);
				System.out.println("sss:"+sss);
			}
			
		}.start();
		Thread.sleep(1000);
		System.out.println("线程执行后f:"+f.i);//对象中的数值可变
		System.out.println("线程执行后ii:"+ii);
		System.out.println("线程执行后ss:"+ss);
	}

}

class FC{
	int i = 1;
}

