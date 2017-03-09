/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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


/** Enables <tt>org.omg.CORBA.Any</tt> values to be dynamically
 * interpreted (traversed) and
 *  constructed. A <tt>DynAny</tt> object is associated with a data value
 *  which may correspond to a copy of the value inserted into an <tt>Any</tt>.
 *  The <tt>DynAny</tt> APIs enable traversal of the data value associated with an
 *  Any at runtime and extraction of the primitive constituents of the
 *  data value.
 * <p>
 *  解释(遍历)和构造。 <tt> DynAny </tt>对象与可能对应于插入到<tt>任何</tt>中的值的副本的数据值相关联。
 *  <tt> DynAny </tt> API允许在运行时遍历与Any相关联的数据值,并提取数据值的基本组成部分。
 * 
 * 
 * @deprecated Use the new <a href="../DynamicAny/DynAny.html">DynAny</a> instead
 */
@Deprecated
public interface DynAny extends org.omg.CORBA.Object
{
    /**
     * Returns the <code>TypeCode</code> of the object inserted into
     * this <code>DynAny</code>.
     *
     * <p>
     *  返回插入到此<code> DynAny </code>中的对象的<code> TypeCode </code>。
     * 
     * 
     * @return the <code>TypeCode</code> object.
     */
    public org.omg.CORBA.TypeCode type() ;

    /**
     * Copy the contents from one Dynamic Any into another.
     *
     * <p>
     *  将内容从一个动态任何复制到另一个。
     * 
     * 
     * @param dyn_any the <code>DynAny</code> object whose contents
     *                are assigned to this <code>DynAny</code>.
     * @throws Invalid if the source <code>DynAny</code> is
     *            invalid
     */
    public void assign(org.omg.CORBA.DynAny dyn_any)
        throws org.omg.CORBA.DynAnyPackage.Invalid;

    /**
     * Make a <code>DynAny</code> object from an <code>Any</code>
     * object.
     *
     * <p>
     *  从<code> Any </code>对象中创建<code> DynAny </code>对象。
     * 
     * 
     * @param value the <code>Any</code> object.
     * @throws Invalid if the source <code>Any</code> object is
     *                    empty or bad
     */
    public void from_any(org.omg.CORBA.Any value)
        throws org.omg.CORBA.DynAnyPackage.Invalid;

    /**
     * Convert a <code>DynAny</code> object to an <code>Any</code>
     * object.
     *
     * <p>
     *  将<code> DynAny </code>对象转换为<code> Any </code>对象。
     * 
     * 
     * @return the <code>Any</code> object.
     * @throws Invalid if this <code>DynAny</code> is empty or
     *                    bad.
     *            created or does not contain a meaningful value
     */
    public org.omg.CORBA.Any to_any()
        throws org.omg.CORBA.DynAnyPackage.Invalid;

    /**
     * Destroys this <code>DynAny</code> object and frees any resources
     * used to represent the data value associated with it. This method
     * also destroys all <code>DynAny</code> objects obtained from it.
     * <p>
     * Destruction of <code>DynAny</code> objects should be handled with
     * care, taking into account issues dealing with the representation of
     * data values associated with <code>DynAny</code> objects.  A programmer
     * who wants to destroy a <code>DynAny</code> object but still be able
     * to manipulate some component of the data value associated with it,
     * should first create a <code>DynAny</code> object for the component
     * and then make a copy of the created <code>DynAny</code> object.
     * <p>
     *  销毁此<code> DynAny </code>对象并释放用于表示与其相关联的数据值的任何资源。这种方法还会破坏从它获得的所有<code> DynAny </code>对象。
     * <p>
     *  考虑到与处理与<code> DynAny </code>对象相关联的数据值的表示的问题,应谨慎处理<code> DynAny </code>对象的销毁。
     * 一个想要销毁一个<code> DynAny </code>对象但仍然能够处理与其相关联的数据值的某个组件的程序员应该首先为该组件创建一个<code> DynAny </code>对象,然后创建所创建的<code>
     *  DynAny </code>对象的副本。
     *  考虑到与处理与<code> DynAny </code>对象相关联的数据值的表示的问题,应谨慎处理<code> DynAny </code>对象的销毁。
     * 
     */
    public void destroy() ;

    /**
     * Clones this <code>DynAny</code> object.
     *
     * <p>
     *  克隆此<code> DynAny </code>对象。
     * 
     * 
     * @return a copy of this <code>DynAny</code> object
     */
    public org.omg.CORBA.DynAny copy() ;

