/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.IDLEntity;

/**
 * Serves as a container for any data that can be
 * described in IDL or for any IDL primitive type.
 * An <code>Any</code> object is used as a component of a
 * <code>NamedValue</code> object, which provides information about
 * arguments or return values in requests, and which is used to define
 * name/value pairs in <code>Context</code> objects.
 <p>
 *
 * An <code>Any</code> object consists of two parts:
 * <OL>
 * <LI>a data value
 * <LI>a <code>TypeCode</code> object describing the type of the data
 * value contained in the <code>Any</code> object.  For example,
 * a <code>TypeCode</code> object for an array contains
 * a field for the length of the array and a field for
 * the type of elements in the array. (Note that in     this case, the
 * second field of the <code>TypeCode</code> object is itself a
 * <code>TypeCode</code> object.)
 * </OL>
 *
 * <P>
 * <a name="anyOps"</a>
 * A large part of the <code>Any</code> class consists of pairs of methods
 * for inserting values into and extracting values from an
 * <code>Any</code> object.
 * <P>
 * For a given primitive type X, these methods are:
 *  <dl>
 *      <dt><code><bold> void insert_X(X x)</bold></code>
 *      <dd> This method allows the insertion of
 *        an instance <code>x</code> of primitive type <code>X</code>
 *    into the <code>value</code> field of the <code>Any</code> object.
 *    Note that the method
 *    <code>insert_X</code> also resets the <code>Any</code> object's
 *    <code>type</code> field if necessary.
 *      <dt> <code><bold>X extract_X()</bold></code>
 *      <dd> This method allows the extraction of an instance of
 *        type <code>X</code> from the <code>Any</code> object.
 *    <BR>
 *    <P>
 *    This method throws the exception <code>BAD_OPERATION</code> under two conditions:
 *    <OL>
 *     <LI> the type of the element contained in the <code>Any</code> object is not
 *         <code>X</code>
 *     <LI> the method <code>extract_X</code> is called before
 *     the <code>value</code> field of the <code>Any</code> object
 *     has been set
 *    </OL>
 * </dl>
 * <P>
 * There are distinct method pairs for each
 * primitive IDL data type (<code>insert_long</code> and <code>extract_long</code>,
 * <code>insert_string</code> and <code>extract_string</code>, and so on).<BR>
 * <P>
 * The class <code>Any</code> also has methods for
 * getting and setting the type code,
 * for testing two <code>Any</code> objects for equality,
 * and for reading an <code>Any</code> object from a stream or
 * writing it to a stream.
 * <BR>
 * <p>
 *  作为可以在IDL或任何IDL原语类型中描述的任何数据的容器<code>任何</code>对象用作<code> NamedValue </code>对象的组件,在请求中的参数或返回值,并且用于在<code>
 *  Context </code>对象中定义名称/值对。
 * <p>
 * 
 *  <code> Any </code>对象由两部分组成：
 * <OL>
 * <li> </li> </li> </li> <li> <li> <li> <li> <li> <li> <li> <li>数组的对象包含数组长度的字段和数组中元素类型的字段(请注意,在这种情况下,<code>
 *  TypeCode </code>对象的第二个字段本身是<code > TypeCode </code> object)。
 * </OL>
 * 
 * <P>
 *  <a name=\"anyOps\"</a> <code> Any </code>类的很大一部分由用于将值插入到<code> Any </code>对象中并从中提取值的一对方法组成
 * <P>
 *  对于给定的基本类型X,这些方法是：
 * <dl>
 * <dt>此插入方法允许插入一个实例<code> x </code>的原始类型<code> X <X> <// code>插入<code>任何</code>对象的<code> value </code>字
 * 段注意,<code> insert_X </code>方法还会重置<code> Any </code>如果需要,可以提供<code> type </code>字段<dt> <code> <bold> X
 *  extract_X()</bold> </code> <dd>代码>从<code> Any </code>对象。
 * <BR>
 * <P>
 *  此方法在两种情况下会抛出异常<code> BAD_OPERATION </code>：
 * <OL>
 * 在<code> Any </code>对象中包含的元素的类型不是<code> X </code> <LI>,<code>方法<code> extract_X </code>已经设置了<code> Any
 *  </code>对象的值</code>字段。
 * </OL>
 * </dl>
 * <P>
 *  每个基本IDL数据类型(<code> insert_long </code>和<code> extract_long </code>,<code> insert_string </code>和<code>
 *  extract_string </code>)有不同的方法对on)<BR>。
 * <P>
 *  类<code> Any </code>还具有获取和设置类型代码的方法,用于测试两个<code>任何</code>对象的相等性,以及读取<code> Any </code>流或将其写入流
 * <BR>
 * 
 * @since   JDK1.2
 */
