package com.lobinary.java.jdk.jdk18;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class 方法引用 {

	/**
	 * Function<T, R>：将 T 作为输入，返回 R 作为输出，他还包含了和其他函数组合的默认方法。 Predicate<T> ：将 T
	 * 作为输入，返回一个布尔值作为输出，该接口包含多种默认方法来将 Predicate 组合成其他复杂的逻辑（与、或、非）。 Consumer<T>
	 * ：将 T 作为输入，不返回任何内容，表示在单个参数上的操作。
	 */
	public static void main(String[] args) {

		// 使用内部类
		Function<Integer, String> f = new Function<Integer, String>() {
			@Override
			public String apply(Integer t) {
				return String.valueOf(t);
			}
		};

		// 使用 Lambda 表达式
		Function<Integer, String> f1 = (t) -> String.valueOf(t);

		// 使用方法引用的方式
		Function<Integer, String> f2 = String::valueOf;

		System.out.println(f.apply(5));

		System.out.println(f1.apply(6));

		System.out.println(f2.apply(7));
	}

}
