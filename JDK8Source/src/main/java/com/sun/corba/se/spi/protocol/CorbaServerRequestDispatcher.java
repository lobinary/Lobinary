/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.protocol;

import com.sun.corba.se.pept.protocol.ServerRequestDispatcher;

import com.sun.corba.se.spi.ior.ObjectKey;

// XXX These must all be replaced by Sun private APIs.
import com.sun.corba.se.spi.ior.IOR ;

/**
 * Server delegate adds behavior on the server-side -- specifically
 * on the dispatch path. A single server delegate instance serves
 * many server objects.  This is the second level of the dispatch
 * on the server side: Acceptor to ServerSubcontract to ServerRequestDispatcher to
 * ObjectAdapter to Servant, although this may be short-circuited.
 * Instances of this class are registered in the subcontract Registry.
 * <p>
 *  服务器代理在服务器端添加行为 - 特别是在调度路径上。单个服务器委托实例提供多个服务器对象。
 * 这是服务器端分派的第二级：ServerSubcontractor ServerRequestDispatcher to ObjectAdapter to Servant的接受者,尽管这可能是短路的。
 * 此类的实例在转包注册中注册。
 * 
 */
public interface CorbaServerRequestDispatcher
    extends ServerRequestDispatcher
{
    /**
     * Handle a locate request.
     * <p>
     */
    public IOR locate(ObjectKey key);
}

// End of file.
