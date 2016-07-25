package com.boce.test.设计模式.START模拟运行;

import com.boce.test.设计模式.前端控制器模式.Controller;
import com.boce.test.设计模式.前端控制器模式.Dispatcher;


/**
 * 前端控制器模式
	前端控制器模式（Front Controller Pattern）是用来提供一个集中的请求处理机制，所有的请求都将由一个单一的处理程序处理。该处理程序可以做认证/授权/记录日志，或者跟踪请求，然后把请求传给相应的处理程序。以下是这种设计模式的实体。
	前端控制器（Front Controller） - 处理应用程序所有类型请求的单个处理程序，应用程序可以是基于 web 的应用程序，也可以是基于桌面的应用程序。
	调度器（Dispatcher） - 前端控制器可能使用一个调度器对象来调度请求到相应的具体处理程序。
	视图（View） - 视图是为请求而创建的对象。
 */
public class 前端控制器模式 {

	/**
	 * 个人理解：
	 * 
	 * 		这个和现在的mvc框架结构接近，尤其是spring mvc 都是 使用的dispathcer 和 controller 所以理解自然不难，至于使用，如果不自己搭建这个框架，基本不会使用这个模式
	 * 		它讲究的是分离层，让整体框架更加清晰避免像以前 servlet一样， 试图 控制 都混在一起，看起来 不仅乱，还男拓展
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.request("http://www.lobinary.com/addProduct.jsp");
		controller.request("http://www.lobinary.com/deleteProduct.jsp");
	}
	
}
