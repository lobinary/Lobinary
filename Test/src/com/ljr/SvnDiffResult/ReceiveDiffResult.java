package com.ljr.SvnDiffResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;


public class ReceiveDiffResult {
	
    SimpleISVNDiffStatusHandler diffhandler = new SimpleISVNDiffStatusHandler();

    public void diffResult(SVNClientManager ourClientManager,SVNURL repositoryURL,SVNRevision myRevision,String filePath) throws IOException, SVNException{
    	File filename = new File(filePath);
    	creatTxtFile(filename);
        try {
    		FileOutputStream bos = new FileOutputStream(filename);
    		System.setOut(new PrintStream(bos));
    	} catch (FileNotFoundException e1) {
    		e1.printStackTrace();
    	}
       
        ourClientManager.getDiffClient().doDiffStatus(repositoryURL, myRevision, repositoryURL, SVNRevision.HEAD, true, true, diffhandler);
        
        if (null != diffhandler.list && diffhandler.list.size() > 0)
        {
     	   System.out.println(diffhandler);
     	   System.out.println("hahahha");
        }
        else
        {
     	   System.out.println("oh nothing!");
        }
    }
    
//    public void exportDiffResult(SVNClientManager ourClientManager) throws SVNException{
//        System.out.println("list length  " + diffhandler.listLength());
//        SVNURL exportUrl = null;
//        
//        int i;
//        for(i=0;i<diffhandler.listLength();i++){
//     	   String exportPath = "D:/ljrsvntest/" + diffhandler.list.get(i);
//     	   File exportFile = new File(exportPath);
//     	   exportUrl = SVNURL.parseURIEncoded("https://123.125.97.77/svn/db/代扣+托收/" + diffhandler.list.get(i));
//     	   ourClientManager.getUpdateClient().doExport(exportUrl, exportFile, SVNRevision.HEAD, SVNRevision.HEAD, "LF", true, true);
//        }
//    }
	
    private void creatTxtFile(File filename2){
		if (!filename2.exists()){
	  		   try {
				filename2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  		   System.err.println(filename2+"已创建");
	  	   }
	  	else{
	  		   System.out.println(filename2+"已存在");
	  	   }
	}   
}
