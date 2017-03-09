/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2001, Oracle and/or its affiliates. All rights reserved.
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

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

/**
 * The Holder for <tt>TypeCode</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>TypeCode</code> object
 * that is used to store "out" and "inout" parameters in IDL operations.
 * If an IDL operation signature has an IDL <code>TypeCode</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>TypeCodeHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myTypeCodeHolder</code> is an instance of <code>TypeCodeHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myTypeCodeHolder.value</code>.
 *
 * <p>
 *  <tt> TypeCode </tt>的持有人。
 * 有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> TypeCode <代码>对象,用于在IDL操作中存储"out"和"inout"参数。
 * 如果IDL操作签名具有作为"out"或"inout"参数的IDL <code> TypeCode </code>,则程序员必须传递<code> TypeCodeHolder </code>的实例作为方法
 * 调用中的相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> TypeCode <代码>对象,用于在IDL操作中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myTypeCodeHolder </code>是<code> TypeCodeHolder </code>的实例,则存储在其<code> value </code>字段中的值可以用
 * <code> myTypeCodeHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class TypeCodeHolder implements Streamable {

    /**
     * The <code>TypeCode</code> value held by
     * this <code>TypeCodeHolder</code> object.
     * <p>
     *  <code> TypeCodeHolder </code>对象持有的<code> TypeCode </code>值。
     * 
     */
    public TypeCode value;

    /**
     * Constructs a new <code>TypeCodeHolder</code> object with its
     * <code>value</code> field initialized to <code>null</code>.
     * <p>
     *  构造一个新的<code> TypeCodeHolder </code>对象,其<code> value </code>字段初始化为<code> null </code>。
     * 
     */
    public TypeCodeHolder() {
    }

    /**
     * Constructs a new <code>TypeCodeHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>TypeCode</code> object.
     * <p>
     *  构造一个新的<code> TypeCodeHolder </code>对象,其<code> value </code>字段初始化为给定的<code> TypeCode </code>对象。
     * 
     * 
     * @param initial the <code>TypeCode</code> object with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>TypeCodeHolder</code> object
     */
    public TypeCodeHolder(TypeCode initial) {
        value = initial;
    }

    /**
     * Reads from <code>input</code> and initalizes the value in
     * this <code>TypeCodeHolder</code> object
     * with the unmarshalled data.
     *
     * <p>
     * 从<code>输入</code>读取,并初始化此<code> TypeCodeHolder </code>对象中的值与未组合的数据。
     * 
     * 
     * @param input the InputStream containing CDR formatted data from the wire
     */
    public void _read(InputStream input) {
        value = input.read_TypeCode();
    }

    /**
     * Marshals to <code>output</code> the value in
     * this <code>TypeCodeHolder</code> object.
     *
     * <p>
     *  在<code> TypeCodeHolder </code>对象中输入<code>输出</code>的值。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data
     */
    public void _write(OutputStream output) {
        output.write_TypeCode(value);
    }

    /**
     * Returns the TypeCode corresponding to the value held in
     * this <code>TypeCodeHolder</code> object.
     *
     * <p>
     *  返回与此<code> TypeCodeHolder </code>对象中保存的值对应的TypeCode。
     * 
     * @return    the TypeCode of the value held in
     *             this <code>TypeCodeHolder</code> object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
    }
}
