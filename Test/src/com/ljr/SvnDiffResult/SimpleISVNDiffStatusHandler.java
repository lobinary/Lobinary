package com.ljr.SvnDiffResult;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;

public class SimpleISVNDiffStatusHandler implements ISVNDiffStatusHandler{
	public List<String> list = new ArrayList<String>();
	
	@Override
	public void handleDiffStatus(SVNDiffStatus diffStatus) throws SVNException {
		String modificateStatus = diffStatus.getModificationType().toString();
		String typeOfPath = diffStatus.getKind().toString();
		if(modificateStatus != "deleted" && typeOfPath == "file"){
			list.add(diffStatus.getPath());
			System.out.println(diffStatus.getPath());
		}
	}
	
	public int listLength(){
		return list.size();
	}
}
