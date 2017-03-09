package com.lobinary.实用工具.Java源码注释翻译工具;

import java.io.File;
import java.util.List;

import com.lobinary.工具类.JAU2;

public class 翻译Java文件线程 implements Runnable{

	List<File> 该目录下所有文件列表;
	
	public 翻译Java文件线程(List<File> 该目录下所有文件列表) {
		super();
		this.该目录下所有文件列表 = 该目录下所有文件列表;
	}

	@Override
	public void run() {
		while(true){
			if(该目录下所有文件列表.size()>0){
				JAU2.翻译(该目录下所有文件列表.remove(0));
			}
		}
	}

}
