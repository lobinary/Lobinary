package com.xxx.newsvn;


import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

public class SimpleISVNLogEntryHandler implements ISVNLogEntryHandler{
	public List<SVNLogEntry> list = new ArrayList<SVNLogEntry>();

    @Override
    public void handleLogEntry(SVNLogEntry logEntry) throws SVNException
    {
         //System.out.println(logEntry.getDate());
         list.add(logEntry);
    }
}
