package com.lobinary.设计模式.观察者模式;

import java.util.ArrayList;
import java.util.List;

import com.lobinary.设计模式.观察者模式.观察者.观察者;

public class 变动类 {
	
	private int 变动属性;
	
	List<观察者> 观察者列表 = new ArrayList<观察者>();
	
	public void 添加观察者(观察者 观察者){
		观察者列表.add(观察者);
	}

	public void 删除观察者(观察者 观察者){
		观察者列表.remove(观察者);
	}

	private void 通知所有观察者() {
		for (观察者 观察者 : 观察者列表) {
			观察者.下达通知(变动属性);
		}
	}

	public int get变动属性() {
		return 变动属性;
	}

	public void set变动属性(int 变动属性) {
		this.变动属性 = 变动属性;
		通知所有观察者();//XXX 变动属性需要进行通知操作
	}

}
