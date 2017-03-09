/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * The Holder for <tt>Byte</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>byte</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>octet</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>ByteHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myByteHolder</code> is an instance of <code>ByteHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myByteHolder.value</code>.
 *
 * <p>
 *  <tt>字节</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code>字节的持有人类<代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> octet </code>,则程序员必须传递<code> ByteHolder </code>的实例作为方法调用中的相应参
 * 数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code>字节的持有人类<代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myByteHolder </code>是<code> ByteHolder </code>的实例,则存储在其<code> value </code>字段中的值可以用<code> m
 * yByteHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class ByteHolder implements Streamable {
    /**
     * The <code>byte</code> value held by this <code>ByteHolder</code>
     * object.
     * <p>
     *  该<code> ByteHolder </code>对象保存的<code> byte </code>值。
     * 
     */

    public byte value;

    /**
     * Constructs a new <code>ByteHolder</code> object with its
     * <code>value</code> field initialized to 0.
     * <p>
     *  构造一个新的<code> ByteHolder </code>对象,其<code> value </code>字段初始化为0。
     * 
     */
    public ByteHolder() {
    }

    /**
     * Constructs a new <code>ByteHolder</code> object for the given
     * <code>byte</code>.
     * <p>
     *  为给定的<code>字节</code>构造一个新的<code> ByteHolder </code>对象。
     * 
     * 
     * @param initial the <code>byte</code> with which to initialize
     *                the <code>value</code> field of the new
     *                <code>ByteHolder</code> object
     */
    public ByteHolder(byte initial) {
        value = initial;
    }

    /**
     * Reads from <code>input</code> and initalizes the value in
     * this <code>ByteHolder</code> object
     * with the unmarshalled data.
     *
     * <p>
     *  从<code>输入</code>读取,并初始化此<code> ByteHolder </code>对象中的值与未组合的数据。
     * 
     * 
     * @param input the InputStream containing CDR formatted data from the wire.
     */
    public void _read(InputStream input) {
        value = input.read_octet();
    }

    /**
     * Marshals to <code>output</code> the value in
     * this <code>ByteHolder</code> object.
     *
     * <p>
     *  在<code> ByteHolder </code>对象中输入<code>输出</code>的值。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data.
     */
    public void _write(OutputStream output) {
        output.write_octet(value);
    }

    /**
     * Returns the TypeCode corresponding to the value held in
     * this <code>ByteHolder</code> object.
     *
     * <p>
     * 返回与此<code> ByteHolder </code>对象中保存的值对应的TypeCode。
     * 
     * @return    the TypeCode of the value held in
     *               this <code>ByteHolder</code> object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_octet);
    }
}
