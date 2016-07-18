package com.boce.test.设计模式.START模拟运行;

import com.boce.test.设计模式.代理模式.虚拟代理.代理对象;
import com.boce.test.设计模式.代理模式.虚拟代理.图形.图形;

/**
 * 代理模式
	在代理模式（Proxy Pattern）中，一个类代表另一个类的功能。这种类型的设计模式属于结构型模式。
	在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口。
	介绍
	意图：为其他对象提供一种代理以控制对这个对象的访问。
	主要解决：在直接访问对象时带来的问题，比如说：要访问的对象在远程的机器上。在面向对象系统中，有些对象由于某些原因（比如对象创建开销很大，或者某些操作需要安全控制，或者需要进程外的访问），直接访问会给使用者或者系统结构带来很多麻烦，我们可以在访问此对象时加上一个对此对象的访问层。
	何时使用：想在访问一个类时做一些控制。
	如何解决：增加中间层。
	关键代码：实现与被代理类组合。
	应用实例： 
		1、Windows 里面的快捷方式。 
		2、猪八戒去找高翠兰结果是孙悟空变的，可以这样理解：把高翠兰的外貌抽象出来，高翠兰本人和孙悟空都实现了这个接口，猪八戒访问高翠兰的时候看不出来这个是孙悟空，所以说孙悟空是高翠兰代理类。 
		3、买火车票不一定在火车站买，也可以去代售点。 
		4、一张支票或银行存单是账户中资金的代理。支票在市场交易中用来代替现金，并提供对签发人账号上资金的控制。 
		5、spring aop。
	优点： 
		1、职责清晰。 
		2、高扩展性。 
		3、智能化。
	缺点： 
		1、由于在客户端和真实主题之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢。 
		2、实现代理模式需要额外的工作，有些代理模式的实现非常复杂。
	使用场景：按职责来划分，通常有以下使用场景： 
		1、远程代理。 
		2、虚拟代理。 
		3、Copy-on-Write 代理。 
		4、保护（Protect or Access）代理。 
		5、Cache代理。 
		6、防火墙（Firewall）代理。 
		7、同步化（Synchronization）代理。 
		8、智能引用（Smart Reference）代理。
	注意事项： 
		1、和适配器模式的区别：适配器模式主要改变所考虑对象的接口，而代理模式不能改变所代理类的接口。 
		2、和装饰器模式的区别：装饰器模式为了增强功能，而代理模式是为了加以控制。
		
		
	在需要用比较通用和复杂的对象指针代替简单的指针的时候，使用 Proxy模式。下面是一些可以使用Proxy模式常见情况：
		1) 远程代理（Remote  Proxy）为一个位于不同的地址空间的对象提供一个本地的代理对象。这个不同的地址空间可以是在同一台主机中，也可是在另一台主机中，远程代理又叫做大使(Ambassador)
		2) 虚拟代理（Virtual Proxy）根据需要创建开销很大的对象。如果需要创建一个资源消耗较大的对象，先创建一个消耗相对较小的对象来表示，真实对象只在需要时才会被真正创建。 
		3) 保护代理（Protection Proxy）控制对原始对象的访问。保护代理用于对象应该有不同的访问权限的时候。
		4) 智能指引（Smart Reference）取代了简单的指针，它在访问对象时执行一些附加操作。
		5) Copy-on-Write代理：它是虚拟代理的一种，把复制（克隆）操作延迟到只有在客户端真正需要时才执行。一般来说，对象的深克隆是一个开销较大的操作，Copy-on-Write代理可以让这个操作延迟，只有对象被用到的时候才被克隆。
 */
public class 代理模式 {
	
	/**
	 * 个人理解：
	 * 
	 * 		代理模式具体分几种，本代码只展示了虚拟代理
	 * 		后续会增加其他代理源码
	 * @param args
	 */
	public static void main(String[] args) {
		图形 图形 = new 代理对象();
		
		System.out.println("准备调用方法");
		图形.画();
	}

}
