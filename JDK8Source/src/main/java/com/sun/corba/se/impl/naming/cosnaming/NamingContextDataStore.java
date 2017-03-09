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

package com.sun.corba.se.impl.naming.cosnaming;

// Import general CORBA classes
import org.omg.CORBA.Object;

// Import org.omg.CosNaming classes
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.BindingTypeHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.PortableServer.POA;

/**
 * This interface defines a set of methods that must be implemented by the
 * "data store" associated with a NamingContext implementation.
 * It allows for different implementations of naming contexts that
 * support the same API but differ in storage mechanism.
 * <p>
 *  此接口定义了一组必须由与NamingContext实现相关联的"数据存储"实现的方法。它允许命名上下文的不同实现,其支持相同的API但是在存储机制上不同。
 * 
 */
public interface NamingContextDataStore {
    /**
     * Method which implements binding a name to an object as
     * the specified binding type.
     * <p>
     *  实现将名称绑定到对象作为指定的绑定类型的方法。
     * 
     * 
     * @param n a NameComponent which is the name under which the object
     * will be bound.
     * @param obj the object reference to be bound.
     * @param bt Type of binding (as object or as context).
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
     */
    void Bind(NameComponent n, org.omg.CORBA.Object obj, BindingType bt)
        throws org.omg.CORBA.SystemException;

    /**
     * Method which implements resolving the specified name,
     * returning the type of the binding and the bound object reference.
     * If the id and kind of the NameComponent are both empty, the initial
     * naming context (i.e., the local root) must be returned.
     * <p>
     *  实现解析指定名称,返回绑定类型和绑定对象引用的方法。如果NameComponent的id和类型都为空,则必须返回初始命名上下文(即,本地根)。
     * 
     * 
     * @param n a NameComponent which is the name to be resolved.
     * @param bth the BindingType as an out parameter.
     * @return the object reference bound under the supplied name.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
     */
    org.omg.CORBA.Object Resolve(NameComponent n,BindingTypeHolder bth)
        throws org.omg.CORBA.SystemException;

    /**
     * Method which implements unbinding a name.
     * <p>
     *  实现解除绑定名称的方法。
     * 
     * 
     * @return the object reference bound to the name, or null if not found.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
     */
    org.omg.CORBA.Object Unbind(NameComponent n)
        throws org.omg.CORBA.SystemException;

    /**
     * Method which implements listing the contents of this
     * NamingContext and return a binding list and a binding iterator.
     * <p>
     *  实现列出此NamingContext的内容并返回绑定列表和绑定迭代器的方法。
     * 
     * 
     * @param how_many The number of requested bindings in the BindingList.
     * @param bl The BindingList as an out parameter.
     * @param bi The BindingIterator as an out parameter.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
     */
    void List(int how_many, BindingListHolder bl, BindingIteratorHolder bi)
        throws org.omg.CORBA.SystemException;

    /**
     * Method which implements creating a new NamingContext.
     * <p>
     *  实现创建一个新的NamingContext的方法。
     * 
     * 
     * @return an object reference for a new NamingContext object implemented
     * by this Name Server.
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
     */
    NamingContext NewContext()
        throws org.omg.CORBA.SystemException;

    /**
     * Method which implements destroying this NamingContext.
     * <p>
     *  实现销毁此NamingContext的方法。
     * 
     * 
     * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
     */
    void Destroy()
        throws org.omg.CORBA.SystemException;

    /**
     * Method which returns whether this NamingContext is empty
     * or not.
     * <p>
     *  返回此NamingContext是否为空的方法。
     * 
     * @return true if this NamingContext contains no bindings.
     */
    boolean IsEmpty();

    POA getNSPOA( );
}
