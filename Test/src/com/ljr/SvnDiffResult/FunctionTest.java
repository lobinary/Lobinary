package com.ljr.SvnDiffResult;
import java.io.BufferedOutputStream;
import java.io.*;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;


public class FunctionTest {
	SVNURL repositoryURL = null;

	public void doDelete(){
	    SVNURL[] repositoryPath = new SVNURL[1];
		try{
            repositoryPath[0] = SVNURL.parseURIEncoded("https://123.125.97.77/svn/pay/trunk/平台系统/高阳/银行网关/fdfadsfadsf");
       }catch(SVNException e){
            e.printStackTrace();
       }
	}
	
	
	public void doDiff(SVNClientManager ourClientManager) throws SVNException{
		BufferedOutputStream result = null;
		SVNRevision myRevision = SVNRevision.create(52003);
		try {
            result =new BufferedOutputStream(new FileOutputStream("E:/result.txt"));
            ourClientManager.getDiffClient().doDiff(repositoryURL, myRevision, repositoryURL, SVNRevision.HEAD, true, true, result);
            //ourClientManager.getDiffClient().doDiff(compFile, SVNRevision.HEAD, SVNRevision.WORKING, SVNRevision.HEAD, SVNDepth.INFINITY, true, result,null);
            //ourClientManager.getDiffClient().doDiff(repositoryURL, SVNRevision.HEAD, myRevision, SVNRevision.HEAD, SVNDepth.INFINITY, true, result);
      } catch (FileNotFoundException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
      }
	}
	
	
	public void doImport(SVNClientManager ourClientManager){
		String myWorkingCopyPath = "E:/svnWorkingCopy";
		
		File wcDir = new File(myWorkingCopyPath);
        SVNCommitInfo commitInfo = null;
		try {
			commitInfo = ourClientManager.getCommitClient().doImport(wcDir,repositoryURL,"New Feature test",false,false);
		} catch (SVNException e) {
			e.printStackTrace();
		}
        System. out.println(commitInfo.toString());
	}
	
	
}
