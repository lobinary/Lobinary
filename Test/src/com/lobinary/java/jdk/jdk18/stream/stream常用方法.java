package com.lobinary.java.jdk.jdk18.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lobinary.工具类.O;

public class stream常用方法 {

	/*
	  	中间操作
			该操作会保持 stream 处于中间状态，允许做进一步的操作。它返回的还是的 Stream，允许更多的链式操作。常见的中间操作有：
			filter()：对元素进行过滤；
			sorted()：对元素排序；
			map()：元素的映射；
			distinct()：去除重复元素；
			subStream()：获取子 Stream 等。
		
		终止操作
			该操作必须是流的最后一个操作，一旦被调用，Stream 就到了一个终止状态，而且不能再使用了。常见的终止操作有：
			forEach()：对每个元素做处理；
			toArray()：把元素导出到数组；
			findFirst()：返回第一个匹配的元素；
			anyMatch()：是否有匹配的元素等。
			
	 */
	public static void main(String[] args) throws IOException {
		O.f("filter过滤");
		filter过滤();
		O.f("sorted");
		sorted();
		O.f("findFirst");
		findFirst();
		O.f("文件操作");
		文件操作();
		O.f("文件操作");
	}

	private static void 文件操作() throws IOException {
		Files.list(new File("e:/").toPath()).forEach(System.out::println);
	}

	private static void findFirst() {
		List<String> list = new ArrayList<>();
		list.add("5");
		list.add("3");
		list.add("4");
		list.add("2");
		list.add("1");
		Optional<String> first = list.stream().sorted().findFirst();
		System.out.println(first.get());
	}

	private static void sorted() {
		List<String> list = new ArrayList<>();
		list.add("5");
		list.add("3");
		list.add("4");
		list.add("2");
		list.add("1");
		list.stream().sorted().forEach((s)->{System.out.print(s+" ");});
		System.out.println();
	}

	private static void filter过滤() {
		List<String> list = new ArrayList<>();
		list.add("B231");
		list.add("A1231");
		list.add("C4231");
		list.add("A1231");
		list.stream().filter((s) -> s.startsWith("A")).forEach(System.out::println);
	}

}
