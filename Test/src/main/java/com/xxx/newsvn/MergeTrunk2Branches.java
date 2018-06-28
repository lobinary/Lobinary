package com.xxx.newsvn;

import java.io.File;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class MergeTrunk2Branches {
	private final static Logger logger = Logger.getLogger("Merge");
	public static SVNClientManager ourClientManager; //svn 客户端管理类
	
	public void mergeTr2Br(){
		DAVRepositoryFactory.setup();
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		String name = "liujingran";
		String password = "3759662Ljr";
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions)options, name, password);
		
	}
}
