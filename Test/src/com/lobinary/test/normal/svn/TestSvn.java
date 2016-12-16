package com.lobinary.test.normal.svn;

import java.io.File;

import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNConflictReason;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNEvent;
import org.tmatesoft.svn.core.wc.SVNMergeResult;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatusType;

public class TestSvn {

	public static void main(String[] args) throws SVNException {
		System.out.println("hello");
		SVNStatusType status = null;
		SVNConflictReason reason = null;
		SVNMergeResult rs = SVNMergeResult.createMergeResult(status, reason);
		SVNStatusType ms = rs.getMergeStatus();
		
		SVNURL url = SVNURL.parseURIDecoded( "svn://host/path_to_repository_root/inner_path" );
		SVNRepository repository = SVNRepositoryFactory.create( url, null );
		
		ISVNAuthenticationManager authManager = null;
		ISVNOptions options = null;
		SVNDiffClient sdc = new SVNDiffClient(authManager, options);
		File path1 = null;
		SVNRevision revision1 = null;
		File path2 = null;
		SVNRevision revision2 = null;
		File dstPath = null;
		SVNDepth depth = null;
		boolean useAncestry = true;
		boolean force = true;
		boolean dryRun = true;
		boolean recordOnly = true;
		sdc.setEventHandler(new ISVNEventHandler() {
			
			@Override
			public void checkCancelled() throws SVNCancelException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleEvent(SVNEvent event, double progress) throws SVNException {
				// TODO Auto-generated method stub
				
			}
		});
		sdc.doMerge(path1, revision1, path2, revision2, dstPath, depth, useAncestry, force, dryRun, recordOnly);
	}

}
