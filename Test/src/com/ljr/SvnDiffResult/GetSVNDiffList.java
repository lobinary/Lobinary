package com.ljr.SvnDiffResult;
import java.io.*;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.wc.*;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;

public class GetSVNDiffList {
    
     private static SVNClientManager ourClientManager; //svn �ͻ��˹�����
    
     public void zhu(String svnUrl,String scmFileListPath,int versionNum) throws SVNException, IOException{
    	 //��ʼ��*******************
           DAVRepositoryFactory.setup();//��ʼ��֧��httpЭ��Ŀ⡣������ִ�д˲�����
           ISVNOptions options = SVNWCUtil.createDefaultOptions(true);//����ѡ��
           String name = "bin.lv";
           String password = "1234qwer";           
           ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
            
           SVNURL repositoryURL = null;
           SVNRevision myRevision = SVNRevision.create(versionNum);
           try{
                 repositoryURL = SVNURL.parseURIEncoded(svnUrl);
           } catch(SVNException e){
                 e.printStackTrace();
           }
           
           ReceiveDiffResult diresult = new ReceiveDiffResult();
           diresult.diffResult(ourClientManager, repositoryURL, myRevision,scmFileListPath);
//         diresult.exportDiffResult(ourClientManager);
     }
     
}