abstract public class Any implements IDLEntity {

    /**
     * Checks for equality between this <code>Any</code> object and the
     * given <code>Any</code> object.  Two <code>Any</code> objects are
     * equal if both their values and type codes are equal.
     *
     * <p>
     * 检查这个<code> Any </code>对象和给定的<code> Any </code>对象之间的相等性两个<code> Any </code>对象都是相等的,如果它们的值和类型代码相等
     * 
     * 
     * @param a the <code>Any</code> object to test for equality
     * @return  <code>true</code> if the <code>Any</code> objects are equal;
     * <code>false</code> otherwise
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    abstract public boolean equal(Any a);

    /**
     * Returns type information for the element contained in this
     * <code>Any</code> object.
     *
     * <p>
     *  返回此<code> Any </code>对象中包含的元素的类型信息
     * 
     * 
     * @return          the <code>TypeCode</code> object containing type information
     *                about the value contained in this <code>Any</code> object
     */
    abstract public TypeCode type();

    /**
     * Sets this <code>Any</code> object's <code>type</code> field
     * to the given <code>TypeCode</code> object and clears its value.
     * <P>
     * Note that using this method to set the type code wipes out the
     * value if there is one. The method
     * is provided primarily so that the type may be set properly for
     * IDL <code>out</code> parameters.  Generally, setting the type
     * is done by the <code>insert_X</code> methods, which will set the type
     * to X if it is not already set to X.
     *
     * <p>
     *  将<code>任何</code>对象的<code>类型</code>字段设置为给定的<code> TypeCode </code>对象并清除其值
     * <P>
     *  注意,使用此方法设置类型代码会擦除值,如果有一个方法主要提供的类型可以正确设置IDL <code> out </code>参数一般来说,设置类型是通过<code> insert_X </code>方法
     * ,如果尚未设置为X,则会将类型设置为X.。
     * 
     * 
     * @param t       the <code>TypeCode</code> object giving
     *                information for the value in
     *                this <code>Any</code> object
     */
    abstract public void type(TypeCode t);

    ///////////////////////////////////////////////////////////////////////////
    // marshalling/unmarshalling routines

    /**
     * Reads off (unmarshals) the value of an <code>Any</code> object from
     * the given input stream using the given typecode.
     *
     * <p>
     * 使用给定的类型代码从给定的输入流读取(解组)<code> Any </code>对象的值
     * 
     * 
     * @param is the <code>org.omg.CORBA.portable.InputStream</code>
     *                object from which to read
     *                the value contained in this <code>Any</code> object
     *
     * @param t  a <code>TypeCode</code> object containing type information
     *           about the value to be read
     *
     * @exception MARSHAL when the given <code>TypeCode</code> object is
     *                    not consistent with the value that was contained
     *                    in the input stream
     */
    abstract public void   read_value(InputStream is, TypeCode t)
        throws MARSHAL;

