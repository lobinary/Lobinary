package com.lobinary.书籍.effective_java.C3对于所有对象都通用的方法;

/**
 * 
 * <pre>
 * 	克隆方法变成两个独立的副本，需要实现Cloneable接口	
 * 
 * 	高级程序员很少实现或使用clone接口，随意不推荐使用，因为我们是高级程序员O(∩_∩)O哈哈~
 * </pre>
 *
 * @ClassName: 克隆方法
 * @author 919515134@qq.com
 * @date 2016年12月5日 下午1:55:15
 * @version V1.0.0
 */
public class 克隆方法 implements Cloneable{

	public String[] array = new String[1];


	/**
	 * clone方法可以更改返回对象从父类改成子类型（这里有一条原则，永远不要让客户做任何类库可以替客户完成的事）
	 * 
	 * 如果是想让其他人继承，则保留为protect并抛出异常，否则
	 * 
	 * 1.方法声明为public 
	 * 2.clone方法不要抛出CloneNotSupportedException异常
	 */
	@Override
	public 克隆方法 clone()  {
		克隆方法 cloneObj = null;
		
		//当仅有普通变量时可以使用该方法
		//但是因为喜爱你在变量是引用类型，所以不可以使用这个
//		cloneObj =(克隆方法) super.clone();
		
		//需要对引用对象进行递归clone
		try {//共有的
			cloneObj = (克隆方法) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloneObj.array = this.array.clone();
		
		return cloneObj;
	}
	
	public static void main(String[] args) {
		克隆方法 x = new 克隆方法();
		x.array[0] = "a";
		克隆方法 y = x.clone();
		System.out.println("克隆方法 y = x.clone();==>");
		System.out.println("x:"+x);
		System.out.println("y:"+y);
		x.array[0] = "c";
		System.out.println("x.array[0] = \"c\";==>");
		System.out.println("x:"+x);
		System.out.println("y:"+y);
		y.array[0] = "d";
		System.out.println("y.array[0] = \"d\";==>");
		System.out.println("x:"+x);
		System.out.println("y:"+y);
	}

	@Override
	public String toString() {
		String s = "[array=${1}}]";
		String l = "{";
		if(array!=null&&array.length>0){
			for (String ss : array) {
				l += ss+",";
			}
			l = l.substring(0, l.length()-1);
		}
		return s.replace("${1}", l);
	}

}
