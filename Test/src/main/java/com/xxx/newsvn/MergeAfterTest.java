package com.xxx.newsvn;

import java.io.File;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNConflictHandler;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNConflictDescription;
import org.tmatesoft.svn.core.wc.SVNConflictResult;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class MergeAfterTest {
	private final static Logger logger = Logger.getLogger(MergeAfterTest.class);
	public static SVNClientManager ourClientManager; //svn �ͻ��˹�����
	
	public void doMergeTest(String fromURL,String toURL,String localWS) throws SVNException{
//		System.out.println("��ʼ����merge������From"+"To");
		logger.info("��ʼ����merge������From:"+toURL+" To:"+fromURL);
			
		SVNURL srcURL=SVNURL.parseURIEncoded(fromURL);
		SVNURL destURL=SVNURL.parseURIEncoded(toURL);
		File localWorkSpace = new File(localWS);
		
		DAVRepositoryFactory.setup();
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		String l = "";
		String z = "1r";
		ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions)options, l, z);
		
		logger.info("checkoutĿ��⣺"+fromURL);
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		updateClient.doCheckout(destURL, localWorkSpace, SVNRevision.HEAD, SVNRevision.HEAD,SVNDepth.INFINITY, true);
//		updateClient.doUpdate(localWorkSpace, SVNRevision.HEAD, true);
		logger.info("�����checkout");
		logger.info("��ʼ����merge");
		SVNDiffClient diffClient = ourClientManager.getDiffClient();
		
		

		diffClient.setIgnoreExternals(false);  
		DefaultSVNOptions op = (DefaultSVNOptions) diffClient.getOptions();  
		//����һ�� ConflictResolverHandler  
		op.setConflictHandler(new ISVNConflictHandler(){

			@Override
			public SVNConflictResult handleConflict(SVNConflictDescription conflictDescription) throws SVNException {
				logger.info("���ֳ�ͻ"+conflictDescription.getConflictAction());
				return null;
			}
			
		});

		diffClient.setEventHandler(new SimpleISVNEventHandler());
		
		diffClient.doMerge(srcURL, SVNRevision.HEAD, destURL, SVNRevision.HEAD, localWorkSpace,SVNDepth.INFINITY, true, false, true, false);
		logger.info("�����merge");
	}
	
	public static void main(String[] args) {
		MergeAfterTest a = new MergeAfterTest();
		try {
//			String fromURL = "http://172.17.103.2/YeePay/dev/app/wechat-app/trunk";
//			String toURL = "http://172.17.103.2/YeePay/dev/app/wechat-app/branches/20160801";
			String fromURL = "http://172.17.103.2/YeePay/dev/app/wechat-app/branches/20160321/test/a";
			String toURL = "http://172.17.103.2/YeePay/dev/app/wechat-app/branches/20160321/test/b";
			
			String localWS = "E:\\app\\data\\test";
			File f = new File(localWS);
			f.deleteOnExit();
			f.mkdirs();
			a.doMergeTest(fromURL,toURL,localWS);
		} catch (SVNException e) {
			e.printStackTrace();
		}
	}
}