    /**
     * Writes out the value of this <code>Any</code> object
     * to the given output stream.  If both <code>typecode</code>
     * and <code>value</code> need to be written, use
     * <code>create_output_stream()</code> to create an <code>OutputStream</code>,
     * then use <code>write_any</code> on the <code>OutputStream</code>.
     * <P>
     * If this method is called on an <code>Any</code> object that has not
     * had a value inserted into its <code>value</code> field, it will throw
     * the exception <code>java.lang.NullPointerException</code>.
     *
     * <p>
     *  将<code> Any </code>对象的值写入给定输出流如果需要写入<code> typecode </code>和<code> value </code>,请使用<code> create_ou
     * tput_stream )</code>创建一个<code> OutputStream </code>,然后使用<code> write_any </code>。
     * <P>
     *  如果这个方法在一个<code> Any </code>对象上调用,该对象没有插入到它的<code> value </code>字段,它会抛出异常<code> javalangNullPointerEx
     * ception </code>。
     * 
     * 
     * @param os        the <code>org.omg.CORBA.portable.OutputStream</code>
     *                object into which to marshal the value
     *                of this <code>Any</code> object
     *
     */
    abstract public void   write_value(OutputStream os);

    /**
     * Creates an output stream into which this <code>Any</code> object's
     * value can be marshalled.
     *
     * <p>
     *  创建一个输出流,其中<code> Any </code>对象的值可以编组
     * 
     * 
     * @return          the newly-created <code>OutputStream</code>
     */
    abstract public OutputStream  create_output_stream();

    /**
     * Creates an input stream from which this <code>Any</code> object's value
     * can be unmarshalled.
     *
     * <p>
     * 创建一个输入流,从这个<code> Any </code>对象的值可以解组
     * 
     * 
     * @return          the newly-created <code>InputStream</code>
     */
    abstract public InputStream  create_input_stream();

    ///////////////////////////////////////////////////////////////////////////
    // basic insertion/extraction methods

    /**
     * Extracts the <code>short</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> short </code>
     * 
     * 
     * @return the <code>short</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>short</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public short    extract_short() throws BAD_OPERATION;

    /**
     * Inserts the given <code>short</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> short </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param s         the <code>short</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_short(short s);

    /**
     * Extracts the <code>int</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> int </code>
     * 
     * 
     * @return the <code>int</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than an <code>int</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public int      extract_long() throws BAD_OPERATION;

    /**
     * Inserts the given <code>int</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> int </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param l         the <code>int</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_long(int l);


    /**
     * Extracts the <code>long</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> long </code>
     * 
     * 
     * @return the <code>long</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>long</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public long     extract_longlong() throws BAD_OPERATION;

    /**
     * Inserts the given <code>long</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> long </code>插入此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param l         the <code>long</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_longlong(long l);

    /**
     * Extracts the <code>short</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> short </code>
     * 
     * 
     * @return the <code>short</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>short</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public short    extract_ushort() throws BAD_OPERATION;

    /**
     * Inserts the given <code>short</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     * 将给定的<code> short </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param s         the <code>short</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_ushort(short s);

    /**
     * Extracts the <code>int</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> int </code>
     * 
     * 
     * @return the <code>int</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than an <code>int</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public int      extract_ulong() throws BAD_OPERATION;

    /**
     * Inserts the given <code>int</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> int </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param l         the <code>int</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_ulong(int l);

    /**
     * Extracts the <code>long</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> long </code>
     * 
     * 
     * @return the <code>long</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>long</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public long     extract_ulonglong() throws BAD_OPERATION;

    /**
     * Inserts the given <code>long</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> long </code>插入此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param l         the <code>long</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_ulonglong(long l);

    /**
     * Extracts the <code>float</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> float </code>
     * 
     * 
     * @return the <code>float</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>float</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public float    extract_float() throws BAD_OPERATION;

    /**
     * Inserts the given <code>float</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> float </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param f         the <code>float</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_float(float f);

    /**
     * Extracts the <code>double</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  在<code>任何</code>对象的<code>值</code>字段中提取<code> double </code>
     * 
     * 
     * @return the <code>double</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>double</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public double   extract_double() throws BAD_OPERATION;

    /**
     * Inserts the given <code>double</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     * 将给定的<code> double </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param d         the <code>double</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_double(double d);

    /**
     * Extracts the <code>boolean</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> boolean </code>
     * 
     * 
     * @return the <code>boolean</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>boolean</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public boolean  extract_boolean() throws BAD_OPERATION;

    /**
     * Inserts the given <code>boolean</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> boolean </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param b         the <code>boolean</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_boolean(boolean b);

    /**
     * Extracts the <code>char</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> char </code>
     * 
     * 
     * @return the <code>char</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>char</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public char     extract_char() throws BAD_OPERATION;

    /**
     * Inserts the given <code>char</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> char </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param c         the <code>char</code> to insert into this
     *                <code>Any</code> object
     * @exception DATA_CONVERSION if there is a data conversion
         *            error
     */
    abstract public void     insert_char(char c) throws DATA_CONVERSION;

