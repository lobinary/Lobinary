/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性Copyright IBM Corp. 1998 1999保留所有权利
 * 
 */

package javax.rmi.CORBA;

import java.rmi.RemoteException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;

/**
 * Supports delegation for method implementations in {@link javax.rmi.PortableRemoteObject}.
 * The delegate is a singleton instance of a class that implements this
 * interface and provides a replacement implementation for all the
 * methods of <code>javax.rmi.PortableRemoteObject</code>.
 *
 * Delegates are enabled by providing the delegate's class name as the
 * value of the
 * <code>javax.rmi.CORBA.PortableRemoteObjectClass</code>
 * system property.
 *
 * <p>
 *  支持{@link javax.rmi.PortableRemoteObject}中的方法实现的委派。
 * 委托是一个实现这个接口的类的单例实例,并为<code> javax.rmi.PortableRemoteObject </code>的所有方法提供替换实现。
 * 
 *  通过将代理的类名称作为<code> javax.rmi.CORBA.PortableRemoteObjectClass </code>系统属性的值来启用代理。
 * 
 * 
 * @see javax.rmi.PortableRemoteObject
 */
public interface PortableRemoteObjectDelegate {

    /**
     * Delegation call for {@link javax.rmi.PortableRemoteObject#exportObject}.
     * <p>
     *  委托调用{@link javax.rmi.PortableRemoteObject#exportObject}。
     * 
     */
    void exportObject(Remote obj)
        throws RemoteException;

    /**
     * Delegation call for {@link javax.rmi.PortableRemoteObject#toStub}.
     * <p>
     *  委托调用{@link javax.rmi.PortableRemoteObject#toStub}。
     * 
     */
    Remote toStub (Remote obj)
        throws NoSuchObjectException;

    /**
     * Delegation call for {@link javax.rmi.PortableRemoteObject#unexportObject}.
     * <p>
     *  委托调用{@link javax.rmi.PortableRemoteObject#unexportObject}。
     * 
     */
    void unexportObject(Remote obj)
        throws NoSuchObjectException;

    /**
     * Delegation call for {@link javax.rmi.PortableRemoteObject#narrow}.
     * <p>
     *  委托调用{@link javax.rmi.PortableRemoteObject#narrow}。
     * 
     */
    java.lang.Object narrow (java.lang.Object narrowFrom,
                                    java.lang.Class narrowTo)
        throws ClassCastException;

    /**
     * Delegation call for {@link javax.rmi.PortableRemoteObject#connect}.
     * <p>
     *  委托调用{@link javax.rmi.PortableRemoteObject#connect}。
     */
    void connect (Remote target, Remote source)
        throws RemoteException;

}
