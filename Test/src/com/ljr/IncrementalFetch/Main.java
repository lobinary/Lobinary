package com.ljr.IncrementalFetch;

import java.io.File;
import com.ljr.IncrementalFetch.*;
import com.ljr.SvnDiffResult.*;

public class Main {
	public void DrawFile(String fileListPath,String increasePath,String warPath,String unzipPath,String localWorkspace) {
//		String fileName = args[0];//文件清单
//		String pathDes = args[1];//增量文件存放目录
//		String warPath = args[2];//war包目录
//		String unzipPath = args[3];//解压后目录
		
//		String fileName = "D:\\0704上线\\全流程\\list.txt";//文件清单
//		String pathDes = "D:\\0704上线\\全流程\\incre\\";//增量文件存放目录
//		String warPath = "D:\\0704上线\\全流程\\hrbcms-0.0.1.war";//war包目录
//		String unzipPath =  "D:\\0704上线\\全流程\\unzip\\";//解压后目录
//		String pathSource = unzipPath;
		DealWar warDeal = new DealWar();
		warDeal.unzip(warPath,unzipPath);
		File file = new File(fileListPath);
		GetIncreFile gifile = new GetIncreFile(file,localWorkspace);
	}
}