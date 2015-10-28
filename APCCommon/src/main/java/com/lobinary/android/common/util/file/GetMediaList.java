package com.lobinary.android.common.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.lobinary.android.common.pojo.model.Music;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月27日 下午11:29:30
 * @version V1.0.0 描述 : 创建文件GetMusicList
 * 
 *         
 * 
 */
public class GetMediaList {
	int i;
	static int j,k;
	String musicPath;
	Map<Random,Music> musicMap= new HashMap<Random,Music>();
	/**
	 * 
	 */
	public void GetMusicList(String mp) {
		File file = new File(mp);
		File[] files = file.listFiles(new MusicFileFilter());
		for(int i = 0;i<files.length;i++){
			if(files[i].isDirectory()){
				GetMusicList(files[i].getPath());
			}
			else{
				Music mu = new Music();
				mu.setName(files[i].getName());
				mu.setId(new Random(10000));
				musicMap.put(mu.getId(), mu);
			}
		}
	}
	
	
	public static void main(String[] args) {
		GetMediaList haha = new GetMediaList();
		haha.GetMusicList("H:\\music");
	}
}
