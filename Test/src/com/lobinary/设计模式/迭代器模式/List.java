package com.lobinary.设计模式.迭代器模式;

public class List{

	String[] data = {"I","love","x","x","x"};
	
	public Iterator getIterator(){
		return new ListIterator();
	}
	
	class ListIterator implements Iterator{
		
		int index = 0;//此处可以不写，默认就是0，为方便阅读，此处写0

		@Override
		public boolean hasNext() {
			if(index<data.length)return true;
			return false;
		}

		@Override
		public Object next() {
			String string = data[index];
			index++;
			return string;
		}
		
	}
	
}
