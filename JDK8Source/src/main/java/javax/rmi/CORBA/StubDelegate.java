/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2001, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import org.omg.CORBA.ORB;

/**
 * Supports delegation for method implementations in {@link Stub}.
 * A delegate is an instance of a class that implements this
 * interface and provides a replacement implementation for all the
 * methods of <code>javax.rmi.CORBA.Stub</code>.  If delegation is
 * enabled, each stub has an associated delegate.
 *
 * Delegates are enabled by providing the delegate's class name as the
 * value of the
 * <code>javax.rmi.CORBA.StubClass</code>
 * system property.
 *
 * <p>
 *  支持{@link Stub}中方法实现的委派。委托是实现此接口的类的实例,并为<code> javax.rmi.CORBA.Stub </code>的所有方法提供替换实现。
 * 如果启用委派,每个存根都有一个关联的委托。
 * 
 *  通过将代理的类名称作为<code> javax.rmi.CORBA.StubClass </code>系统属性的值来启用代理。
 * 
 * 
 * @see Stub
 */
public interface StubDelegate {

    /**
     * Delegation call for {@link Stub#hashCode}.
     * <p>
     *  委托调用{@link Stub#hashCode}。
     * 
     */
    int hashCode(Stub self);

    /**
     * Delegation call for {@link Stub#equals}.
     * <p>
     *  委托调用{@link Stub#equals}。
     * 
     */
    boolean equals(Stub self, java.lang.Object obj);

    /**
     * Delegation call for {@link Stub#toString}.
     * <p>
     *  委托调用{@link Stub#toString}。
     * 
     */
    String toString(Stub self);

    /**
     * Delegation call for {@link Stub#connect}.
     * <p>
     *  委托调用{@link Stub#connect}。
     * 
     */
    void connect(Stub self, ORB orb)
        throws RemoteException;

    // _REVISIT_ cannot link to Stub.readObject directly... why not?
    /**
     * Delegation call for
     * <a href="{@docRoot}/serialized-form.html#javax.rmi.CORBA.Stub"><code>Stub.readObject(java.io.ObjectInputStream)</code></a>.
     * <p>
     *  委派调用<a href="{@docRoot}/serialized-form.html#javax.rmi.CORBA.Stub"> <code> Stub.readObject(java.io.O
     * bjectInputStream)</code> </a>。
     * 
     */
    void readObject(Stub self, ObjectInputStream s)
        throws IOException, ClassNotFoundException;

    // _REVISIT_ cannot link to Stub.writeObject directly... why not?
    /**
     * Delegation call for
     * <a href="{@docRoot}/serialized-form.html#javax.rmi.CORBA.Stub"><code>Stub.writeObject(java.io.ObjectOutputStream)</code></a>.
     * <p>
     *  委派调用<a href="{@docRoot}/serialized-form.html#javax.rmi.CORBA.Stub"> <code> Stub.writeObject(java.io.
     * ObjectOutputStream)</code> </a>。
     */
    void writeObject(Stub self, ObjectOutputStream s)
        throws IOException;

}
