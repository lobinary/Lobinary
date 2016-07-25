package com.boce.test.设计模式.命令模式.命令.实现类;

import com.boce.test.设计模式.命令模式.命令.命令;
import com.boce.test.设计模式.命令模式.接收者.录音机;

public class 暂停音乐命令 implements 命令 {

	public 录音机 命令接受者录音机;
	
	public 暂停音乐命令(录音机 命令接受者录音机) {
		super();
		this.命令接受者录音机 = 命令接受者录音机;
	}

	@Override
	public void 执行() {
		命令接受者录音机.暂停();
	}

}
