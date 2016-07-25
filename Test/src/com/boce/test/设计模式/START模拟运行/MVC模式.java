package com.boce.test.设计模式.START模拟运行;

import com.boce.test.设计模式.MVC模式.Controller;
import com.boce.test.设计模式.MVC模式.Model;
import com.boce.test.设计模式.MVC模式.View;


/**
 * MVC 模式
	MVC 模式代表 Model-View-Controller（模型-视图-控制器） 模式。这种模式用于应用程序的分层开发。
	Model（模型） - 模型代表一个存取数据的对象或 JAVA POJO。它也可以带有逻辑，在数据变化时更新控制器。
	View（视图） - 视图代表模型包含的数据的可视化。
	Controller（控制器） - 控制器作用于模型和视图上。它控制数据流向模型对象，并在数据变化时更新视图。它使视图与模型分离开。
 */
public class MVC模式 {

	/**
	 * 个人理解：
	 * 
	 * 		mvc模式应用最广泛的是在web开发中，m是model、pojo、dto这些对象，v是jsp等对象，c是struts2中的action或springmvc中的controller
	 * 		mvc协助我们通过数据库里的数据装配出来的对象，通过c控制层，把对象数据装配到v展示层，让各层分开，方便拓展，思路清晰
	 */
	public static void main(String[] args) {
		Model model = new Model("老婆","女");
		View view = new View();
		Controller c = new Controller(model, view);
		c.接收到请求();
	}
	
}
