package com.xxx.newsvn;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.SVNEvent;

public class SimpleISVNEventHandler implements ISVNEventHandler{
	private final static Logger logger = Logger.getLogger(SimpleISVNEventHandler.class);
	
	@Override
	public void checkCancelled() throws SVNCancelException{
//		System.out.println("quxiaole");
	}
	
	@Override
	public void handleEvent(SVNEvent event,double progress){
//		System.out.println("eve:"+event);
		logger.info("eve:"+event.getAction() + ",file:" + event.getFile());
	}
}
