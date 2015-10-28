package com.lobinary.android.common.util.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月28日 上午12:25:06
 * @version V1.0.0 描述 : 创建文件MusicFileFilter
 * 
 *         
 * 
 */
public class MusicFileFilter implements FileFilter{


	@Override
	public boolean accept(File pathname) {
		if(pathname.isDirectory()){
			return true;
		}else{
			String name = pathname.getName();
			if (name.endsWith(".mp3"))
				return true;
			else
				return false;
		}
	}

}
