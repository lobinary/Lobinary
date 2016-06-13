package com.boce.test.normal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class 通过txt保存对象 {

	public static void main(String[] args) {
		List<RequestRule> rrList = new ArrayList<RequestRule>();
		File file = new File("c:\\Test.data");
		RequestRule rr = new RequestRule();
		rr.setMsgRequestParam("hahaha");
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		rrList.add(rr);
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(rrList); // 括号内参数为要保存java对象
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("保存完毕");
		open(file.getPath());
	}

	private static void open(String path) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		File f = new File(path);
		try {
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<RequestRule> rrList = (List<RequestRule>) ois.readObject();// 强制类型转换
			System.out.println(rrList.size());
			System.out.println(rrList.get(0).getMsgRequestParam());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
