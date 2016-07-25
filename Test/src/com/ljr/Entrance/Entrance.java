package com.ljr.Entrance;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;

import com.ljr.IncrementalFetch.DealWar;
import com.ljr.IncrementalFetch.Main;
import com.ljr.IncrementalFetch.ScmPathList2LocalPathList;
import com.ljr.SvnDiffResult.GetSVNDiffList;

public class Entrance {
	public static void main(String[] args) {
//		String svnPath = args[0];//Ҫ�����svnĿ¼
//		String svnRevision = args[1];//��svnRevision�汾��ʼ��Head�汾�����������svnRevision�汾��,���Ҫ��"/"
//		String localWorkspace = args[2];//���ش���ù��̵ĸ�Ŀ¼
//		String packageName = args[3];//Ҫ�����ȫ��������
	
		String svnPath = "http://172.17.103.2/ProjectManagement/��Ʒ��������/����ƽ̨/YeePay_UCM��Ŀ/dev/code/signing/signing-boss/branches/default";//Ҫ�����svnĿ¼
		String svnRevision = "20022";//��svnRevision�汾��ʼ��Head�汾�����������svnRevision�汾��,���Ҫ��"/"
		String localWorkspace = "C:/temp/test";//���ش���ù��̵ĸ�Ŀ¼
		String packageName = "signing-boss.war";//Ҫ�����ȫ��������
		
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
