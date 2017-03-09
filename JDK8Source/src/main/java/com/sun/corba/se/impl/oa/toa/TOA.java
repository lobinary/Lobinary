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

package com.sun.corba.se.impl.oa.toa ;

import com.sun.corba.se.spi.oa.ObjectAdapter ;

/** The Transient Object Adapter is used for standard RMI-IIOP and Java-IDL
 * (legacy JDK 1.2) object implementations.  Its protocol for managing objects is very
 * simple: just connect and disconnect.  There is only a single TOA instance per ORB,
 * and its lifetime is the same as the ORB.  The TOA instance is always ready to receive
 * messages except when the ORB is shutting down.
 * <p>
 *  (legacy JDK 1.2)对象实现。其管理对象的协议非常简单：只是连接和断开连接。每个ORB只有一个TOA实例,其生存期与ORB相同。 TOA实例总是准备好接收消息,除非ORB正在关闭。
 * 
 */
public interface TOA extends ObjectAdapter {
    /** Connect the given servant to the ORB by allocating a transient object key
     *  and creating an IOR and object reference using the current factory.
     * <p>
     *  并使用当前工厂创建IOR和对象引用。
     * 
     */
    void connect( org.omg.CORBA.Object servant ) ;

    /** Disconnect the object from this ORB.
    /* <p>
    */
    void disconnect( org.omg.CORBA.Object obj ) ;
}
