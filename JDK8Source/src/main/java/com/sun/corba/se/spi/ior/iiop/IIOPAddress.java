/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.Writeable ;

/** IIOPAddress represents the host and port used to establish a
 * TCP connection for an IIOP request.
 * <p>
 *  用于IIOP请求的TCP连接。
 */
public interface IIOPAddress extends Writeable
{
    public String getHost() ;

    public int getPort() ;
}
