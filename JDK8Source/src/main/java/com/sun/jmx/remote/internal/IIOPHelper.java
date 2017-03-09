/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.remote.internal;

import java.util.Properties;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.NoSuchObjectException;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A helper class for RMI-IIOP and CORBA APIs.
 * <p>
 *  RMI-IIOP和CORBA API的助手类。
 * 
 */

public final class IIOPHelper {
    private IIOPHelper() { }

    // loads IIOPProxy implementation class if available
    private static final String IMPL_CLASS =
        "com.sun.jmx.remote.protocol.iiop.IIOPProxyImpl";
    private static final IIOPProxy proxy =
        AccessController.doPrivileged(new PrivilegedAction<IIOPProxy>() {
            public IIOPProxy run() {
                try {
                    Class<?> c = Class.forName(IMPL_CLASS, true,
                                               IIOPHelper.class.getClassLoader());
                    return (IIOPProxy)c.newInstance();
                } catch (ClassNotFoundException cnf) {
                    return null;
                } catch (InstantiationException e) {
                    throw new AssertionError(e);
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                }
            }});

    /**
     * Returns true if RMI-IIOP and CORBA is available.
     * <p>
     *  如果RMI-IIOP和CORBA可用,则返回true。
     * 
     */
    public static boolean isAvailable() {
        return proxy != null;
    }

    private static void ensureAvailable() {
        if (proxy == null)
            throw new AssertionError("Should not here");
    }

    /**
     * Returns true if the given object is a Stub.
     * <p>
     *  如果给定对象是存根,则返回true。
     * 
     */
    public static boolean isStub(Object obj) {
        return (proxy == null) ? false : proxy.isStub(obj);
    }

    /**
     * Returns the Delegate to which the given Stub delegates.
     * <p>
     *  返回给定Stub委派给的委托。
     * 
     */
    public static Object getDelegate(Object stub) {
        ensureAvailable();
        return proxy.getDelegate(stub);
    }

    /**
     * Sets the Delegate for a given Stub.
     * <p>
     *  为给定的存根设置代理。
     * 
     */
    public static void setDelegate(Object stub, Object delegate) {
        ensureAvailable();
        proxy.setDelegate(stub, delegate);
    }

    /**
     * Returns the ORB associated with the given stub
     *
     * <p>
     *  返回与给定存根相关联的ORB
     * 
     * 
     * @throws  UnsupportedOperationException
     *          if the object does not support the operation that
     *          was invoked
     */
    public static Object getOrb(Object stub) {
        ensureAvailable();
        return proxy.getOrb(stub);
    }

    /**
     * Connects the Stub to the given ORB.
     * <p>
     *  将Stub连接到给定的ORB。
     * 
     */
    public static void connect(Object stub, Object orb)
        throws IOException
    {
        if (proxy == null)
            throw new IOException("Connection to ORB failed, RMI/IIOP not available");
        proxy.connect(stub, orb);
    }

    /**
     * Returns true if the given object is an ORB.
     * <p>
     *  如果给定的对象是ORB,则返回true。
     * 
     */
    public static boolean isOrb(Object obj) {
        return (proxy == null) ? false : proxy.isOrb(obj);
    }

    /**
     * Creates, and returns, a new ORB instance.
     * <p>
     *  创建并返回一个新的ORB实例。
     * 
     */
    public static Object createOrb(String[] args, Properties props)
        throws IOException
    {
        if (proxy == null)
            throw new IOException("ORB initialization failed, RMI/IIOP not available");
        return proxy.createOrb(args, props);
    }

    /**
     * Converts a string, produced by the object_to_string method, back
     * to a CORBA object reference.
     * <p>
     *  将由object_to_string方法生成的字符串转换回CORBA对象引用。
     * 
     */
    public static Object stringToObject(Object orb, String str) {
        ensureAvailable();
        return proxy.stringToObject(orb, str);
    }

    /**
     * Converts the given CORBA object reference to a string.
     * <p>
     *  将给定的CORBA对象引用转换为字符串。
     * 
     */
    public static String objectToString(Object orb, Object obj) {
        ensureAvailable();
        return proxy.objectToString(orb, obj);
    }

    /**
     * Checks to ensure that an object of a remote or abstract interface
     * type can be cast to a desired type.
     * <p>
     *  检查以确保远程或抽象接口类型的对象可以转换为所需类型。
     * 
     */
    public static <T> T narrow(Object narrowFrom, Class<T> narrowTo) {
        ensureAvailable();
        return proxy.narrow(narrowFrom, narrowTo);
    }

    /**
     * Makes a server object ready to receive remote calls
     * <p>
     *  使服务器对象准备好接收远程调用
     * 
     */
    public static void exportObject(Remote obj) throws IOException {
        if (proxy == null)
            throw new IOException("RMI object cannot be exported, RMI/IIOP not available");
        proxy.exportObject(obj);
    }

    /**
     * Deregisters a server object from the runtime.
     * <p>
     *  从运行时注销一个服务器对象。
     * 
     */
    public static void unexportObject(Remote obj) throws IOException {
        if (proxy == null)
            throw new NoSuchObjectException("Object not exported");
        proxy.unexportObject(obj);
    }

    /**
     * Returns a stub for the given server object.
     * <p>
     *  返回给定服务器对象的存根。
     */
    public static Remote toStub(Remote obj) throws IOException {
        if (proxy == null)
            throw new NoSuchObjectException("Object not exported");
        return proxy.toStub(obj);
    }
}
