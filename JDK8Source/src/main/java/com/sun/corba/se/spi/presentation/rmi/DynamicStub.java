/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.presentation.rmi ;

import java.rmi.RemoteException ;

import org.omg.CORBA.portable.Delegate ;
import org.omg.CORBA.portable.OutputStream ;

import org.omg.CORBA.ORB ;

/** Interface used to support dynamically generated stubs.
 * This supplies some methods that are found in
 * org.omg.CORBA.portable.ObjectImpl that are not available
 * in org.omg.CORBA.Object.
 * <p>
 *  这提供了在org.omg.CORBA.portable.ObjectImpl中找到的一些方法,这些方法在org.omg.CORBA.Object中不可用。
 * 
 */
public interface DynamicStub extends org.omg.CORBA.Object
{
    /** Similar to ObjectImpl._set_delegate
    /* <p>
     */
    void setDelegate( Delegate delegate ) ;

    /** Similar to ObjectImpl._get_delegate
    /* <p>
     */
    Delegate getDelegate() ;

    /** Similar to ObjectImpl._orb()
    /* <p>
     */
    ORB getORB() ;

    /** Similar to ObjectImpl._ids
    /* <p>
     */
    String[] getTypeIds() ;

    /** Connect this dynamic stub to an ORB.
     * Just as in standard RMI-IIOP, this is required after
     * a dynamic stub is deserialized from an ObjectInputStream.
     * It is not needed when unmarshalling from a
     * org.omg.CORBA.portable.InputStream.
     * <p>
     *  与标准RMI-IIOP中一样,这是在从ObjectInputStream反序列化动态存根之后需要的。从org.omg.CORBA.portable.InputStream取消编组时不需要。
     */
    void connect( ORB orb ) throws RemoteException ;

    boolean isLocal() ;

    OutputStream request( String operation, boolean responseExpected ) ;
}
