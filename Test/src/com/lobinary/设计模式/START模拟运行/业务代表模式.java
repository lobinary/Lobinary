package com.lobinary.设计模式.START模拟运行;

import com.lobinary.设计模式.业务代表模式.业务代表;
import com.lobinary.设计模式.业务代表模式.客户端JSP;


/**
 * 业务代表模式
	业务代表模式（Business Delegate Pattern）用于对表示层和业务层解耦。它基本上是用来减少通信或对表示层代码中的业务层代码的远程查询功能。在业务层中我们有以下实体。
	客户端（Client） - 表示层代码可以是 JSP、servlet 或 UI java 代码。
	业务代表（Business Delegate） - 一个为客户端实体提供的入口类，它提供了对业务服务方法的访问。
	查询服务（LookUp Service） - 查找服务对象负责获取相关的业务实现，并提供业务对象对业务代表对象的访问。
	业务服务（Business Service） - 业务服务接口。实现了该业务服务的实体类，提供了实际的业务实现逻辑。
	
	http://www.runoob.com/design-pattern/business-delegate-pattern.html
 */
public class 业务代表模式 {

	/**
	 * 个人理解：
	 * 
	 * 		根据业务类型，执行任务的一个设计模式
	 * 		整体分布就是：我们想调一个业务，那么通过业务代理来调用，业务代理知道了业务类型之后来负责处理这个业务
	 * 		具体由谁执行，怎么执行，都交给业务代理
	 */
	public static void main(String[] args) {
		
		业务代表 业务代表 = new 业务代表(2);
		客户端JSP 客户端JSP = new 客户端JSP(业务代表);
		客户端JSP.执行业务();
		
	}
	
}
