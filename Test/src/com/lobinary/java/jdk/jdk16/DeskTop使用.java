package com.lobinary.java.jdk.jdk16;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class DeskTop使用 {
	
	private static Desktop desktop;

	// 使用默认的浏览器打开网页
	public static void browse() {
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			try {
				// URI指定网页的地址
				desktop.browse(new URI("http://www.baidu.com"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// 编辑文件
	public static void edit() {
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			try {
				desktop.edit(new File("D:\\BruceZhang.txt"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// 打开文件，它和编辑文件的过程类似，都是能看到文件的显示
	public static void open() {
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			try {
				desktop.open(new File("D:\\BruceZhang.txt"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// 打印指定的文件
	public static void print() {
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			try {
				desktop.print(new File("D:\\BruceZhang.txt"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		browse();
//		edit();
//		open();
//		print();
	}
}
