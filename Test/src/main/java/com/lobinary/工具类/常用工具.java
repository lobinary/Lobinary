package com.lobinary.工具类;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class 常用工具 {

	public static void main(String[] args) {
		long转日期(1471017599000l);
	}

	private static void long转日期(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("long转日期 : 输入(" + time + "),输出:(" + sdf.format(new Date(time)) + ")");
	}

	public static String formetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取文件夹 大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) throws Exception{
		long size = 0;
		File flist[] = f.listFiles();
		if(flist==null)return 0;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
	/**
	 * 获取文件夹 大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static int getFileNums(File f) throws Exception{
		int nums = 0;
		File flist[] = f.listFiles();
		if(flist==null)return 0;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				nums = nums + getFileNums(flist[i]);
			} else {
				nums++;
			}
		}
		return nums;
	}

}
