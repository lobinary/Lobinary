/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
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
 * A modifiable list containing <code>NamedValue</code> objects.
 * <P>
 * The class <code>NVList</code> is used as follows:
 * <UL>
 * <LI>to describe arguments for a <code>Request</code> object
 * in the Dynamic Invocation Interface and
 * the Dynamic Skeleton Interface
 * <LI>to describe context values in a <code>Context</code> object
 * </UL>
 * <P>
 * Each <code>NamedValue</code> object consists of the following:
 * <UL>
 * <LI>a name, which is a <code>String</code> object
 * <LI>a value, as an <code>Any</code> object
 * <LI>an argument mode flag
 * </UL>
 * <P>
 * An <code>NVList</code> object
 * may be created using one of the following
 * <code>ORB</code> methods:
 * <OL>
 * <LI><code>org.omg.CORBA.ORB.create_list</code>
 * <PRE>
 *    org.omg.CORBA.NVList nv = orb.create_list(3);
 * </PRE>
 * The variable <code>nv</code> represents a newly-created
 * <code>NVList</code> object.  The argument is a memory-management
 * hint to the orb and does not imply the actual length of the list.
 * If, for example, you want to use an <code>NVList</code> object
 * in a request, and the method being invoked takes three parameters,
 * you might optimize by supplying 3 to the method
 * <code>create_list</code>.  Note that the new <code>NVList</code>
 * will not necessarily have a length of 3; it
 * could have a length of 2 or 4, for instance.
 * Note also that you can add any number of
 * <code>NamedValue</code> objects to this list regardless of
 * its original length.
 * <P>
 * <LI><code>org.omg.CORBA.ORB.create_operation_list</code>
 * <PRE>
 *    org.omg.CORBA.NVList nv = orb.create_operation_list(myOperationDef);
 * </PRE>
 * The variable <code>nv</code> represents a newly-created
 * <code>NVList</code> object that contains descriptions of the
 * arguments to the method described in the given
 * <code>OperationDef</code> object.
 * </OL>
 * <P>
 * The methods in the class <code>NVList</code> all deal with
 * the <code>NamedValue</code> objects in the list.
 * There are three methods for adding a <code>NamedValue</code> object,
 * a method for getting the count of <code>NamedValue</code> objects in
 * the list, a method for retrieving a <code>NamedValue</code> object
 * at a given index, and a method for removing a <code>NamedValue</code> object
 * at a given index.
 *
 * <p>
 *  包含<code> NamedValue </code>对象的可修改列表。
 * <P>
 *  类<code> NVList </code>使用如下：
 * <UL>
 *  <LI>描述动态调用接口和动态骨架接口<LI>中的<code> Request </code>对象的参数,以描述<code> Context </code>对象中的上下文值
 * </UL>
 * <P>
 *  每个<code> NamedValue </code>对象包含以下内容：
 * <UL>
 *  <LI>一个名称,它是一个<code> String </code>对象<li>一个值,作为一个<code> Any </code>对象<li>一个参数模式标志
 * </UL>
 * <P>
 *  可以使用以下<code> ORB </code>方法之一创建<code> NVList </code>对象：
 * <OL>
 *  <LI> <code> org.omg.CORBA.ORB.create_list </code>
 * <PRE>
 *  org.omg.CORBA.NVList nv = orb.create_list(3);
 * </PRE>
 *  变量<code> nv </code>表示新创建的<code> NVList </code>对象。参数是对orb的内存管理提示,并不意味着列表的实际长度。
 * 例如,如果要在请求中使用<code> NVList </code>对象,并且调用的方法需要三个参数,则可以通过向方法<code> create_list </code>提供3来进行优化。
 * 注意,新的<code> NVList </code>不一定具有长度3;它可以具有例如2或4的长度。
 * 还要注意,您可以向此列表中添加任意数量的<code> NamedValue </code>对象,而不考虑其原始长度。
 * <P>
 * <LI> <code> org.omg.CORBA.ORB.create_operation_list </code>
 * <PRE>
 *  org.omg.CORBA.NVList nv = orb.create_operation_list(myOperationDef);
 * </PRE>
 *  变量<code> nv </code>表示新创建的<code> NVList </code>对象,其包含给定<code> OperationDef </code>对象中描述的方法的参数的描述。
 * </OL>
 * <P>
 * 
 * @see org.omg.CORBA.Request
 * @see org.omg.CORBA.ServerRequest
 * @see org.omg.CORBA.NamedValue
 * @see org.omg.CORBA.Context
 *
 * @since       JDK1.2
 */

public abstract class NVList {

