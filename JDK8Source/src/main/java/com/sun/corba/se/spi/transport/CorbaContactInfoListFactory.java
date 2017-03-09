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

package com.sun.corba.se.spi.transport ;

import com.sun.corba.se.spi.transport.CorbaContactInfoList ;

import com.sun.corba.se.spi.ior.IOR ;
import com.sun.corba.se.spi.orb.ORB;

/** Interface used to create a ContactInfoList from an IOR, as required
 * for supporting CORBA semantics using the DCS framework.  This is a
 * natural correspondence since an IOR contains the information for
 * contacting one or more communication endpoints that can be used to
 * invoke a method on an object, along with the necessary information
 * on particular transports, encodings, and protocols to use.
 * Note that the actual implementation may support more than one
 * IOR in the case of GIOP with Location Forward messages.
 * <p>
 *  以支持使用DCS框架的CORBA语义。这是自然对应,因为IOR包含用于联系一个或多个通信端点的信息,该通信端点可以用于调用对象上的方法,以及关于特定传输,编码和使用协议的必要信息。
 * 注意,在具有位置转发消息的GIOP的情况下,实际实现可以支持多于一个IOR。
 * 
 */
public interface CorbaContactInfoListFactory {
    /**
     * This will be called after the no-arg constructor before
     * create is called.
     * <p>
     */
    public void setORB(ORB orb);

    public CorbaContactInfoList create( IOR ior ) ;
}
