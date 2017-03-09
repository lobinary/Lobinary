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
package org.omg.PortableServer;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.portable.Delegate;

/**
 * Defines the native <code>Servant</code> type. In Java, the
 * <code>Servant</code> type is mapped to the Java
 * <code>org.omg.PortableServer.Servant</code> class.
 * It serves as the base class for all POA servant
 * implementations and provides a number of methods that may
 * be invoked by the application programmer, as well as methods
 * which are invoked by the POA itself and may be overridden by
 * the user to control aspects of servant behavior.
 * Based on IDL to Java spec. (CORBA V2.3.1) ptc/00-01-08.pdf.
 * <p>
 *  定义本机<code> Servant </code>类型。
 * 在Java中,<code> Servant </code>类型映射到Java <code> org.omg.PortableServer.Servant </code>类。
 * 它作为所有POA servant实现的基类,并提供了可由应用程序员调用的许多方法,以及由POA本身调用并可被用户覆盖以控制服务方行为的方法。基于IDL到Java规范。
 *  (CORBA V2.3.1)ptc / 00-01-08.pdf。
 * 
 */

abstract public class Servant {

    private transient Delegate _delegate = null;
    /**
     * Gets the ORB vendor-specific implementation of
     * <code>PortableServer::Servant</code>.
     * <p>
     *  获取<code> PortableServer :: Servant </code>的ORB供应商特定实现。
     * 
     * 
     * @return <code>_delegate</code> the ORB vendor-specific
     * implementation of <code>PortableServer::Servant</code>.
     */
    final public Delegate _get_delegate() {
        if (_delegate == null) {
            throw
                new
                org.omg.CORBA.BAD_INV_ORDER
                ("The Servant has not been associated with an ORB instance");
        }
        return _delegate;
    }

    /**
     * Supports the Java ORB portability
     * interfaces by providing a method for classes that support
     * ORB portability through delegation to set their delegate.
     * <p>
     *  支持Java ORB可移植性接口,通过为通过委派设置其委托来支持ORB可移植性的类提供一个方法。
     * 
     * 
     * @param delegate ORB vendor-specific implementation of
     *                 the <code>PortableServer::Servant</code>.
     */
    final public void _set_delegate(Delegate delegate) {
        _delegate = delegate;
    }

    /**
     * Allows the servant to obtain the object reference for
     * the target CORBA object it is incarnating for that request.
     * <p>
     *  允许服务方获取其为该请求所体现的目标CORBA对象的对象引用。
     * 
     * 
     * @return <code>this_object</code> <code>Object</code> reference
     * associated with the request.
     */
    final public org.omg.CORBA.Object _this_object() {
        return _get_delegate().this_object(this);
    }

    /**
     * Allows the servant to obtain the object reference for
     * the target CORBA Object it is incarnating for that request.
     * <p>
     *  允许服务方获取其为该请求所体现的目标CORBA对象的对象引用。
     * 
     * 
     * @param orb ORB with which the servant is associated.
     * @return <code>_this_object</code> reference associated with the request.
     */
    final public org.omg.CORBA.Object _this_object(ORB orb) {
        try {
            ((org.omg.CORBA_2_3.ORB)orb).set_delegate(this);
        }
        catch(ClassCastException e) {
            throw
                new
                org.omg.CORBA.BAD_PARAM
                ("POA Servant requires an instance of org.omg.CORBA_2_3.ORB");
        }
        return _this_object();
    }

    /**
     * Returns the instance of the ORB
     * currently associated with the <code>Servant</code> (convenience method).
     * <p>
     *  返回当前与<code> Servant </code>(方便方法)相关联的ORB的实例。
     * 
     * 
     * @return <code>orb</code> the instance of the ORB currently
     * associated with the <code>Servant</code>.
     */
    final public ORB _orb() {
        return _get_delegate().orb(this);
    }

