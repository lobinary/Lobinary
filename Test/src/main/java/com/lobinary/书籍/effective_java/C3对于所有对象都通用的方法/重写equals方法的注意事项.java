package com.lobinary.书籍.effective_java.C3对于所有对象都通用的方法;

/**
 * 
 * <pre>
 * 	重写equals方法的注意事项有：
 * 
 * 	重写会有一些约定：[*为易出错约定]
 * 		1.自反性 x.equals(x)==true  很难想象能违反这一条
 * 	   *2.对称性 非null元素	x.equals(y)==true时，y.equals(x)也必须为true
 * 		3.传递性 x.equals(y)==true y.equals(z)==true --> x.equals(z)==true
 * 		4.一致性 x.equals(y)==true 如果不对这两个对象更改的情况下，任何时候返回结果不变，一直是true
 * 		5.对于任何非null的引用值x，x.equals(null)必须返回false
 * </pre>
 *
 * @ClassName: 重写equals方法的注意事项
 * @author 919515134@qq.com
 * @date 2016年12月1日 下午6:02:54
 * @version V1.0.0
 */
public class 重写equals方法的注意事项 {
	
	private String value;

	public static void main(String[] args) {
		
	}

	@Override
	public boolean equals(Object obj) {
//		if(obj==null/////////)return false;//5.对于任何非null的引用值x，x.equals(null)必须返回false 
		//尽管上边这个看起来有用，但是实际是多余的，我们下边的方法 obj instanceof *** 这个方法当obj为null时，自动就会返`回false，所以这个步骤可以省略掉
		
		//下边这段代码就违反了2.对称性 非null元素	x.equals(y)==true时，y.equals(x)也必须为true
		//因为当String类型的equals方法返回是false，但是这个类型的equals返回的确是true
//		if(String.class.isInstance(obj.getClass())){
//			return value.equals(obj);
//		}
		
		if(this==obj)return true;//这是一种性能优化，当下面的那个比较方法比较耗费资源时，这个方法能起到一定的优化作用
		
		//为了解决对称性情况，我们使用下面////这种方式
		return obj instanceof 重写equals方法的注意事项 && value.equals(obj);
		
	}
	
}
