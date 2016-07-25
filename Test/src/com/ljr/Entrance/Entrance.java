package com.ljr.Entrance;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;

import com.ljr.IncrementalFetch.DealWar;
import com.ljr.IncrementalFetch.Main;
import com.ljr.IncrementalFetch.ScmPathList2LocalPathList;
import com.ljr.SvnDiffResult.GetSVNDiffList;

public class Entrance {
	public static void main(String[] args) {
//		String svnPath = args[0];//要打包的svn目录
//		String svnRevision = args[1];//从svnRevision版本开始到Head版本打包（不包含svnRevision版本）,最后不要加"/"
//		String localWorkspace = args[2];//本地处理该过程的根目录
//		String packageName = args[3];//要处理的全量包包名
	
		String svnPath = "http://172.17.103.2/ProjectManagement/产品技术中心/核心平台/YeePay_UCM项目/dev/code/signing/signing-boss/branches/default";//要打包的svn目录
		String svnRevision = "20022";//从svnRevision版本开始到Head版本打包（不包含svnRevision版本）,最后不要加"/"
		String localWorkspace = "C:/temp/test";//本地处理该过程的根目录
		String packageName = "signing-boss.war";//要处理的全量包包名
		
		int revision = Integer.parseInt(svnRevision);

		GetSVNDiffList svndiffResult = new GetSVNDiffList();
		try {
			svndiffResult.zhu(svnPath,localWorkspace+"\\svndiffresult.txt",revision);
		} catch (SVNException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		File warPackage = new File(localWorkspace+"/"+packageName);
		DealWar unzipWar = new DealWar();
		String unzipPath = unzipWar.getFileNameWithNoSux(packageName);
		unzipWar.unzip(localWorkspace+"/"+packageName,localWorkspace+"/"+unzipPath);
		
		ScmPathList2LocalPathList changePathList = new ScmPathList2LocalPathList();
		changePathList.ScmPath2LocalPath(localWorkspace+"\\"+unzipPath, localWorkspace+"\\svndiffresult.txt", localWorkspace+"\\CorrectList.txt",localWorkspace+"\\notFoundList");
		Main drawFile = new Main();
		drawFile.DrawFile(localWorkspace+"\\CorrectList.txt",localWorkspace+"\\increaseFile",localWorkspace+"\\"+packageName,localWorkspace+"\\unzipPath",localWorkspace);
	}
}