    /**
     * Inserts the given <code>boolean</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     * 插入给定的<code> boolean </code>作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>boolean</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_boolean(boolean value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>byte</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code>字节</code>作为<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>byte</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_octet(byte value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>char</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> char </code>作为<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>char</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_char(char value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>short</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> short </code>作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>short</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_short(short value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>short</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> short </code>作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>short</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_ushort(short value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>int</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> int </code>作为<code> DynAny </code>对象的值。
     * 
     * <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>int</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_long(int value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>int</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> int </code>作为<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>int</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_ulong(int value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>float</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> float </code>作为<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>float</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_float(float value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>double</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> double </code>作为<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>double</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_double(double value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>String</code> object as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> String </code>对象作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>String</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_string(String value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>org.omg.CORBA.Object</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> org.omg.CORBA.Object </code>作为此<code> DynAny </code>对象的值。
     * 
     * <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>org.omg.CORBA.Object</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_reference(org.omg.CORBA.Object value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>org.omg.CORBA.TypeCode</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> org.omg.CORBA.TypeCode </code>作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>org.omg.CORBA.TypeCode</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_typecode(org.omg.CORBA.TypeCode value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>long</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> long </code>作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>long</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_longlong(long value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>long</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> long </code>作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>long</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_ulonglong(long value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>char</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> char </code>作为<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>char</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_wchar(char value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>String</code> as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> String </code>作为此<code> DynAny </code>对象的值。
     * 
     * <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>String</code> to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_wstring(String value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Inserts the given <code>org.omg.CORBA.Any</code> object as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> org.omg.CORBA.Any </code>对象作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>org.omg.CORBA.Any</code> object to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_any(org.omg.CORBA.Any value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    // orbos 98-01-18: Objects By Value -- begin

    /**
     * Inserts the given <code>java.io.Serializable</code> object as the value for this
     * <code>DynAny</code> object.
     *
     * <p> If this method is called on a constructed <code>DynAny</code>
     * object, it initializes the next component of the constructed data
     * value associated with this <code>DynAny</code> object.
     *
     * <p>
     *  插入给定的<code> java.io.Serializable </code>对象作为此<code> DynAny </code>对象的值。
     * 
     *  <p>如果在构造的<code> DynAny </code>对象上调用此方法,它将初始化与此<code> DynAny </code>对象关联的构造数据值的下一个组件。
     * 
     * 
     * @param value the <code>java.io.Serializable</code> object to insert into this
     *              <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.InvalidValue
     *            if the value inserted is not consistent with the type
     *            of the accessed component in this <code>DynAny</code> object
     */
    public void insert_val(java.io.Serializable value)
        throws org.omg.CORBA.DynAnyPackage.InvalidValue;

    /**
     * Retrieves the <code>java.io.Serializable</code> object contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> java.io.Serializable </code>对象。
     * 
     * 
     * @return the <code>java.io.Serializable</code> object that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>java.io.Serializable</code> object
     */
    public java.io.Serializable get_val()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;

    // orbos 98-01-18: Objects By Value -- end

    /**
     * Retrieves the <code>boolean</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> boolean </code>。
     * 
     * 
     * @return the <code>boolean</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>boolean</code>
     */
    public boolean get_boolean()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>byte</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code>字节</code>。
     * 
     * 
     * @return the <code>byte</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>byte</code>
     */
    public byte get_octet()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;

