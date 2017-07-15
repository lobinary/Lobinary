package com.l.web.house.dto;

import com.l.web.house.model.房屋基本信息;

public class 房屋统计信息 extends 房屋基本信息{
	
	private String 房屋基本信息id;
	private String 房屋本批次号;
    private String 房屋本批次价格;
    private String 房屋本批次首付;
    private String 房屋本批次评估价;
	private String 房屋上一批次号;
	private String 房屋上批次价格;
	private String 差距价格;
	private String 房屋涨价数量;
	private String 房屋降价数量;
	private String 房屋平稳数量;
	private String 批次号;
	
	public String get批次号() {
		return 批次号;
	}
	public void set批次号(String 批次号) {
		this.批次号 = 批次号;
	}
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

	public String get房屋基本信息id() {
		return 房屋基本信息id;
	}
	public void set房屋基本信息id(String 房屋基本信息id) {
		this.房屋基本信息id = 房屋基本信息id;
	}
	public String get房屋上一批次号() {
		return 房屋上一批次号;
	}
	public void set房屋上一批次号(String 房屋上一批次号) {
		this.房屋上一批次号 = 房屋上一批次号;
	}
	public String get差距价格() {
		return 差距价格;
	}
	public void set差距价格(String 差距价格) {
		this.差距价格 = 差距价格;
	}

	@Override
	public String toString() {
		return "房屋统计信息 [房屋涨价数量=" + 房屋涨价数量 + ", 房屋降价数量=" + 房屋降价数量 + ", 房屋平稳数量=" + 房屋平稳数量 +"]";
	}
	public String get房屋本批次号() {
		return 房屋本批次号;
	}
	public void set房屋本批次号(String 房屋本批次号) {
		this.房屋本批次号 = 房屋本批次号;
	}
	public String get房屋本批次价格() {
		return 房屋本批次价格;
	}
	public void set房屋本批次价格(String 房屋本批次价格) {
		this.房屋本批次价格 = 房屋本批次价格;
	}
	public String get房屋上批次价格() {
		return 房屋上批次价格;
	}
	public void set房屋上批次价格(String 房屋上批次价格) {
		this.房屋上批次价格 = 房屋上批次价格;
	}
    public String get房屋本批次首付() {
        return 房屋本批次首付;
    }
    public void set房屋本批次首付(String 房屋本批次首付) {
        this.房屋本批次首付 = 房屋本批次首付;
    }
    public String get房屋本批次评估价() {
        return 房屋本批次评估价;
    }
    public void set房屋本批次评估价(String 房屋本批次评估价) {
        this.房屋本批次评估价 = 房屋本批次评估价;
    }
}
