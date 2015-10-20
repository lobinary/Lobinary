package com.lobinary.android.platform.pojo.bean;

import java.util.ArrayList;

public class FeaturesQueryListBean {

	public static final int ITEM = 0;
	public static final int SECTION = 1;

	public final int type;
	public final String text;

	public int sectionPosition;
	public int listPosition;


	public int getSectionPosition() {
		return sectionPosition;
	}

	public void setSectionPosition(int sectionPosition) {
		this.sectionPosition = sectionPosition;
	}

	public int getListPosition() {
		return listPosition;
	}

	public void setListPosition(int listPosition) {
		this.listPosition = listPosition;
	}

	public FeaturesQueryListBean(int type, String text) {
		this.type = type;
		this.text = text;
	}


	public FeaturesQueryListBean(int type, String text, int sectionPosition, int listPosition) {
		super();
		this.type = type;
		this.text = text;
		this.sectionPosition = sectionPosition;
		this.listPosition = listPosition;
	}

	@Override public String toString() {
		return text;
	}
	public static ArrayList<FeaturesQueryListBean> getData(){
		ArrayList<FeaturesQueryListBean>  list=new ArrayList<FeaturesQueryListBean>();
		list.add(new FeaturesQueryListBean(SECTION, "本地查询"));
		list.add(new FeaturesQueryListBean(ITEM, "文件查询"));
		list.add(new FeaturesQueryListBean(ITEM, "音频查询"));
		list.add(new FeaturesQueryListBean(ITEM, "视频查询"));
		list.add(new FeaturesQueryListBean(ITEM, "图片查询"));

		list.add(new FeaturesQueryListBean(SECTION, "服务器查询"));
		list.add(new FeaturesQueryListBean(ITEM, "连接中服务器"));
		list.add(new FeaturesQueryListBean(ITEM, "可连接服务器"));
		list.add(new FeaturesQueryListBean(ITEM, "不可连接服务器"));
		

		list.add(new FeaturesQueryListBean(SECTION, "其他查询1"));
		list.add(new FeaturesQueryListBean(ITEM, "查询1"));
		list.add(new FeaturesQueryListBean(ITEM, "查询2"));
		list.add(new FeaturesQueryListBean(ITEM, "查询3"));
		list.add(new FeaturesQueryListBean(ITEM, "查询4"));
		list.add(new FeaturesQueryListBean(ITEM, "查询5"));
		list.add(new FeaturesQueryListBean(ITEM, "查询6"));
		list.add(new FeaturesQueryListBean(ITEM, "查询7"));
		list.add(new FeaturesQueryListBean(ITEM, "查询8"));
		list.add(new FeaturesQueryListBean(ITEM, "查询9"));
		list.add(new FeaturesQueryListBean(ITEM, "查询10"));
		list.add(new FeaturesQueryListBean(ITEM, "查询11"));
		list.add(new FeaturesQueryListBean(ITEM, "查询12"));
		list.add(new FeaturesQueryListBean(ITEM, "查询13"));
		
		list.add(new FeaturesQueryListBean(SECTION, "其他查询2"));
		list.add(new FeaturesQueryListBean(ITEM, "查询14"));
		list.add(new FeaturesQueryListBean(ITEM, "查询15"));
		list.add(new FeaturesQueryListBean(ITEM, "查询16"));
		list.add(new FeaturesQueryListBean(ITEM, "查询17"));
		list.add(new FeaturesQueryListBean(ITEM, "查询18"));
		list.add(new FeaturesQueryListBean(ITEM, "查询19"));
		list.add(new FeaturesQueryListBean(ITEM, "查询20"));
		list.add(new FeaturesQueryListBean(ITEM, "查询21"));
		return list;
	}
}
