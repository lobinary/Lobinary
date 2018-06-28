package com.lobinary.java.jdk.jdk15;

import static java.lang.System.out;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lobinary.java.jdk.jdk15.Introspector.Girl;

public class JDK15特性 {

	public static void main(String[] args) {
		内省();
		自动装箱与拆箱();
		枚举();
		静态导入();
		可变参数("5", 2, '1');
		泛型();
		ForEach循环();
	}
	
	/**
	 * 6.泛型(Generic) C++
	 * 通过模板技术可以指定集合的元素类型，而Java在1.5之前一直没有相对应的功能。一个集合可以放任何类型的对象，
	 * 相应地从集合里面拿对象的时候我们也 不得不对他们进行强制得类型转换。猛虎引入了泛型，它允许指定集合里元素的类型，
	 * 这样你可以得到强类型在编译时刻进行类型检查的好处。
	 * <String> 就是泛型的应用
	 */
	private static void 泛型() {
		List<String> list = new ArrayList<String>();
		System.out.println("泛型List<String>"+list);
	}
	
	/**
	 * 5.内省（Introspector）
	 * 
	 * 是 Java语言对Bean类属性、事件的一种缺省处理方法。例如类A中有属性name,那我们可以通过getName,
	 * setName来得到其值或者设置新 的值。
	 * 通过getName/setName来访问name属性，这就是默认的规则。Java中提供了一套API用来访问某个属性的getter
	 * /setter方法， 通过这些API可以使你不需要了解这个规则（但你最好还是要搞清楚），这些API存放于包java.beans中。 一
	 * 般的做法是通过类Introspector来获取某个对象的BeanInfo信息，然后通过BeanInfo来获取属性的描述器
	 * （PropertyDescriptor），
	 * 通过这个属性描述器就可以获取某个属性对应的getter/setter方法，然后我们就可以通过反射机制来 调用这些方法。
	 * 
	 * 将 Java 的反射以及内省应用到程序设计中去可以大大的提供程序的智能化和可扩展性。
	 * 有很多项目都是采取这两种技术来实现其核心功能，
	 * 例如我们前面提到的 Struts ，还有用于处理 XML 文件的 Digester 项目，
	 * 其实应该说几乎所有的项目都或多或少的采用这两种技术。
	 * 在实际应用过程中二者要相互结合方能发挥真正的智能化以及高度可扩展性。
	 */
	private static void 内省() {
		System.out.println("========================内省============================================================================");
		Girl girl = new Girl();
		girl.setName("毛驴子");
		girl.setAge(24);
		girl.setFat(false);
		girl.addFriend("老公钢蛋儿");
		System.out.println(JSONObject.fromObject(girl));
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(girl.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Method writeMethod = propertyDescriptor.getWriteMethod();
				if(writeMethod!=null){
					System.out.println(writeMethod);
					String methodName = writeMethod.getName();
					if(methodName.toUpperCase().contains("NAME")){
						writeMethod.invoke(girl, "小豆包");
					}
					if(methodName.toUpperCase().contains("AGE")){
						writeMethod.invoke(girl, 23);
					}
					if(methodName.toUpperCase().contains("FAT")){
						writeMethod.invoke(girl, true);
					}
				}
			}
			
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSONObject.fromObject(girl));
		System.out.println("========================内省============================================================================");
	}

	/**
	 * 3.静态导入 通过使用 import static，就可以不用指定 Constants 类名而直接使用静态成员，包括静态方法。 import
	 * xxxx 和 import static xxxx的区别是前者一般导入的是类文件如import
	 * java.util.Scanner;后者一般是导入静态的方法，import static java.lang.System.out。
	 */
	private static void 静态导入() {
		out.println("静态导入(import static java.lang.System.out;)haha");

	}

	/**
	 * <pre>
	 * 2.枚举
	 * 		把集合里的对象元素一个一个提取出来。枚举类型使代码更具可读性，理解清晰，易于维护。枚举类型是强类型的，从而保证了系统安全性。而以类的静态字段实现的类似替代模型，不具有枚举的简单性和类型安全性。
	 * 		简单的用法：JavaEnum简单的用法一般用于代表一组常用常量，可用来代表一类相同类型的常量值。
	 * 复杂用法：Java为枚举类型提供了一些内置的方法，同事枚举常量还可以有自己的方法。可以很方便的遍历枚举对象。
	 */
	private static void 枚举() {
		System.out.println(枚举.枚举1);
	}

	/**
	 * 4.可变参数（Varargs）
	 * 
	 * 可变参数的简单语法格式为： methodName([argumentList], dataType...argumentName);
	 */
	public static void 可变参数(Object... objects) {
		System.out.print("可变参数(methodName([argumentList], dataType...argumentName))");
		for (Object object : objects) {
			System.out.print(object);
		}
		System.out.println();
	}

	/**
	 * <pre>
	 * 1.自动装箱与拆箱：
	 * 		自动装箱的过程：每当需要一种类型的对象时，这种基本类型就自动地封装到与它相同类型的包装中。
	 * 		自动拆箱的过程：每当需要一个值时，被装箱对象中的值就被自动地提取出来，没必要再去调用intValue()和doubleValue()方法。
	 * 		自动装箱，只需将该值赋给一个类型包装器引用，java会自动创建一个对象。
	 * 		自动拆箱，只需将该对象值赋给一个基本类型即可。
	 * 		java——类的包装器
	 * 类型包装器有：Double,Float,Long,Integer,Short,Character和Boolean
	 */
	private static void 自动装箱与拆箱() {
		System.out.println("自动装箱与拆箱" + (new Integer(3) + 2));
	}

	/**
	 * 7.For-Each循环 For-Each循环得加入简化了集合的遍历。假设我们要遍历一个集合对其中的元素进行一些处理。
	 */
	private static void ForEach循环() {
		String[] array = { "1", "2", "3" };
		System.out.print("For-Each循环(for (String s : array)) : ");
		for (String s : array) {
			System.out.print(s);
		}
		System.out.println();
	}

}
