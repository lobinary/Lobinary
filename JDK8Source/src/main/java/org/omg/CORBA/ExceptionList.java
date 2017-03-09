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
 * An object used in <code>Request</code> operations to
 * describe the exceptions that can be thrown by a method.  It maintains a
 * modifiable list of <code>TypeCode</code>s of the exceptions.
 * <P>
 * The following code fragment demonstrates creating
 * an <code>ExceptionList</code> object:
 * <PRE>
 *    ORB orb = ORB.init(args, null);
 *    org.omg.CORBA.ExceptionList excList = orb.create_exception_list();
 * </PRE>
 * The variable <code>excList</code> represents an <code>ExceptionList</code>
 * object with no <code>TypeCode</code> objects in it.
 * <P>
 * To add items to the list, you first create a <code>TypeCode</code> object
 * for the exception you want to include, using the <code>ORB</code> method
 * <code>create_exception_tc</code>.  Then you use the <code>ExceptionList</code>
 * method <code>add</code> to add it to the list.
 * The class <code>ExceptionList</code> has a method for getting
 * the number of <code>TypeCode</code> objects in the list, and  after
 * items have been added, it is possible to call methods for accessing
 * or deleting an item at a designated index.
 *
 * <p>
 *  在<code> Request </code>操作中使用的对象,用于描述方法可抛出的异常。它维护一个可修改的<code> TypeCode </code>列表的异常。
 * <P>
 *  以下代码片段演示了如何创建一个<code> ExceptionList </code>对象：
 * <PRE>
 *  ORB orb = ORB.init(args,null); org.omg.CORBA.ExceptionList excList = orb.create_exception_list();
 * </PRE>
 *  变量<code> excList </code>表示其中没有<code> TypeCode </code>对象的<code> ExceptionList </code>对象。
 * <P>
 *  要向列表中添加项,首先使用<code> ORB </code>方法<code> create_exception_tc </code>为要包含的异常创建<code> TypeCode </code>对
 * 象。
 * 然后使用<code> ExceptionList </code>方法<code> add </code>将其添加到列表中。
 * 类<code> ExceptionList </code>具有用于获得列表中<code> TypeCode </code>对象的数量的方法,并且在添加项目之后,可以调用用于访问或删除项目的方法在指定索引
 * 。
 * 然后使用<code> ExceptionList </code>方法<code> add </code>将其添加到列表中。
 * 
 * @since   JDK1.2
 */

public abstract class ExceptionList {

    /**
     * Retrieves the number of <code>TypeCode</code> objects in this
     * <code>ExceptionList</code> object.
     *
     * <p>
     * 
     * 
     * @return          the     number of <code>TypeCode</code> objects in this
     * <code>ExceptionList</code> object
     */

    public abstract int count();

    /**
     * Adds a <code>TypeCode</code> object describing an exception
     * to this <code>ExceptionList</code> object.
     *
     * <p>
     *  撷取此<code> ExceptionList </code>物件中的<code> TypeCode </code>物件的数量。
     * 
     * 
     * @param exc                       the <code>TypeCode</code> object to be added
     */

    public abstract void add(TypeCode exc);

    /**
     * Returns the <code>TypeCode</code> object at the given index.  The first
     * item is at index 0.
     *
     * <p>
     *  向此<code> ExceptionList </code>对象添加一个描述异常的<code> TypeCode </code>对象。
     * 
     * 
     * @param index             the index of the <code>TypeCode</code> object desired.
     *                    This must be an <code>int</code> between 0 and the
     *                    number of <code>TypeCode</code> objects
     *                    minus one, inclusive.
     * @return                  the <code>TypeCode</code> object  at the given index
     * @exception org.omg.CORBA.Bounds   if the index given is greater than
     *                          or equal to the number of <code>TypeCode</code> objects
     *                in this <code>ExceptionList</code> object
     */

    public abstract TypeCode item(int index)
        throws org.omg.CORBA.Bounds;

    /**
     * Removes the <code>TypeCode</code> object at the given index.
     * Note that the indices of all the <code>TypeCoded</code> objects
     * following the one deleted are shifted down by one.
     *
     * <p>
     *  返回给定索引处的<code> TypeCode </code>对象。第一个项目在索引0。
     * 
     * 
     * @param index             the index of the <code>TypeCode</code> object to be
     *                    removed.
     *                    This must be an <code>int</code> between 0 and the
     *                    number of <code>TypeCode</code> objects
     *                    minus one, inclusive.
     *
     * @exception org.omg.CORBA.Bounds if the index is greater than
     *                          or equal to the number of <code>TypeCode</code> objects
     *                in this <code>ExceptionList</code> object
     */

    public abstract void remove(int index)
        throws org.omg.CORBA.Bounds;
}
