package com.lobinary.设计模式.START模拟运行;

import com.lobinary.设计模式.装饰器模式.图形;
import com.lobinary.设计模式.装饰器模式.实现类.三角形;
import com.lobinary.设计模式.装饰器模式.装饰器.使用者.蓝色图形;

/**
 * 装饰器模式（Decorator Pattern）
 * 	装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装。
	这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性的前提下，提供了额外的功能。
	我们通过下面的实例来演示装饰器模式的用法。其中，我们将把一个形状装饰上不同的颜色，同时又不改变形状类。
	介绍
	意图：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
	主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
	何时使用：在不想增加很多子类的情况下扩展类。
	如何解决：将具体功能职责划分，同时继承装饰者模式。
	关键代码： 
		1、Component 类充当抽象角色，不应该具体实现。 
		2、修饰类引用和继承 Component 类，具体扩展类重写父类方法。
	应用实例： 
		1、孙悟空有 72 变，当他变成"庙宇"后，他的根本还是一只猴子，但是他又有了庙宇的功能。 
		2、不论一幅画有没有画框都可以挂在墙上，但是通常都是有画框的，并且实际上是画框被挂在墙上。在挂在墙上之前，画可以被蒙上玻璃，装到框子里；这时画、玻璃和画框形成了一个物体。
	优点：装饰类和被装饰类可以独立发展，不会相互耦合，装饰模式是继承的一个替代模式，装饰模式可以动态扩展一个实现类的功能。
	缺点：多层装饰比较复杂。
	使用场景： 
		1、扩展一个类的功能。 
		2、动态增加功能，动态撤销。
	注意事项：可代替继承。
	
	装饰器通过包装一个装饰对象来扩展其功能，而又不改变其接口，这实际上是基于对象的适配器模式的一种变种。它与对象的适配器模式的异同点如下。
	相同点：都拥有一个目标对象。
	不同点：适配器模式需要实现另外一个接口，而装饰器模式必须实现该对象的接口。
	
	
	
	******************************
	*.	问题：动态给一个对象添加一些额外的职责
		思考：可以修改这个类的源代码吗？
		回答：可以
		思考：那直接修改源代码就行了
		问题：如果不可以修改呢？
		思考：如果不可以修改源代码，那怎么添加？
		回答：有一些类库可以直接修改编译后的class文件，但是这里不考虑
           	可以直接包含这个类的对象，如果这个类有实现某些接口，刚好某个需要添加额外功能的方法正好是其中一个方法，
           	那就好办了，Son这个类的paint()方法需要添加一些额外的功能
    *********************************
 * 
 */
public class 装饰器模式 {
	
	/**
	 * 个人理解
	 *  当使用其他jar包中的一些方法时，如果遇到一些接口需要在执行jar中方法前执行其他操作，比如计算调用这个方法的起止时间
	 *  那么，就需要在方法前后增加计时器，然后我们的项目中都调用这个带有计时器的方法，也就是 都调用蓝色图形这个对象的画方法
	 *  
	 *  装饰器模式和适配器模式很接近，为什么接近呢
	 *  因为适配器也是协助我们调用其他接口的
	 *  但是区别在于，适配器多半是为了让我们调用其他接口的时候，因为接口和现在的请求参数不同，需要补充参数，达到方便调用该接口的目的
	 *  而装饰器模式则是帮我们在调用接口前执行一些我们想要的功能，目的不是为了让我们简化调用接口过程，而是增加调用接口的功能性
	 * 	
	 * @param args
	 */
	public static void main(String[] args) {
		图形 普通三角形 = new 三角形();
		
		图形 蓝色三角形 = new 蓝色图形(普通三角形);
		
		普通三角形.画();
		
		蓝色三角形.画();
	}

}
