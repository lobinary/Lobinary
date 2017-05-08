package com.l.web.house.dto;

public class 房屋统计信息 {
	
	private String 房屋涨价数量;
	private String 房屋降价数量;
	private String 房屋平稳数量;
	
	public String get房屋涨价数量() {
		return 房屋涨价数量;
	}
	public void set房屋涨价数量(String 房屋涨价数量) {
		this.房屋涨价数量 = 房屋涨价数量;
	}
	public String get房屋降价数量() {
		return 房屋降价数量;
	}
	public void set房屋降价数量(String 房屋降价数量) {
		this.房屋降价数量 = 房屋降价数量;
	}
	public String get房屋平稳数量() {
		return 房屋平稳数量;
	}
	public void set房屋平稳数量(String 房屋平稳数量) {
		this.房屋平稳数量 = 房屋平稳数量;
	}

	@Override
	public String toString() {
		return "房屋统计信息 [房屋涨价数量=" + 房屋涨价数量 + ", 房屋降价数量=" + 房屋降价数量 + ", 房屋平稳数量=" + 房屋平稳数量 + "]";
	}
}
