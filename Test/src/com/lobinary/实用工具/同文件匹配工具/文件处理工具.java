package com.lobinary.实用工具.同文件匹配工具;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lobinary.工具类.file.FileUtil;

public class 文件处理工具 {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		List<File> fl = FileUtil.searchFile(new File("J:\\照片\\设备相册"));
		Map<Long,相同文件对象> 相同文件Map = new HashMap<Long,相同文件对象>();
		Map<Long,File> 文件大小Map = new HashMap<Long,File>();

		for (File f : fl) {
			if(文件大小Map.containsKey(f.length())){
				if(FileUtil.isSameFile(文件大小Map.get(f.length()),f)){
					System.out.println("发现相同文件"+文件大小Map.get(f.length()).getAbsolutePath() + "与" + f.getAbsolutePath());
					if(相同文件Map.containsKey(f.length())){
						相同文件对象 相同文件对象 = 相同文件Map.get(f.length());
						相同文件对象.add(f);
					}else{
						相同文件对象 相同文件组 = 相同文件对象.get(f);
						相同文件组.add(文件大小Map.get(f.length()));
						相同文件Map.put(f.length(),相同文件组);
					}
				}
			}else{
				文件大小Map.put(f.length(),f);
			}
		}
		
		System.out.println("############################################共发现"+相同文件Map.size()+"个相同文件数据#####################################################################");
		int successCount = 0;
		List<相同文件对象> 失败列表 = new ArrayList<相同文件对象>();
		for(相同文件对象 xt : 相同文件Map.values()){
			System.out.println(xt);
			boolean result = xt.removeSameFile();
			if(result){
				successCount++;
			}else{
				失败列表.add(xt);
			}
		}
		System.out.println("############################################共"+相同文件Map.size()+"个相同文件数据,成功删除"+successCount+"条，失败"+(相同文件Map.size()-successCount)+"条#####################################################################");
		System.out.println("####################下方为失败列表####################");
		for (相同文件对象 相同文件对象 : 失败列表) {
			System.out.println(相同文件对象);
		}
	}

}
