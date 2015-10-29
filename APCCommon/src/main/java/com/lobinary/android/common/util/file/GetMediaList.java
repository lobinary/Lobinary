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
	
	long sequence = 0;
	
	int i;
	static int j,k;
	String musicPath;
	Map<Long,Music> musicMap= new HashMap<Long,Music>();
	Map<String,String[]> mediaType = new HashMap<String,String[]>();
	String[] music = {".mp3",".wma",".wav",".asf"};
	String[] video = {".avi",".asf","mpg","rm","rmvb","wmv","mp4","divx"};
	
	private void mediaTypeMap(){
		mediaType.put("music", music);
		mediaType.put("video", video);
	}

	/**
	 * 
	 */
	public void GetList(String mp,String type) {
		mediaTypeMap();
		File file = new File(mp);
		File[] files = file.listFiles(new MediaFileFilter(mediaType.get(type)));
		for(int i = 0;i<files.length;i++){
			if(files[i].isDirectory()){
				GetList(files[i].getPath(),type);
			}
			else{
				Music mu = new Music();
				mu.setName(files[i].getName());
				sequence++;
				mu.setId(System.currentTimeMillis()+sequence);
				musicMap.put(mu.getId(), mu);
				System.out.println(musicMap.get(mu.getId()).getName());
			}
		}
	}
}
