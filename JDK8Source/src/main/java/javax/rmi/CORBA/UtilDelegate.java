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

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.ValueHandler;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.SystemException;

/**
 * Supports delegation for method implementations in {@link Util}.  The
 * delegate is a singleton instance of a class that implements this
 * interface and provides a replacement implementation for all the
 * methods of <code>javax.rmi.CORBA.Util</code>.
 *
 * Delegation is enabled by providing the delegate's class name as the
 * value of the
 * <code>javax.rmi.CORBA.UtilClass</code>
 * system property.
 *
 * <p>
 *  支持{@link Util}中的方法实现的委派。委托是实现此接口的类的单例实例,并为<code> javax.rmi.CORBA.Util </code>的所有方法提供替换实现。
 * 
 *  通过将代理的类名作为<code> javax.rmi.CORBA.UtilClass </code>系统属性的值提供来启用委派。
 * 
 * 
 * @see Util
 */
public interface UtilDelegate {

    /**
     * Delegation call for {@link Util#mapSystemException}.
     * <p>
     *  委派调用{@link Util#mapSystemException}。
     * 
     */
    RemoteException mapSystemException(SystemException ex);

    /**
     * Delegation call for {@link Util#writeAny}.
     * <p>
     *  委托调用{@link Util#writeAny}。
     * 
     */
    void writeAny(OutputStream out, Object obj);

    /**
     * Delegation call for {@link Util#readAny}.
     * <p>
     *  委托调用{@link Util#readAny}。
     * 
     */
    java.lang.Object readAny(InputStream in);

    /**
     * Delegation call for {@link Util#writeRemoteObject}.
     * <p>
     *  委派调用{@link Util#writeRemoteObject}。
     * 
     */
    void writeRemoteObject(OutputStream out, Object obj);

    /**
     * Delegation call for {@link Util#writeAbstractObject}.
     * <p>
     *  委托调用{@link Util#writeAbstractObject}。
     * 
     */
    void writeAbstractObject(OutputStream out, Object obj);

    /**
     * Delegation call for {@link Util#registerTarget}.
     * <p>
     *  委托调用{@link Util#registerTarget}。
     * 
     */
    void registerTarget(Tie tie, Remote target);

    /**
     * Delegation call for {@link Util#unexportObject}.
     * <p>
     *  委派调用{@link Util#unexportObject}。
     * 
     */
    void unexportObject(Remote target) throws java.rmi.NoSuchObjectException;

    /**
     * Delegation call for {@link Util#getTie}.
     * <p>
     *  委托调用{@link Util#getTie}。
     * 
     */
    Tie getTie(Remote target);

    /**
     * Delegation call for {@link Util#createValueHandler}.
     * <p>
     *  委派调用{@link Util#createValueHandler}。
     * 
     */
    ValueHandler createValueHandler();

    /**
     * Delegation call for {@link Util#getCodebase}.
     * <p>
     *  委托调用{@link Util#getCodebase}。
     * 
     */
    String getCodebase(Class clz);

    /**
     * Delegation call for {@link Util#loadClass}.
     * <p>
     *  委托调用{@link Util#loadClass}。
     * 
     */
    Class loadClass(String className, String remoteCodebase, ClassLoader loader)
        throws ClassNotFoundException;

    /**
     * Delegation call for {@link Util#isLocal}.
     * <p>
     *  委托调用{@link Util#isLocal}。
     * 
     */
    boolean isLocal(Stub stub) throws RemoteException;

    /**
     * Delegation call for {@link Util#wrapException}.
     * <p>
     *  委托调用{@link Util#wrapException}。
     * 
     */
    RemoteException wrapException(Throwable obj);

    /**
     * Delegation call for {@link Util#copyObject}.
     * <p>
     *  委托调用{@link Util#copyObject}。
     * 
     */
    Object copyObject(Object obj, ORB orb) throws RemoteException;

    /**
     * Delegation call for {@link Util#copyObjects}.
     * <p>
     *  委托调用{@link Util#copyObjects}。
     */
    Object[] copyObjects(Object[] obj, ORB orb) throws RemoteException;

}