    /**
     * Returns the number of <code>NamedValue</code> objects that have
     * been added to this <code>NVList</code> object.
     *
     * <p>
     *  <code> NVList </code>类中的方法都处理列表中的<code> NamedValue </code>对象。
     * 有三种添加<code> NamedValue </code>对象的方法,一种用于获取列表中<code> NamedValue </code>对象计数的方法,一种用于检索<code> NamedValue
     *  </code>对象,以及用于在给定索引处移除<code> NamedValue </code>对象的方法。
     *  <code> NVList </code>类中的方法都处理列表中的<code> NamedValue </code>对象。
     * 
     * 
     * @return                  an <code>int</code> indicating the number of
     * <code>NamedValue</code> objects in this <code>NVList</code>.
     */

    public abstract int count();

    /**
     * Creates a new <code>NamedValue</code> object initialized with the given flag
     * and adds it to the end of this <code>NVList</code> object.
     * The flag can be any one of the argument passing modes:
     * <code>ARG_IN.value</code>, <code>ARG_OUT.value</code>, or
     * <code>ARG_INOUT.value</code>.
     *
     * <p>
     *  返回已添加到此<code> NVList </code>对象的<code> NamedValue </code>对象的数量。
     * 
     * 
     * @param flags             one of the argument mode flags
     * @return                  the newly-created <code>NamedValue</code> object
     */

    public abstract NamedValue add(int flags);

    /**
     * Creates a new <code>NamedValue</code> object initialized with the
     * given name and flag,
     * and adds it to the end of this <code>NVList</code> object.
     * The flag can be any one of the argument passing modes:
     * <code>ARG_IN.value</code>, <code>ARG_OUT.value</code>, or
     * <code>ARG_INOUT.value</code>.
     *
     * <p>
     *  创建用给定标志初始化的新<code> NamedValue </code>对象,并将其添加到此<code> NVList </code>对象的末尾。
     * 该标志可以是任何一种参数传递模式：<code> ARG_IN.value </code>,<code> ARG_OUT.value </code>或<code> ARG_INOUT.value </code>
     * 。
     *  创建用给定标志初始化的新<code> NamedValue </code>对象,并将其添加到此<code> NVList </code>对象的末尾。
     * 
     * 
     * @param item_name the name for the new <code>NamedValue</code> object
     * @param flags             one of the argument mode flags
     * @return                  the newly-created <code>NamedValue</code> object
     */

    public abstract NamedValue add_item(String item_name, int flags);

    /**
     * Creates a new <code>NamedValue</code> object initialized with the
     * given name, value, and flag,
     * and adds it to the end of this <code>NVList</code> object.
     *
     * <p>
     *  创建用给定名称和标志初始化的新<code> NamedValue </code>对象,并将其添加到此<code> NVList </code>对象的末尾。
     * 该标志可以是任何一种参数传递模式：<code> ARG_IN.value </code>,<code> ARG_OUT.value </code>或<code> ARG_INOUT.value </code>
     * 。
     *  创建用给定名称和标志初始化的新<code> NamedValue </code>对象,并将其添加到此<code> NVList </code>对象的末尾。
     * 
     * 
     * @param item_name the name for the new <code>NamedValue</code> object
     * @param val         an <code>Any</code> object containing the  value
     *                    for the new <code>NamedValue</code> object
     * @param flags       one of the following argument passing modes:
     *                    <code>ARG_IN.value</code>, <code>ARG_OUT.value</code>, or
     *                    <code>ARG_INOUT.value</code>
     * @return            the newly created <code>NamedValue</code> object
     */

    public abstract NamedValue add_value(String item_name, Any val, int flags);

    /**
     * Retrieves the <code>NamedValue</code> object at the given index.
     *
     * <p>
     * 创建用给定的名称,值和标志初始化的新的<code> NamedValue </code>对象,并将其添加到此<code> NVList </code>对象的末尾。
     * 
     * 
     * @param index             the index of the desired <code>NamedValue</code> object,
     *                    which must be between zero and the length of the list
     *                    minus one, inclusive.  The first item is at index zero.
     * @return                  the <code>NamedValue</code> object at the given index
     * @exception org.omg.CORBA.Bounds  if the index is greater than
     *                          or equal to number of <code>NamedValue</code> objects
     */

    public abstract NamedValue item(int index) throws org.omg.CORBA.Bounds;

    /**
     * Removes the <code>NamedValue</code> object at the given index.
     * Note that the indices of all <code>NamedValue</code> objects following
     * the one removed are shifted down by one.
     *
     * <p>
     *  检索给定索引处的<code> NamedValue </code>对象。
     * 
     * 
     * @param index             the index of the <code>NamedValue</code> object to be
     *                    removed, which must be between zero and the length
     *                    of the list minus one, inclusive.
     *                    The first item is at index zero.
     * @exception org.omg.CORBA.Bounds  if the index is greater than
     *                          or equal to number of <code>NamedValue</code> objects in
     *                the list
     */

    public abstract void remove(int index) throws org.omg.CORBA.Bounds;

}
