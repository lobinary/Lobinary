package com.ljr.IncrementalFetch;

import java.io.File;
import com.ljr.IncrementalFetch.*;
import com.ljr.SvnDiffResult.*;

public class Main {
	public void DrawFile(String fileListPath,String increasePath,String warPath,String unzipPath,String localWorkspace) {
//		String fileName = args[0];//�ļ��嵥
//		String pathDes = args[1];//�����ļ����Ŀ¼
//		String warPath = args[2];//war��Ŀ¼
//		String unzipPath = args[3];//��ѹ��Ŀ¼
		
//		String fileName = "D:\\0704����\\ȫ����\\list.txt";//�ļ��嵥
//		String pathDes = "D:\\0704����\\ȫ����\\incre\\";//�����ļ����Ŀ¼
//		String warPath = "D:\\0704����\\ȫ����\\hrbcms-0.0.1.war";//war��Ŀ¼
//		String unzipPath =  "D:\\0704����\\ȫ����\\unzip\\";//��ѹ��Ŀ¼
//		String pathSource = unzipPath;
		DealWar warDeal = new DealWar();
		warDeal.unzip(warPath,unzipPath);
		File file = new File(fileListPath);
		GetIncreFile gifile = new GetIncreFile(file,localWorkspace);
	}
}