    /**
     * Retrieves the <code>char</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> char </code>。
     * 
     * 
     * @return the <code>char</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>char</code>
     */
    public char get_char()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>short</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> short </code>。
     * 
     * 
     * @return the <code>short</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>short</code>
     */
    public short get_short()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>short</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> short </code>。
     * 
     * 
     * @return the <code>short</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>short</code>
     */
    public short get_ushort()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>int</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> int </code>。
     * 
     * 
     * @return the <code>int</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>int</code>
     */
    public int get_long()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>int</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> int </code>。
     * 
     * 
     * @return the <code>int</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>int</code>
     */
    public int get_ulong()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>float</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     * 检索此<code> DynAny </code>对象中包含的<code> float </code>。
     * 
     * 
     * @return the <code>float</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>float</code>
     */
    public float get_float()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>double</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> double </code>。
     * 
     * 
     * @return the <code>double</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>double</code>
     */
    public double get_double()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>String</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> String </code>。
     * 
     * 
     * @return the <code>String</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>String</code>
     */
    public String get_string()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>org.omg.CORBA.Other</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> org.omg.CORBA.Other </code>。
     * 
     * 
     * @return the <code>org.omg.CORBA.Other</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for an <code>org.omg.CORBA.Other</code>
     */
    public org.omg.CORBA.Object get_reference()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>org.omg.CORBA.TypeCode</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> org.omg.CORBA.TypeCode </code>。
     * 
     * 
     * @return the <code>org.omg.CORBA.TypeCode</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>org.omg.CORBA.TypeCode</code>
     */
    public org.omg.CORBA.TypeCode get_typecode()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>long</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> long </code>。
     * 
     * 
     * @return the <code>long</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>long</code>
     */
    public long get_longlong()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>long</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> long </code>。
     * 
     * 
     * @return the <code>long</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>long</code>
     */
    public long get_ulonglong()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>char</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> char </code>。
     * 
     * 
     * @return the <code>char</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>char</code>
     */
    public char get_wchar()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>String</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> String </code>。
     * 
     * 
     * @return the <code>String</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for a <code>String</code>
     */
    public String get_wstring()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;


    /**
     * Retrieves the <code>org.omg.CORBA.Any</code> contained
     * in this <code>DynAny</code> object.
     *
     * <p>
     *  检索此<code> DynAny </code>对象中包含的<code> org.omg.CORBA.Any </code>。
     * 
     * 
     * @return the <code>org.omg.CORBA.Any</code> that is the
     *         value for this <code>DynAny</code> object
     * @throws org.omg.CORBA.DynAnyPackage.TypeMismatch
     *               if the type code of the accessed component in this
     *               <code>DynAny</code> object is not equivalent to
     *               the type code for an <code>org.omg.CORBA.Any</code>
     */
    public org.omg.CORBA.Any get_any()
        throws org.omg.CORBA.DynAnyPackage.TypeMismatch;

    /**
     * Returns a <code>DynAny</code> object reference that can
     * be used to get/set the value of the component currently accessed.
     * The appropriate <code>insert</code> method
     * can be called on the resulting <code>DynAny</code> object
     * to initialize the component.
     * The appropriate <code>get</code> method
     * can be called on the resulting <code>DynAny</code> object
     * to extract the value of the component.
         *
         * <p>
         *  返回一个<code> DynAny </code>对象引用,可用于获取/设置当前访问的组件的值。
         * 可以在生成的<code> DynAny </code>对象上调用适当的<code> insert </code>方法来初始化组件。
         * 可以在生成的<code> DynAny </code>对象上调用适当的<code> get </code>方法来提取组件的值。
         * 
         * 
         * @return a <code>DynAny</code> object reference that can be
         *         used to retrieve or set the value of the component currently
         *         accessed
     */
    public org.omg.CORBA.DynAny current_component() ;

    /**
     * Moves to the next component of this <code>DynAny</code> object.
     * This method is used for iterating through the components of
     * a constructed type, effectively moving a pointer from one
     * component to the next.  The pointer starts out on the first
     * component when a <code>DynAny</code> object is created.
     *
     * <p>
     * 移动到此<code> DynAny </code>对象的下一个组件。此方法用于遍历构造类型的组件,有效地将指针从一个组件移动到下一个组件。
     * 当创建一个<code> DynAny </code>对象时,指针从第一个组件开始。
     * 
     * 
     * @return <code>true</code> if the pointer points to a component;
     * <code>false</code> if there are no more components or this
     * <code>DynAny</code> is associated with a basic type rather than
     * a constructed type
     */
    public boolean next() ;

    /**
     * Moves the internal pointer to the given index. Logically, this method
     * sets a new offset for this pointer.
     *
     * <p>
     *  将内部指针移动到给定的索引。逻辑上,此方法为此指针设置新的偏移量。
     * 
     * 
     * @param index an <code>int</code> indicating the position to which
     *              the pointer should move.  The first position is 0.
     * @return <code>true</code> if the pointer points to a component;
     * <code>false</code> if there is no component at the designated
     * index.  If this <code>DynAny</code> object is associated with a
     * basic type, this method returns <code>false</code> for any index
     * other than 0.
     */
    public boolean seek(int index) ;

    /**
     * Moves the internal pointer to the first component.
     * <p>
     *  将内部指针移动到第一个组件。
     */
    public void rewind() ;
}
