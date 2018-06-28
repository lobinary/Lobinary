package com.lobinary.设计模式.组合模式;

import java.util.ArrayList;
import java.util.List;

public class 员工 {
	
	public 员工(String 姓名) {
		super();
		this.姓名 = 姓名;
	}

	public String 姓名;
	
	public List<员工> 下属 = new ArrayList<员工>();
	
	public void 添加下属(员工 下属员工){
		下属.add(下属员工);
	}

	public void 删除下属(员工 下属员工){
		下属.remove(下属员工);
	}

	@Override
	public String toString() {
		return "员工 [姓名=" + 姓名 + ", 下属=" + 下属.size() + "个]";
	}
	
}
