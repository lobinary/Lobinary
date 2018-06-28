package com.xxx.newsvn;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;

public class SimpleISVNDiffStatusHandler implements ISVNDiffStatusHandler{
	private final static Logger logger = Logger.getLogger("SimpleISVNDiffStatusHandler");
	public List<String> list = new ArrayList<String>();
	
	@Override
	public void handleDiffStatus(SVNDiffStatus diffStatus) throws SVNException {
		
		
		try {
			String modificateStatus = diffStatus.getModificationType().toString();
			String typeOfPath = diffStatus.getKind().toString();
//			logger.info("modificateStatus:"+modificateStatus+",typeOfPath:"+typeOfPath+",file:"+diffStatus.getPath());
			if(modificateStatus != "deleted" && modificateStatus != "none" && typeOfPath == "file"){
				list.add(diffStatus.getPath());
				System.out.println(diffStatus.getPath());
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	public int listLength(){
		return list.size();
	}
}
