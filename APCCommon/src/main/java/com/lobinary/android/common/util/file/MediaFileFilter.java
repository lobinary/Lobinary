package com.lobinary.android.common.util.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ljrxxx
 * @since 2015年10月28日 上午12:25:06
 * @version V1.0.0 描述 : 创建文件MusicFileFilter
 * 
 * 
 * 
 */
public class MediaFileFilter implements FileFilter {
	public String[] postfix;
	int i;

	MediaFileFilter(String[] pf) {
		this.postfix = pf;
	}

	@Override
	public boolean accept(File pathname) {
		String getpf;
		if (pathname.isDirectory()) {
			return true;
		} else {
			String name = pathname.getName();
			for (i = 0; i < postfix.length; i++) {
				getpf = postfix[i];
				if (name.endsWith(getpf)) {
					return true;
				} 
			}
			return false;
		}
	}
}
