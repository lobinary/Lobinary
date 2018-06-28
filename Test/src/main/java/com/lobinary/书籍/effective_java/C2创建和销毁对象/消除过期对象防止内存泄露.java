package com.lobinary.书籍.effective_java.C2创建和销毁对象;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 
 * <pre>
 * 	为了更好理解内存泄露，我们下面以一个  自制的栈 来举例	
 * 
 * 	乍一看感觉这个代码没有问题，实际隐藏着内存泄露
 * 
 * 	没有将不使用的的对象进行内存回收，请详见相关代码段的解读
 * </pre>
 *
 * @ClassName: 防止内存泄露
 * @author 919515134@qq.com
 * @date 2016年12月1日 上午10:49:34
 * @version V1.0.0
 */
public class 消除过期对象防止内存泄露 {
	
	private Object[] statckArray;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	

	public 消除过期对象防止内存泄露() {
		statckArray = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object value){
		ensureCapacity();
		statckArray[size] = value;
		size++;
	}

	/*
	 * 此处在返回对象的时候，我们只是将size这个指针指向的对象返回，但是并没有把这个指的对象给移除出statckArray
	 * 所以导致这个对象还存在与这个statckArray中，这种就是内存泄露
	 * 
	 * 如果加上注释的话，这样就可以防止内存泄露了
	 * 
	 */
	public Object pop(){
		if(size==0)throw new EmptyStackException();
		Object result = statckArray[--size];
		//statckArray[size]=null;
		return result;
	}
	
	private void ensureCapacity() {
		if(statckArray.length==size){
			statckArray = Arrays.copyOf(statckArray, size*2+1);
		}
	}
	
}