    /**
     * Extracts the <code>char</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> char </code>
     * 
     * 
     * @return the <code>char</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>char</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public char     extract_wchar() throws BAD_OPERATION;

    /**
     * Inserts the given <code>char</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> char </code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param c         the <code>char</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_wchar(char c);

    /**
     * Extracts the <code>byte</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取此<code>任何</code>对象的<code>值</code>字段中的<code> byte </code>
     * 
     * 
     * @return the <code>byte</code> stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>byte</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public byte     extract_octet() throws BAD_OPERATION;

    /**
     * Inserts the given <code>byte</code>
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     * 将给定的<code>字节</code>插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param b         the <code>byte</code> to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_octet(byte b);

    /**
     * Extracts the <code>Any</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> Any </code>
     * 
     * 
     * @return the <code>Any</code> object stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this <code>Any</code> object
     *              contains something other than an <code>Any</code> object or the
     *              <code>value</code> field has not yet been set
     */
    abstract public Any      extract_any() throws BAD_OPERATION;

    /**
     * Inserts the given <code>Any</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> Any </code>对象插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param a         the <code>Any</code> object to insert into this
     *                <code>Any</code> object
     */
    abstract public void     insert_any(Any a);

    /**
     * Extracts the <code>org.omg.CORBA.Object</code> in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> orgomgCORBAObject </code>
     * 
     * 
     * @return the <code>org.omg.CORBA.Object</code> stored in
     *         this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than an
     *              <code>org.omg.CORBA.Object</code> or the
     *              <code>value</code> field has not yet been set
     */
    abstract public org.omg.CORBA.Object extract_Object() throws BAD_OPERATION;

    /**
     * Inserts the given <code>org.omg.CORBA.Object</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> orgomgCORBAObject </code>对象插入到此<code> Any </code>对象的<code> value </code>字段
     * 
     * 
     * @param o         the <code>org.omg.CORBA.Object</code> object to insert into this
     *                <code>Any</code> object
     */
    abstract public void insert_Object(org.omg.CORBA.Object o);

    /**
     * Extracts the <code>java.io.Serializable</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  在此<code>任何</code>对象的<code>值</code>字段中提取<code> javaioSerializable </code>对象
     * 
     * 
     * @return the <code>java.io.Serializable</code> object stored in
     *         this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>java.io.Serializable</code>
     *              object or the
     *              <code>value</code> field has not yet been set
     */
    abstract public java.io.Serializable extract_Value() throws BAD_OPERATION ;

    /**
     * Inserts the given <code>java.io.Serializable</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> javaioSerializable </code>对象插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param v         the <code>java.io.Serializable</code> object to insert into this
     *                <code>Any</code> object
     */
    abstract public void insert_Value(java.io.Serializable v) ;

