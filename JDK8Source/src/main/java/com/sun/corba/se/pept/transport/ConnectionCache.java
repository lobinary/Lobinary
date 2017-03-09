/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2010, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.pept.transport;

/**
/* <p>
/* 
 * @author Harold Carr
 */
public interface ConnectionCache
{
    public String getCacheType();

    public void stampTime(Connection connection);

    public long numberOfConnections();

    public long numberOfIdleConnections();

    public long numberOfBusyConnections();

    public boolean reclaim();

    /** Close all connections in the connection cache.
     * This is used as a final cleanup, and will result
     * in abrupt termination of any pending communications.
     * <p>
     *  这用作最终清除,并且将导致任何未决通信的突然终止。
     */
    public void close();
}

// End of file.
