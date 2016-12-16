package com.lobinary.书籍.effective_java.C2创建和销毁对象.Builder构建器;

import java.util.Date;

import com.lobinary.工具类.StringUtil;

/**
 * 
 * <pre>
 * 本代码以食品为例来展示Builder的多功能用途
 * 
 * 		1.当参数过多时，通过调用set方法可以及时校验参数的正确性	
 * 		2.当参数过多时，防止重叠构造器的乱[通常会添加一些不必要的数据]，如本案例：名称和类型必填，其他选填，如果构造器包含所有参数，就要必填无用参数，如果想要定制自己的参数构造器，就要一个一个排列组合，会爆炸，builder可以很好解决这个问题
 * 		
 * Builder使用场景：参数过多的情况下推荐使用，一般大于4个，并且以后会逐渐增加
 * 		
 * </pre>
 *
 * @ClassName: 食品
 * @author 919515134@qq.com
 * @date 2016年11月30日 下午4:20:16
 * @version V1.0.0
 */
public class 食品 {
	
	public static void main(String[] args) {
		食品 金锣王火腿肠 = new 食品.Builder("金锣王火腿肠", "香肠").热量(100).buid();//这种设置变量的方式也是非常不错的，要多多使用该技术
		System.out.println(金锣王火腿肠);
	}

	//必填
	private String 名称;
	private String 类型;
	
	//选填
	private long 重量;//单位 g
	private Date 保质期;
	private long 热量;//焦耳
	
	public static class Builder {

		//必填
		private String 名称;
		private String 类型;
		
		//选填 Builder可以帮助我们给选填的数据默认初始化，这样我们不关系你的数据，就不需要再填写
		private long 重量 = 1;//单位 g
		private Date 保质期 = new Date();
		private long 热量 = 1;//焦耳
		
		public Builder(String 名称, String 类型) {
			super();
			this.名称 = 名称;
			this.类型 = 类型;
		}
		
		public Builder 重量(long 重量){
			this.重量 = 重量;
			return this;
		}
		
		public Builder 保质期(Date 保质期){
			this.保质期 = 保质期;
			return this;
		}
		
		public Builder 热量(long 热量){
			this.热量 = 热量;
			return this;
		}
		
		public 食品 buid(){
			食品 sp = new 食品(this);
			return sp ;
		}
		
	}
	
	private 食品 (Builder builder){
		set名称(builder.名称);
		set类型(builder.类型);
		set重量(builder.重量);
		set保质期(builder.保质期);
		set热量(builder.热量);
	}
	
	public String get名称() {
		return 名称;
	}
	public void set名称(String 名称) {
		if(StringUtil.isEmpty(名称))throw new IllegalStateException("名称不能为空");
		this.名称 = 名称;
	}
	public String get类型() {
		return 类型;
	}
	public void set类型(String 类型) {
		if(StringUtil.isEmpty(类型))throw new IllegalStateException("类型不能为空");
		this.类型 = 类型;
	}
	public long get重量() {
		return 重量;
	}
	public void set重量(long 重量) {
		if(重量<=0)throw new IllegalStateException("重量必须大于0");
		this.重量 = 重量;
	}
	public Date get保质期() {
		return 保质期;
	}
	
	public void set保质期(Date 保质期) {
		if(保质期.compareTo(new Date())<0)throw new IllegalStateException("保质期必须大于当前日期");
		this.保质期 = 保质期;
	}
	public long get热量() {
		return 热量;
	}
	public void set热量(long 热量) {
		if(热量<=0)throw new IllegalStateException("热量必须大于0");
		this.热量 = 热量;
	}

	@Override
	public String toString() {
		return "食品 [名称=" + 名称 + ", 类型=" + 类型 + ", 重量=" + 重量 + ", 保质期=" + 保质期 + ", 热量=" + 热量 + "]";
	}
	
}
