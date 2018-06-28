package com.lobinary.实用工具.同文件匹配工具;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lobinary.工具类.常用工具;
import com.lobinary.工具类.file.FileUtil;

public class 文件处理工具 {
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File rootFolder = new File("J:\\照片\\设备相册\\iphone\\iphone5s\\自动备份");
//		File rootFolder = new File("C:\\test");
		
//		List<File> kf = 查找空文件夹(rootFolder);
//		for (File file : kf) {
//			System.out.println(file.getAbsolutePath());
//		}
//		移除相同文件(rootFolder);
		合并文件到文件夹按照修改日期(rootFolder);
	}
	
	private static void 合并文件到文件夹按照修改日期(File rootFolder){
		
		File[] files = rootFolder.listFiles();
		for(File f:files){
			if(f.isDirectory())continue;
			Calendar c = FileUtil.getModifyTimeCal(f);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH)+1;
			File targetFolder = new File(f.getParentFile().getAbsoluteFile() + "\\" + year+"-"+(month<10?"0"+month:month));
			if(!targetFolder.exists())targetFolder.mkdirs();
			File targetFile = new File(targetFolder.getAbsolutePath() + "\\" + f.getName());
			boolean b = f.renameTo(targetFile);
			if(!b){
				System.out.println("移动文件失败\t"+f.getAbsolutePath());
				System.out.println("到\t\t"+targetFile.getAbsolutePath());
			}
		}
		
		
//		Map<Long,File> fm = new TreeMap<Long,File>();
//		System.out.println("准备装配最后修改时间");
//		for(File f : files){
//			fm.put(f.lastModified(), f);
//		}
//		System.out.println("最后修改时间装配完毕，准备开始排序");
//		for(Long l:fm.keySet()){
//			File f = fm.get(l);
//			System.out.println(FileUtil.getModifiedTimeStr(f) + "\t" + f.getAbsolutePath());
//		}
		
		
	}
	
	public static List<File> 查找空文件夹(File rootFolder){
		List<File> 空文件夹 = new ArrayList<File>();
		File[] fileList = rootFolder.listFiles();
		for(File f:fileList){
			if(f.isDirectory()){
				if(f.listFiles().length==0){
					空文件夹.add(f);
				}else{
					空文件夹.addAll(查找空文件夹(f));
				}
			}
		}
		return 空文件夹;
	}

	public static void 移除相同文件(File rootFolder) throws FileNotFoundException, IOException {
		List<File> fl = FileUtil.searchFile(rootFolder);
		Map<Long,相同文件对象> 相同文件Map = new HashMap<Long,相同文件对象>();
		Map<Long,File> sizeMap = new HashMap<Long,File>();
		for (File f : fl) {
			if(sizeMap.containsKey(f.length())){
				if(相同文件Map.containsKey(f.length())){
					相同文件对象 相同文件对象 = 相同文件Map.get(f.length());
					相同文件对象.add(f);
				}else{
					相同文件Map.put(f.length(),相同文件对象.get(sizeMap.get(f.length())));//加入第一次未加入的文件
					相同文件对象 相同文件对象 = 相同文件Map.get(f.length());//加入本次的文件
					相同文件对象.add(f);
				}
			}else{
				sizeMap.put(f.length(), f);
			}
		}
		
		
		System.out.println("############################################共发现"+相同文件Map.size()+"个相同文件数据#####################################################################");
		int successCount = 0;
		List<相同文件对象> 失败列表 = new ArrayList<相同文件对象>();
		for(相同文件对象 xt : 相同文件Map.values()){
			if(xt.sameFileMap.size()==1)continue;
			boolean result = xt.removeSameFile();
			if(result){
				successCount++;
			}else{
				失败列表.add(xt);
			}
		}
		System.out.println("############################################共"+相同文件Map.size()+"个文件数据,"+(successCount+失败列表.size())+"个相同数据，成功删除"+successCount+"条，失败"+失败列表.size()+"条 ,移除文件总大小为："+常用工具.formetFileSize(相同文件对象.文件总大小)+"#####################################################################");
		System.out.println("####################下方为失败列表####################");
		for (相同文件对象 相同文件对象 : 失败列表) {
			System.out.println(相同文件对象);
		}
	}

}
