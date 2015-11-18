package com.lobinary.android.common.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.lobinary.android.common.pojo.model.Model;
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
	Map<Long,Music> musicMap = new HashMap<Long,Music>();
	Map<Long,Music>	videoMap = new HashMap<Long, Music>(); 
	Map<String,String[]> mediaType = new HashMap<String,String[]>();
	Map<String,Map> storeMedia = new HashMap<String,Map>();
	Map<String, Model> getObject = new HashMap<String,Model>();
	String[] music = {".mp3",".wma",".wav",".asf"};
	String[] video = {".avi",".asf","mpg","rm","rmvb","wmv","mp4","divx"};
	
	private void mediaTypeMap(){
		mediaType.put("music", music);
		mediaType.put("video", video);
		storeMedia.put("music", musicMap);
		storeMedia.put("video", videoMap);
		getObject.put("music", new Music());
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
				Model me = getObject.get(type);
				me.setName(files[i].getName());
				sequence++;
				me.setId(System.currentTimeMillis()+sequence);
				storeMedia.get(type).put(me.getId(), me);
				Model pri = getObject.get(type);//test
				pri = (Model) storeMedia.get(type).get(me.getId());//test
				System.out.println(pri.getName());//test
			}
		}
	}
	
	public static void main(String[] args) {
		GetMediaList haha = new GetMediaList();
		haha.GetList("H:\\music","music");
	}
}
