/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NoSuchObjectException;

/**
 * An interface to a subset of the RMI-IIOP and CORBA APIs to avoid a
 * static dependencies on the types defined by these APIs.
 * <p>
 *  与RMI-IIOP和CORBA API子集的接口,以避免对这些API定义的类型产生静态依赖。
 * 
 */

public interface IIOPProxy {

    /**
     * Returns true if the given object is a Stub.
     * <p>
     *  如果给定对象是存根,则返回true。
     * 
     */
    boolean isStub(Object obj);

    /**
     * Returns the Delegate to which the given Stub delegates.
     * <p>
     *  返回给定Stub委派给的委托。
     * 
     */
    Object getDelegate(Object stub);

    /**
     * Sets the Delegate for a given Stub.
     * <p>
     *  为给定的存根设置代理。
     * 
     */
    void setDelegate(Object stub, Object delegate);

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
    Object getOrb(Object stub);

    /**
     * Connects the Stub to the given ORB.
     * <p>
     *  将Stub连接到给定的ORB。
     * 
     */
    void connect(Object stub, Object orb) throws RemoteException;

    /**
     * Returns true if the given object is an ORB.
     * <p>
     *  如果给定的对象是ORB,则返回true。
     * 
     */
    boolean isOrb(Object obj);

    /**
     * Creates, and returns, a new ORB instance.
     * <p>
     *  创建并返回一个新的ORB实例。
     * 
     */
    Object createOrb(String[] args, Properties props);

    /**
     * Converts a string, produced by the object_to_string method, back
     * to a CORBA object reference.
     * <p>
     *  将由object_to_string方法生成的字符串转换回CORBA对象引用。
     * 
     */
    Object stringToObject(Object orb, String str);

    /**
     * Converts the given CORBA object reference to a string.
     * <p>
     *  将给定的CORBA对象引用转换为字符串。
     * 
     */
    String objectToString(Object orb, Object obj);

    /**
     * Checks to ensure that an object of a remote or abstract interface
     * type can be cast to a desired type.
     * <p>
     *  检查以确保远程或抽象接口类型的对象可以转换为所需类型。
     * 
     */
    <T> T narrow(Object narrowFrom, Class<T> narrowTo);

    /**
     * Makes a server object ready to receive remote calls
     * <p>
     *  使服务器对象准备好接收远程调用
     * 
     */
    void exportObject(Remote obj) throws RemoteException;

    /**
     * Deregisters a server object from the runtime.
     * <p>
     *  从运行时注销一个服务器对象。
     * 
     */
    void unexportObject(Remote obj) throws NoSuchObjectException;

    /**
     * Returns a stub for the given server object.
     * <p>
     *  返回给定服务器对象的存根。
     */
    Remote toStub(Remote obj) throws NoSuchObjectException;
}