    /**
     * Inserts the given <code>java.io.Serializable</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     * 将给定的<code> javaioSerializable </code>对象插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param v         the <code>java.io.Serializable</code> object to insert into this
     *                <code>Any</code> object
     * @param t     the <code>TypeCode</code> object that is to be inserted into
     *              this <code>Any</code> object's <code>type</code> field
     *              and that describes the <code>java.io.Serializable</code>
     *              object being inserted
         * @throws MARSHAL if the ORB has a problem marshalling or
         *          unmarshalling parameters
     */
    abstract public void insert_Value(java.io.Serializable v, TypeCode t)
        throws MARSHAL ;
/**
     * Inserts the given <code>org.omg.CORBA.Object</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> orgomgCORBAObject </code>对象插入到此<code> Any </code>对象的<code> value </code>字段
     * 
     * 
     * @param o         the <code>org.omg.CORBA.Object</code> instance to insert into this
     *                <code>Any</code> object
     * @param t     the <code>TypeCode</code> object that is to be inserted into
     *              this <code>Any</code> object and that describes
     *              the <code>Object</code> being inserted
     * @exception BAD_OPERATION if this  method is invalid for this
         *            <code>Any</code> object
     *
     */
    abstract public void insert_Object(org.omg.CORBA.Object o, TypeCode t)
        throws BAD_PARAM;

    /**
     * Extracts the <code>String</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> String </code>
     * 
     * 
     * @return the <code>String</code> object stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>String</code> object or the
     *              <code>value</code> field has not yet been set
     */
    abstract public String   extract_string() throws BAD_OPERATION;

    /**
     * Inserts the given <code>String</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> String </code>对象插入到此<code>任何</code>对象的<code> value </code>字段
     * 
     * 
     * @param s         the <code>String</code> object to insert into this
     *                <code>Any</code> object
     * @exception DATA_CONVERSION if there is a data conversion error
     * @exception MARSHAL if the ORB has a problem marshalling or
         *             unmarshalling parameters
     */
    abstract public void     insert_string(String s) throws DATA_CONVERSION, MARSHAL;

    /**
     * Extracts the <code>String</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> String </code>
     * 
     * 
     * @return the <code>String</code> object stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>String</code> object or the
     *              <code>value</code> field has not yet been set
     */
    abstract public String   extract_wstring() throws BAD_OPERATION;

    /**
     * Inserts the given <code>String</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  将给定的<code> String </code>对象插入到此<code>任何</code>对象的<code> value </code>字段
     * 
     * 
     * @param s         the <code>String</code> object to insert into this
     *                <code>Any</code> object
     * @exception MARSHAL if the ORB has a problem marshalling or
         *             unmarshalling parameters
     */
    abstract public void     insert_wstring(String s) throws MARSHAL;

    /**
     * Extracts the <code>TypeCode</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  在此<code>任何</code>对象的<code>值</code>字段中提取<code> TypeCode </code>
     * 
     * 
     * @return the <code>TypeCode</code> object stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a <code>TypeCode</code> object or the
     *              <code>value</code> field has not yet been set
     */
    abstract public TypeCode extract_TypeCode() throws BAD_OPERATION;

    /**
     * Inserts the given <code>TypeCode</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     * 将给定的<code> TypeCode </code>对象插入到此<code>任何</code>对象的<code>值</code>字段
     * 
     * 
     * @param t         the <code>TypeCode</code> object to insert into this
     *                <code>Any</code> object
     */
    abstract public void           insert_TypeCode(TypeCode t);

    /**
     * Extracts the <code>Principal</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     * Note that the class <code>Principal</code> has been deprecated.
     *
     * <p>
     *  提取<code>任何</code>对象的<code>值</code>字段中的<code> Principal </code>对象请注意,<code> Principal </code>类已被弃用
     * 
     * 
     * @return the <code>Principal</code> object stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a
     *              <code>Principal</code> object or the
     *              <code>value</code> field has not yet been set
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Deprecated by CORBA 2.2.
     */
    @Deprecated
    public Principal extract_Principal() throws BAD_OPERATION {
        throw new org.omg.CORBA.NO_IMPLEMENT() ;
    }

    /**
     * Inserts the given <code>Principal</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     * Note that the class <code>Principal</code> has been deprecated.
     *
     * <p>
     *  将给定的<code> Principal </code>对象插入到<code> Any </code>对象的<code> value </code>字段中注意,<code> Principal </code>
     * 。
     * 
     * 
     * @param p         the <code>Principal</code> object to insert into this
     *                <code>Any</code> object
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     * @deprecated Deprecated by CORBA 2.2.
     */
    @Deprecated
    public void    insert_Principal(Principal p) {
        throw new org.omg.CORBA.NO_IMPLEMENT() ;
    }

