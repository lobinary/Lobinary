/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.corba.se.impl.activation;

import java.util.*;
import com.sun.corba.se.impl.orbutil.ORBConstants;

/** ProcessMonitorThread is started when ServerManager is instantiated. The
  * thread wakes up every minute (This can be changed by setting sleepTime) and
  * makes sure that all the processes (Servers) registered with the ServerTool
  * are healthy. If not the state in ServerTableEntry will be changed to
  * De-Activated.
  * Note: This thread can be killed from the main thread by calling
  *       interrupThread()
  * <p>
  *  线程每分钟唤醒(这可以通过设置sleepTime更改),并确保注册到ServerTool的所有进程(服务器)都是正常的。
  * 如果不是,ServerTableEntry中的状态将更改为De-Activated。注意：这个线程可以通过调用interrupThread()从主线程中被杀死,。
  */
public class ProcessMonitorThread extends java.lang.Thread {
    private HashMap serverTable;
    private int sleepTime;
    private static ProcessMonitorThread instance = null;

    private ProcessMonitorThread( HashMap ServerTable, int SleepTime ) {
        serverTable = ServerTable;
        sleepTime = SleepTime;
    }

    public void run( ) {
        while( true ) {
            try {
                // Sleep's for a specified time, before checking
                // the Servers health. This will repeat as long as
                // the ServerManager (ORBD) is up and running.
                Thread.sleep( sleepTime );
            } catch( java.lang.InterruptedException e ) {
                break;
            }
            Iterator serverList;
            synchronized ( serverTable ) {
                // Check each ServerTableEntry to make sure that they
                // are in the right state.
                serverList = serverTable.values().iterator();
            }
            try {
                checkServerHealth( serverList );
            } catch( ConcurrentModificationException e ) {
                break;
            }
        }
    }

    private void checkServerHealth( Iterator serverList ) {
        if( serverList == null ) return;
        while (serverList.hasNext( ) ) {
            ServerTableEntry entry = (ServerTableEntry) serverList.next();
            entry.checkProcessHealth( );
        }
    }

    static void start( HashMap serverTable ) {
        int sleepTime = ORBConstants.DEFAULT_SERVER_POLLING_TIME;

        String pollingTime = System.getProperties().getProperty(
            ORBConstants.SERVER_POLLING_TIME );

        if ( pollingTime != null ) {
            try {
                sleepTime = Integer.parseInt( pollingTime );
            } catch (Exception e ) {
                // Too late to complain, Just use the default
                // sleepTime
            }
        }

        instance = new ProcessMonitorThread( serverTable,
            sleepTime );
        instance.setDaemon( true );
        instance.start();
    }

    static void interruptThread( ) {
        instance.interrupt();
    }
}
