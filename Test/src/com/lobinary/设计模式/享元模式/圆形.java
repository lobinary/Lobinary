package com.lobinary.设计模式.享元模式;
/**
 * 
 * 此对象的分类属性是不允许修改的，只允许构造时初始化
 * 
 */
public class 圆形 {
	
	/**
	 * 分类属性
	 */
	private String 颜色;
	
	private int x坐标;
	private int y坐标;
	private int 半径;
	
	
	public 圆形(String 颜色) {
		super();
		this.颜色 = 颜色;
	}
	
	public void 作图(){
		System.out.println(this);
	}


	@Override
	public String toString() {
		return "圆形 [颜色=" + 颜色 + ", x坐标=" + x坐标 + ", y坐标=" + y坐标 + ", 半径=" + 半径 + "]";
	}

	public int getX坐标() {
		return x坐标;
	}


	public void setX坐标(int x坐标) {
		this.x坐标 = x坐标;
	}


	public int getY坐标() {
		return y坐标;
	}


	public void setY坐标(int y坐标) {
		this.y坐标 = y坐标;
	}


	public int get半径() {
		return 半径;
	}


	public void set半径(int 半径) {
		this.半径 = 半径;
	}
	
	
	
}