    /**
     * Allows easy execution of common methods, equivalent to
     * <code>PortableServer::Current:get_POA</code>.
     * <p>
     *  允许方便地执行常用方法,等效于<code> PortableServer :: Current：get_POA </code>。
     * 
     * 
     * @return <code>poa</code> POA associated with the servant.
     */
    final public POA _poa() {
        return _get_delegate().poa(this);
    }

    /**
     * Allows easy execution of
     * common methods, equivalent
     * to calling <code>PortableServer::Current::get_object_id</code>.
     * <p>
     *  允许方便地执行常用方法,相当于调用<code> PortableServer :: Current :: get_object_id </code>。
     * 
     * 
     * @return <code>object_id</code> the <code>Object</code> ID associated
     * with this servant.
     */
    final public byte[] _object_id() {
        return _get_delegate().object_id(this);
    }

    /**
     * Returns the
     * root POA from the ORB instance associated with the servant.
     * Subclasses may override this method to return a different POA.
     * <p>
     * 从与服务方关联的ORB实例返回根POA。子类可以覆盖此方法以返回不同的POA。
     * 
     * 
     * @return <code>default_POA</code> the POA associated with the
     * <code>Servant</code>.
     */
    public POA _default_POA() {
        return _get_delegate().default_POA(this);
    }

    /**
     * Checks to see if the specified <code>repository_id</code> is present
     * on the list returned by <code>_all_interfaces()</code> or is the
     * <code>repository_id</code> for the generic CORBA Object.
     * <p>
     *  检查以确定<code> _all_interfaces()</code>返回的列表中是否存在指定的<code> repository_id </code>,或者是通用CORBA对象的<code> re
     * pository_id </code>。
     * 
     * 
     * @param repository_id the <code>repository_id</code>
     *          to be checked in the repository list or against the id
     *          of generic CORBA objects.
     * @return <code>is_a</code> boolean indicating whether the specified
     *          <code>repository_id</code> is
     *         in the repository list or is same as a generic CORBA
     *         object.
     */
    public boolean _is_a(String repository_id) {
        return _get_delegate().is_a(this, repository_id);
    }

    /**
     * Checks for the existence of an
     * <code>Object</code>.
     * The <code>Servant</code> provides a default implementation of
     * <code>_non_existent()</code> that can be overridden by derived servants.
     * <p>
     *  检查是否存在<code> Object </code>。
     *  <code> Servant </code>提供了可以被派生服务器覆盖的<code> _non_existent()</code>的默认实现。
     * 
     * 
     * @return <code>non_existent</code> <code>true</code> if that object does
     *           not exist,  <code>false</code> otherwise.
     */
    public boolean _non_existent() {
        return _get_delegate().non_existent(this);
    }

    // Ken and Simon will ask about editorial changes
    // needed in IDL to Java mapping to the following
    // signature.
    /**
     * Returns an object in the Interface Repository
     * which provides type information that may be useful to a program.
     * <code>Servant</code> provides a default implementation of
     * <code>_get_interface()</code>
     * that can be overridden by derived servants if the default
     * behavior is not adequate.
     * <p>
     *  在Interface Repository中返回一个对象,该对象提供可能对程序有用的类型信息。
     *  <code> Servant </code>提供了<code> _get_interface()</code>的默认实现,如果默认行为不足,可以由派生服务器覆盖。
     * 
     * 
     * @return <code>get_interface</code> type information that corresponds to this servant.
     */
    /*
    public org.omg.CORBA.Object _get_interface() {
        return _get_delegate().get_interface(this);
    }
    /* <p>
    /*  public org.omg.CORBA.Object _get_interface(){return _get_delegate()。get_interface(this); }}
    /* 
    */

    // _get_interface_def() replaces the _get_interface() method

