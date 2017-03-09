/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * The Holder for <tt>Char</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>char</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>char</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>CharHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myCharHolder</code> is an instance of <code>CharHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myCharHolder.value</code>.
 *
 * <p>
 *  <tt> Char </tt>的持有人。
 * 有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> char <代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> char </code>,则程序员必须传递<code> CharHolder </code>的实例作为方法调用中的相应参数
 * ;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> char <代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myCharHolder </code>是<code> CharHolder </code>的实例,则存储在其<code> value </code>字段中的值可以通过<code> 
 * myCharHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class CharHolder implements Streamable {

    /**
     * The <code>char</code> value held by this <code>CharHolder</code>
     * object.
     * <p>
     *  此<c> CharHolder </code>对象持有的<code> char </code>值。
     * 
     */
    public char value;

    /**
     * Constructs a new <code>CharHolder</code> object with its
     * <code>value</code> field initialized to <code>0</code>.
     * <p>
     *  构造一个新的<code> CharHolder </code>对象,其<code> value </code>字段初始化为<code> 0 </code>。
     * 
     */
    public CharHolder() {
    }

    /**
     * Constructs a new <code>CharHolder</code> object for the given
     * <code>char</code>.
     * <p>
     *  为给定的<code> char </code>构造一个新的<code> CharHolder </code>对象。
     * 
     * 
     * @param initial the <code>char</code> with which to initialize
     *                the <code>value</code> field of the new
     *                <code>CharHolder</code> object
     */
    public CharHolder(char initial) {
        value = initial;
    }

    /**
     * Reads from <code>input</code> and initalizes the value in
     * this <code>CharHolder</code> object
     * with the unmarshalled data.
     *
     * <p>
     *  从<code>输入</code>中读取,并初始化此<c> CharHolder </code>对象中的值与未组合的数据。
     * 
     * 
     * @param input the InputStream containing CDR formatted data from the wire
     */
    public void _read(InputStream input) {
        value = input.read_char();
    }

    /**
     * Marshals to <code>output</code> the value in
     * this <code>CharHolder</code> object.
     *
     * <p>
     *  在<code> CharHolder </code>对象中输入<code>输出</code>的值。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data
     */
    public void _write(OutputStream output) {
        output.write_char(value);
    }

    /**
     * Returns the <code>TypeCode</code> object  corresponding
     * to the value held in
     * this <code>CharHolder</code> object.
     *
     * <p>
     * 返回与此<code> CharHolder </code>对象中保存的值对应的<code> TypeCode </code>对象。
     * 
     * @return    the TypeCode of the value held in
     *            this <code>CharHolder</code> object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_char);
    }
}
