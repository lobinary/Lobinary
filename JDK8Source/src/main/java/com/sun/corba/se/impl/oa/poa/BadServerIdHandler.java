/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.oa.poa;

import com.sun.corba.se.spi.ior.ObjectKey;

/**
 * The bad server id handler is used to locate persistent objects.
 * The Locator object registers the BadServerIdHandler with the ORB
 * and when requests for persistent objects for servers (other than
 * itself) comes, it throws a ForwardException with the IOR pointing
 * to the active server.
 * <p>
 *  坏的服务器id处理程序用于定位持久化对象。
 *  Locator对象使用ORB注册BadServerIdHandler,当服务器(除了自身)的持久对象的请求到来时,它抛出一个ForwardException,IOR指向活动服务器。
 */
public interface BadServerIdHandler
{
    void handle(ObjectKey objectKey) ;
}
