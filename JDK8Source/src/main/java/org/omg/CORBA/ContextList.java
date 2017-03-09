/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * An object containing a modifiable list of <code>String</code> objects
 * that represent property names.
 * This class is used in <code>Request</code> operations to
 * describe the contexts that need to be resolved and sent with the
 * invocation.  (A context is resolved by giving a property name
 * and getting back the value associated with it.)  This is done
 * by calling the <code>Context</code> method
 * <code>get_values</code> and supplying a string from a
 * <code>ContextList</code> object as the third parameter.
 * The method <code>get_values</code> returns an <code>NVList</code>
 * object containing the <code>NamedValue</code> objects that hold
 * the value(s) identified by the given string.
 * <P>
 * A <code>ContextList</code> object is created by the ORB, as
 * illustrated here:
 * <PRE>
 *   ORB orb = ORB.init(args, null);
 *   org.omg.CORBA.ContextList ctxList = orb.create_context_list();
 * </PRE>
 * The variable <code>ctxList</code> represents an empty
 * <code>ContextList</code> object.  Strings are added to
 * the list with the method <code>add</code>, accessed
 * with the method <code>item</code>, and removed with the
 * method <code>remove</code>.
 *
 * <p>
 *  包含表示属性名称的<code> String </code>对象的可修改列表的对象。此类在<code> Request </code>操作中用于描述需要解析并与调用一起发送的上下文。
 *  (通过提供一个属性名称并获取与之关联的值来解析上下文。
 * )这是通过调用<code> Context </code>方法<code> get_values </code>代码> ContextList </code>对象作为第三个参数。
 * 方法<code> get_values </code>返回包含保存由给定字符串标识的值的<code> NamedValue </code>对象的<code> NVList </code>对象。
 * <P>
 *  一个<code> ContextList </code>对象由ORB创建,如下所示：
 * <PRE>
 *  ORB orb = ORB.init(args,null); org.omg.CORBA.ContextList ctxList = orb.create_context_list();
 * </PRE>
 *  变量<code> ctxList </code>表示一个空的<code> ContextList </code>对象。
 * 使用方法<code> add </code>将字符串添加到列表中,使用方法<code> item </code>访问,并使用方法<code> remove </code>删除。
 * 
 * 
 * @see Context
 * @since   JDK1.2
 */

public abstract class ContextList {

    /**
     * Returns the number of <code>String</code> objects in this
     * <code>ContextList</code> object.
     *
     * <p>
     * 
     * @return                  an <code>int</code> representing the number of
     * <code>String</code>s in this <code>ContextList</code> object
     */

    public abstract int count();

    /**
     * Adds a <code>String</code> object to this <code>ContextList</code>
     * object.
     *
     * <p>
     *  返回<code> ContextList </code>对象中<code> String </code>对象的数量。
     * 
     * 
     * @param ctx               the <code>String</code> object to be added
     */

    public abstract void add(String ctx);

    /**
     * Returns the <code>String</code> object at the given index.
     *
     * <p>
     *  向此<code> ContextList </code>对象添加<code> String </code>对象。
     * 
     * 
     * @param index             the index of the string desired, with 0 being the
     index of the first string
     * @return                  the string at the given index
     * @exception org.omg.CORBA.Bounds  if the index is greater than
     *                          or equal to the number of strings in this
     *                <code>ContextList</code> object
     */

    public abstract String item(int index) throws org.omg.CORBA.Bounds;

    /**
     * Removes the <code>String</code> object at the given index. Note that
     * the indices of all strings following the one removed are
     * shifted down by one.
     *
     * <p>
     *  返回给定索引处的<code> String </code>对象。
     * 
     * 
     * @param index     the index of the <code>String</code> object to be removed,
     *                with 0 designating the first string
     * @exception org.omg.CORBA.Bounds  if the index is greater than
     *                          or equal to the number of <code>String</code> objects in
     *                this <code>ContextList</code> object
     */

    public abstract void remove(int index) throws org.omg.CORBA.Bounds;

}