    /**
     * Returns an <code>InterfaceDef</code> object as a
     * <code>CORBA::Object</code> that defines the runtime type of the
     * <code>CORBA::Object</code> implemented by the <code>Servant</code>.
     * The invoker of <code>_get_interface_def</code>
     * must narrow the result to an <code>InterfaceDef</code> in order
     * to use it.
     * <P>This default implementation of <code>_get_interface_def()</code>
     * can be overridden
     * by derived servants if the default behavior is not adequate.
     * As defined in the CORBA 2.3.1 specification, section 11.3.1, the
     * default behavior of <code>_get_interface_def()</code> is to use
     * the most derived
     * interface of a static servant or the most derived interface retrieved
     * from a dynamic servant to obtain the <code>InterfaceDef</code>.
     * This behavior must
     * be supported by the <code>Delegate</code> that implements the
     * <code>Servant</code>.
     * <p>
     * 返回一个<code> InterfaceDef </code>对象作为定义<code> CORBA :: Object </code>的运行时类型的<code> CORBA :: Object </code>
     *  / code>。
     *  <code> _get_interface_def </code>的调用者必须将结果缩小到<code> InterfaceDef </code>以便使用它。
     *  <P>如果默认行为不足,派生服务器可以覆盖<code> _get_interface_def()</code>的默认实现。
     * 如CORBA 2.3.1规范第11.3.1节中所定义,<code> _get_interface_def()</code>的默认行为是使用静态服务方的最派生接口或从动态服务方检索的最派生接口以获取<code>
     *  InterfaceDef </code>。
     * 
     * @return <code>get_interface_def</code> an <code>InterfaceDef</code>
     * object as a
     * <code>CORBA::Object</code> that defines the runtime type of the
     * <code>CORBA::Object</code> implemented by the <code>Servant</code>.
     */
    public org.omg.CORBA.Object _get_interface_def()
    {
        // First try to call the delegate implementation class's
        // "Object get_interface_def(..)" method (will work for ORBs
        // whose delegates implement this method).
        // Else call the delegate implementation class's
        // "InterfaceDef get_interface(..)" method using reflection
        // (will work for ORBs that were built using an older version
        // of the Delegate interface with a get_interface method
        // but not a get_interface_def method).

        org.omg.PortableServer.portable.Delegate delegate = _get_delegate();
        try {
            // If the ORB's delegate class does not implement
            // "Object get_interface_def(..)", this will throw
            // an AbstractMethodError.
            return delegate.get_interface_def(this);
        } catch( AbstractMethodError aex ) {
            // Call "InterfaceDef get_interface(..)" method using reflection.
            try {
                Class[] argc = { org.omg.PortableServer.Servant.class };
                java.lang.reflect.Method meth =
                     delegate.getClass().getMethod("get_interface", argc);
                Object[] argx = { this };
                return (org.omg.CORBA.Object)meth.invoke(delegate, argx);
            } catch( java.lang.reflect.InvocationTargetException exs ) {
                Throwable t = exs.getTargetException();
                if (t instanceof Error) {
                    throw (Error) t;
                } else if (t instanceof RuntimeException) {
                    throw (RuntimeException) t;
                } else {
                    throw new org.omg.CORBA.NO_IMPLEMENT();
                }
            } catch( RuntimeException rex ) {
                throw rex;
            } catch( Exception exr ) {
                throw new org.omg.CORBA.NO_IMPLEMENT();
            }
        }
    }

    // methods for which the user must provide an
    // implementation
    /**
     * Used by the ORB to obtain complete type
     * information from the servant.
     * <p>
     *  <P>如果默认行为不足,派生服务器可以覆盖<code> _get_interface_def()</code>的默认实现。
     * 此行为必须由实现<code> Servant </code>的<code> Delegate </code>支持。
     * 
     * 
     * @param poa POA with which the servant is associated.
     * @param objectId is the id corresponding to the object
     *         associated with this servant.
     * @return list of type information for the object.
     */
    abstract public String[] _all_interfaces( POA poa, byte[] objectId);
}
