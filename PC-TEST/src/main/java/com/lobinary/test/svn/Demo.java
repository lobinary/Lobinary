package com.lobinary.test.svn;

import java.io.*;
import java.util.Map;

import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.wc.*;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.util.SVNPathUtil;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.util.ISVNDebugLog;

public class Demo {
     
     private static SVNClientManager ourClientManager; //声明svn 客户端管理类
     
     public static void main(String[] args) throws SVNException{
           SVNRepositoryFactoryImpl. setup();//初始化支持http协议的库。必须先执行此操作。
           String name = "lvbin";
           String password = "lvb1310";
           
           SVNURL repositoryURL = null;
           SVNURL[] repositoryPath = new SVNURL[1];
           BufferedOutputStream result = null;
           SVNRevision myRevision = SVNRevision.create(126);
            
           try{
                 repositoryURL = SVNURL.parseURIEncoded("https://123.125.97.77/svn/fundability/历史文档");
           } catch(SVNException e){
                 e.printStackTrace();
           }
           
/*doDelete
           try{
        	   repositoryPath[0] = SVNURL.parseURIEncoded("https://123.125.97.77/svn/pay/trunk/平台系统/高阳/银行网关/fdfadsfadsf");
           }catch(SVNException e){
        	   e.printStackTrace();
           }*/
           
           
           String myWorkingCopyPath = "E:/";
           //File compFile = new File("C:/Users/Administrator/Desktop/ptca");

           
           ISVNOptions options = SVNWCUtil.createDefaultOptions(true);//驱动选项
           
           ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
           
           System.out.println("hello");
//           E:/result.txt接收diff结果
           try {
        	   result =new BufferedOutputStream(new FileOutputStream("E:/result.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
           
/*doImport*/
           File wcDir = new File(myWorkingCopyPath);
           SVNCommitInfo commitInfo= ourClientManager.getCommitClient().doImport(wcDir,repositoryURL,
                            "New Feature test",null,false,false,SVNDepth.INFINITY);
           System. out.println( commitInfo.toString());
            
/*doDelete
           SVNCommitInfo commitInfo= ourClientManager.getCommitClient().doDelete(repositoryPath, "New Feature test");//doDelete的第一个参数是svn地址，不是本地目录
           System. out.println( commitInfo.toString());*/
            
/*doDiff
           try{
        	   ourClientManager.getDiffClient().doDiff(repositoryURL, myRevision, repositoryURL, SVNRevision.HEAD, true, true, result);
        	   //ourClientManager.getDiffClient().doDiff(compFile, SVNRevision.HEAD, SVNRevision.WORKING, SVNRevision.HEAD, SVNDepth.INFINITY, true, result,null);
        	   //ourClientManager.getDiffClient().doDiff(repositoryURL, SVNRevision.HEAD, myRevision, SVNRevision.HEAD, SVNDepth.INFINITY, true, result);
           }catch (SVNException e){
        	   e.printStackTrace();
           }*/
           
           
           
/*doLog
           //ourClientManager.getLogClient().doLog(repositoryURL, args, SVNRevision.HEAD, myRevision, SVNRevision.HEAD, false, true, 0, null);
           long message;
           ourClientManager.
           SVNInfo info = ourClientManager.doInfo(repositoryURL, SVNRevision.HEAD, SVNRevision.HEAD);
           message = info.getCommittedRevision().getNumber();
           SVNLogClient mylog = null;
           File mypaths = new File("C:/Users/Administrator/Desktop/ptca");
           mylog = new SVNLogClient(ourClientManager, options);*/
           
           
            
     }
}
