package com.lobinary.设计模式.中介者模式.同事类;

import com.lobinary.设计模式.中介者模式.中介者.中介者抽象类;

/**
 * 同事类：
 * 		如果一个对象会影响其他的对象，同时也会被其他对象影响，那么这两个对象称为同事类。
 * 		在类图中，同事类只有一个，这其实是现实的省略，在实际应用中，同事类一般由多个组成，他们之间相互影响，相互依赖。
 * 		同事类越多，关系越复杂。并且，同事类也可以表现为继承了同一个抽象类的一组实现组成。
 * 		在中介者模式中，同事类之间必须通过中介者才能进行消息传递。
 */
public abstract class 同事类抽象类 {
	
	public int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public abstract void changeNum(int num,中介者抽象类 中介者);

}