    ///////////////////////////////////////////////////////////////////////////
    // insertion/extraction of streamables

    /**
     * Extracts a <code>Streamable</code> from this <code>Any</code> object's
     * <code>value</code> field.  This method allows the extraction of
     * non-primitive IDL types.
     *
     * <p>
     *  从此<code>任何</code>对象的<code>值</code>字段中提取<code> Streamable </code>此方法允许提取非原始IDL类型
     * 
     * 
     * @return the <code>Streamable</code> stored in the <code>Any</code> object.
     * @throws BAD_INV_ORDER if the caller has invoked operations in the wrong order
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public org.omg.CORBA.portable.Streamable extract_Streamable()
        throws org.omg.CORBA.BAD_INV_ORDER {
        throw new org.omg.CORBA.NO_IMPLEMENT() ;
    }

    /**
     * Inserts the given <code>Streamable</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     * This method allows the insertion of non-primitive IDL types.
     *
     * <p>
     *  将给定的<code> Streamable </code>对象插入到此<code>任何</code>对象的<code> value </code>字段中此方法允许插入非原始IDL类型
     * 
     * 
     * @param s         the <code>Streamable</code> object to insert into this
     *                <code>Any</code> object; may be a non-primitive
     *                IDL type
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public void insert_Streamable(Streamable s) {
        throw new org.omg.CORBA.NO_IMPLEMENT() ;
    }

    /**
     * Extracts the <code>java.math.BigDecimal</code> object in this
     * <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     * 在此<code>任何</code>对象的<code>值</code>字段中提取<code> javamathBigDecimal </code>
     * 
     * 
     * @return the <code>java.math.BigDecimal</code> object
     *         stored in this <code>Any</code> object
     * @exception BAD_OPERATION if this  <code>Any</code> object
     *              contains something other than a
     *              <code>java.math.BigDecimal</code> object or the
     *              <code>value</code> field has not yet been set
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public java.math.BigDecimal extract_fixed() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Throws an <a href="package-summary.html#NO_IMPLEMENT">
     * <code>org.omg.CORBA.NO_IMPLEMENT</code></a> exception.
     * <P>
     * Inserts the given <code>java.math.BigDecimal</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  引发<a href=\"package-summaryhtml#NO_IMPLEMENT\"> <code> orgomgCORBANO_IMPLEMENT </code> </a>例外
     * <P>
     *  将给定的<code> javamathBigDecimal </code>对象插入到此<code> Any </code>对象的<code> value </code>字段
     * 
     * 
     * @param value             the <code>java.math.BigDecimal</code> object
     *                  to insert into this <code>Any</code> object
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public void insert_fixed(java.math.BigDecimal value) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Throws an <a href="package-summary.html#NO_IMPLEMENT">
     * <code>org.omg.CORBA.NO_IMPLEMENT</code></a> exception.
     * <P>
     * Inserts the given <code>java.math.BigDecimal</code> object
     * into this <code>Any</code> object's <code>value</code> field.
     *
     * <p>
     *  引发<a href=\"package-summaryhtml#NO_IMPLEMENT\"> <code> orgomgCORBANO_IMPLEMENT </code> </a>例外
     * <P>
     * 
     * @param value             the <code>java.math.BigDecimal</code> object
     *                  to insert into this <code>Any</code> object
     * @param type     the <code>TypeCode</code> object that is to be inserted into
     *              this <code>Any</code> object's <code>type</code> field
     *              and that describes the <code>java.math.BigDecimal</code>
     *              object being inserted
     * @throws org.omg.CORBA.BAD_INV_ORDER if this method is  invoked improperly
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public void insert_fixed(java.math.BigDecimal value, org.omg.CORBA.TypeCode type)
        throws org.omg.CORBA.BAD_INV_ORDER
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
}
