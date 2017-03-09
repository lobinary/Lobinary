/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Set;

import com.sun.corba.se.pept.protocol.ClientRequestDispatcher ;
import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher ;
import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcherFactory ;

import com.sun.corba.se.spi.oa.ObjectAdapterFactory ;

/**
 * This is a registry of all subcontract ID dependent objects.  This includes:
 * LocalClientRequestDispatcherFactory, ClientRequestDispatcher, ServerRequestDispatcher, and
 * ObjectAdapterFactory.
 * <p>
 *  这是所有外包ID依赖对象的注册表。
 * 这包括：LocalClientRequestDispatcherFactory,ClientRequestDispatcher,ServerRequestDispatcher和ObjectAdapter
 * Factory。
 *  这是所有外包ID依赖对象的注册表。
 * 
 */
public interface RequestDispatcherRegistry {

    /** Register a ClientRequestDispatcher for a particular subcontract ID.
     * The subcontract ID appears in the ObjectKey of an object reference, and is used
     * to control how a remote method invocation is processed by the ORB for a
     * particular kind of object reference.
     * <p>
     *  外包ID显示在对象引用的ObjectKey中,用于控制ORB如何为特定类型的对象引用处理远程方法调用。
     * 
     */
    void registerClientRequestDispatcher( ClientRequestDispatcher csc, int scid) ;

    /** Get the ClientRequestDispatcher for subcontract ID scid.
    /* <p>
     */
    ClientRequestDispatcher getClientRequestDispatcher( int scid ) ;

    /** Register a LocalClientRequestDispatcher for a particular subcontract ID.
     * The subcontract ID appears in the ObjectKey of an object reference, and is used
     * to control how a particular kind of colocated request is processed.
     * <p>
     *  外包ID出现在对象引用的ObjectKey中,用于控制如何处理特定类型的共同定位的请求。
     * 
     */
    void registerLocalClientRequestDispatcherFactory( LocalClientRequestDispatcherFactory csc, int scid) ;

    /** Get the LocalClientRequestDispatcher for subcontract ID scid.
    /* <p>
     */
    LocalClientRequestDispatcherFactory getLocalClientRequestDispatcherFactory( int scid ) ;

    /** Register a CorbaServerRequestDispatcher for a particular subcontract ID.
     * The subcontract ID appears in the ObjectKey of an object reference, and is used
     * to control how a particular kind of request is processed when received by the ORB.
     * <p>
     *  子合同ID显示在对象引用的ObjectKey中,用于控制ORB接收时如何处理特定类型的请求。
     * 
     */
    void registerServerRequestDispatcher( CorbaServerRequestDispatcher ssc, int scid) ;

    /** Get the CorbaServerRequestDispatcher for subcontract ID scid.
    /* <p>
     */
    CorbaServerRequestDispatcher getServerRequestDispatcher(int scid) ;

    /** Register a CorbaServerRequestDispatcher for handling an explicit object key name.
     * This is used for non-standard invocations such as INS and the bootstrap name service.
     * <p>
     *  这用于非标准调用(如INS和引导名称服务)。
     * 
     */
    void registerServerRequestDispatcher( CorbaServerRequestDispatcher ssc, String name ) ;

    /** Get the CorbaServerRequestDispatcher for a particular object key.
    /* <p>
     */
    CorbaServerRequestDispatcher getServerRequestDispatcher( String name ) ;

    /** Register an ObjectAdapterFactory for a particular subcontract ID.
     * This controls how Object references are created and managed.
     * <p>
     *  这控制如何创建和管理对象引用。
     * 
     */
    void registerObjectAdapterFactory( ObjectAdapterFactory oaf, int scid) ;

    /** Get the ObjectAdapterFactory for a particular subcontract ID scid.
    /* <p>
     */
    ObjectAdapterFactory getObjectAdapterFactory( int scid ) ;

    /** Return the set of all ObjectAdapterFactory instances that are registered.
    /* <p>
     */
    Set<ObjectAdapterFactory> getObjectAdapterFactories();
}
