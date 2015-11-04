package com.lobinary.android.platform.pojo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.ui.activity.LogActivity;
import com.lobinary.android.platform.ui.activity.MusicActivity;

/**
 * 功能列表数据
 * @author Lobinary
 *
 */
public class PinnerListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 602729587642188398L;
	/**
	 * 基本远程方法调用(不带参数型)
	 */
	public final static int BASE_REMOTE_METHOD = 1;
	/**
	 * 页面跳转-需要client连接
	 */
	public final static int ACTIVITY_INTENT_NEED_CLIENT = 2;
	/**
	 * 页面跳转-不需要client连接
	 */
	public final static int ACTIVITY_INTENT_NOT_NEED_CLIENT = 3;
	
	public PinnerListBean(int type, String text) {
		this.type = type;
		this.text = text;
		this.imgId = R.drawable.unknow;
		this.invokeType = 0;
		this.baseMethodName = null;
		this.intentAcivityClass = null;
	}
	
	public PinnerListBean(int type, String text,int imgId,int invokeType,String baseMethodName,Class<? extends Activity> intentAcivity) {
		this.type = type;
		this.text = text;
		this.imgId = imgId;
		this.invokeType = invokeType;
		this.baseMethodName = baseMethodName;
		this.intentAcivityClass = intentAcivity;
	}


	public PinnerListBean(int type, String text, int sectionPosition, int listPosition) {
		super();
		this.type = type;
		this.text = text;
		this.sectionPosition = sectionPosition;
		this.listPosition = listPosition;
		this.imgId = R.drawable.unknow;
		this.invokeType = 0;
		this.baseMethodName = null;
		this.intentAcivityClass = null;
	}
	
	public static final int ITEM = 0;
	public static final int SECTION = 1;

	public final int type;
	public final String text;
	public final int imgId;
	public final int invokeType;
	public final String baseMethodName;
	public final Class intentAcivityClass;

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

	@Override public String toString() {
		return text;
	}
	
	public static ArrayList<PinnerListBean> getTestData(){
		ArrayList<PinnerListBean>  list = new ArrayList<PinnerListBean>();
		list.add(new PinnerListBean(SECTION, "标题"));
		list.add(new PinnerListBean(ITEM, "子标题"));
		return list;
	}
	
	/**
	 * key 是 title value的list是item
	 * @param dataMap
	 * @return
	 */
	public static ArrayList<PinnerListBean> translateData(Map<String,List<String>> dataMap){
		ArrayList<PinnerListBean>  list=new ArrayList<PinnerListBean>();
		for(String key:dataMap.keySet()){
			list.add(new PinnerListBean(SECTION, key));
			for(String item : dataMap.get(key)){
				list.add(new PinnerListBean(ITEM, item));
			}
		}
		return list;
	}
	

	public static ArrayList<PinnerListBean> getQueryData(){
		ArrayList<PinnerListBean>  list = new ArrayList<PinnerListBean>();
		list.add(new PinnerListBean(SECTION, "本地查询"));
		list.add(new PinnerListBean(ITEM, "文件查询"));
		list.add(new PinnerListBean(ITEM, "音频查询"));
		list.add(new PinnerListBean(ITEM, "视频查询"));
		list.add(new PinnerListBean(ITEM, "图片查询"));

		list.add(new PinnerListBean(SECTION, "服务器查询"));
		list.add(new PinnerListBean(ITEM, "连接中服务器"));
		list.add(new PinnerListBean(ITEM, "可连接服务器"));
		list.add(new PinnerListBean(ITEM, "不可连接服务器"));
		

		list.add(new PinnerListBean(SECTION, "其他查询1"));
		list.add(new PinnerListBean(ITEM, "查询1"));
		list.add(new PinnerListBean(ITEM, "查询2"));
		list.add(new PinnerListBean(ITEM, "查询3"));
		list.add(new PinnerListBean(ITEM, "查询4"));
		list.add(new PinnerListBean(ITEM, "查询5"));
		list.add(new PinnerListBean(ITEM, "查询6"));
		list.add(new PinnerListBean(ITEM, "查询7"));
		list.add(new PinnerListBean(ITEM, "查询8"));
		list.add(new PinnerListBean(ITEM, "查询9"));
		list.add(new PinnerListBean(ITEM, "查询10"));
		list.add(new PinnerListBean(ITEM, "查询11"));
		list.add(new PinnerListBean(ITEM, "查询12"));
		list.add(new PinnerListBean(ITEM, "查询13"));
		
		list.add(new PinnerListBean(SECTION, "其他查询2"));
		list.add(new PinnerListBean(ITEM, "查询14"));
		list.add(new PinnerListBean(ITEM, "查询15"));
		list.add(new PinnerListBean(ITEM, "查询16"));
		list.add(new PinnerListBean(ITEM, "查询17"));
		list.add(new PinnerListBean(ITEM, "查询18"));
		list.add(new PinnerListBean(ITEM, "查询19"));
		list.add(new PinnerListBean(ITEM, "查询20"));
		list.add(new PinnerListBean(ITEM, "查询21"));
		return list;
	}

	public static ArrayList<PinnerListBean> getControlData(){
		ArrayList<PinnerListBean>  list=new ArrayList<PinnerListBean>();
		list.add(new PinnerListBean(SECTION, "媒体"));
		list.add(new PinnerListBean(ITEM, "音乐",R.drawable.music,ACTIVITY_INTENT_NEED_CLIENT,null,MusicActivity.class));
		list.add(new PinnerListBean(ITEM, "暂停音乐"));
		list.add(new PinnerListBean(ITEM, "上一曲"));
		list.add(new PinnerListBean(ITEM, "下一曲"));
		list.add(new PinnerListBean(ITEM, "播放视频"));
		list.add(new PinnerListBean(ITEM, "播放图片"));

		list.add(new PinnerListBean(SECTION, "电脑"));
		list.add(new PinnerListBean(ITEM, "关机",R.drawable.music,BASE_REMOTE_METHOD,"shutDown",null));
		list.add(new PinnerListBean(ITEM, "取消关机",R.drawable.music,BASE_REMOTE_METHOD,"cancelShutDown",null));
		list.add(new PinnerListBean(ITEM, "音量+",R.drawable.music,BASE_REMOTE_METHOD,"increaseVoice",null));
		list.add(new PinnerListBean(ITEM, "音量-",R.drawable.music,BASE_REMOTE_METHOD,"decreaseVoice",null));
		list.add(new PinnerListBean(ITEM, "亮度+"));
		list.add(new PinnerListBean(ITEM, "亮度-"));

		return list;
	}
	

	public static ArrayList<PinnerListBean> getCommunicationData(){
		ArrayList<PinnerListBean>  list=new ArrayList<PinnerListBean>();
		list.add(new PinnerListBean(SECTION, "文件交互"));
		list.add(new PinnerListBean(ITEM, "文件管理器"));
		list.add(new PinnerListBean(ITEM, "发送文件"));
		list.add(new PinnerListBean(ITEM, "下载文件"));

		list.add(new PinnerListBean(SECTION, "标题"));
		list.add(new PinnerListBean(ITEM, "功能1"));
		list.add(new PinnerListBean(ITEM, "功能2"));
		list.add(new PinnerListBean(ITEM, "功能3"));
		
		return list;
	}
	
	public static ArrayList<PinnerListBean> getDeveloperData() {
		ArrayList<PinnerListBean>  list=new ArrayList<PinnerListBean>();
		list.add(new PinnerListBean(SECTION, "日志"));
		list.add(new PinnerListBean(ITEM, "日志页面",R.drawable.music,ACTIVITY_INTENT_NOT_NEED_CLIENT,null,LogActivity.class));
		list.add(new PinnerListBean(ITEM, "清空日志"));
		list.add(new PinnerListBean(ITEM, "调整日志级别为:Debug"));
		list.add(new PinnerListBean(ITEM, "调整日志级别为:Info"));
		list.add(new PinnerListBean(ITEM, "调整日志级别为:Error"));

		list.add(new PinnerListBean(SECTION, "测试页面"));
		list.add(new PinnerListBean(ITEM, "Socket测试页面"));
		list.add(new PinnerListBean(ITEM, "测试页面2"));
		list.add(new PinnerListBean(ITEM, "测试页面3"));
		
		return list;
	}
	
}
