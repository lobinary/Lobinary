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
 * The Holder for <tt>String</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>String</code>
 * that is used to store "out" and "inout" parameters in IDL operations.
 * If an IDL operation signature has an IDL <code>string</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>StringHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myStringHolder</code> is an instance of <code>StringHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myStringHolder.value</code>.
 *
 * <p>
 *  <tt>字符串</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> String <代码>,用于在IDL操作中存储"out"和"inout"参数。
 * 如果IDL操作签名具有作为"out"或"inout"参数的IDL <code> string </code>,则程序员必须传递<code> StringHolder </code>的实例作为方法调用中的
 * 相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> String <代码>,用于在IDL操作中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myStringHolder </code>是<code> StringHolder </code>的实例,则存储在其<code> value </code>字段中的值可以通过<code>
 *  myStringHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class StringHolder implements Streamable {

    /**
     * The <code>String</code> value held by this <code>StringHolder</code>
     * object.
     * <p>
     *  此<> StringHolder </code>对象所持有的<code> String </code>值。
     * 
     */
    public String value;

    /**
     * Constructs a new <code>StringHolder</code> object with its
     * <code>value</code> field initialized to <code>null</code>.
     * <p>
     *  构造一个新的<code> StringHolder </code>对象,其<code> value </code>字段初始化为<code> null </code>。
     * 
     */
    public StringHolder() {
    }

    /**
     * Constructs a new <code>StringHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>String</code>.
     * <p>
     *  构造一个新的<code> StringHolder </code>对象,其<code> value </code>字段初始化为给定的<code> String </code>。
     * 
     * 
     * @param initial the <code>String</code> with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>StringHolder</code> object
     */
    public StringHolder(String initial) {
        value = initial;
    }

    /**
     * Reads the unmarshalled data from <code>input</code> and assigns it to
     * the <code>value</code> field of this <code>StringHolder</code> object.
     *
     * <p>
     * 从<code> input </code>读取未组合的数据,并将其分配给此<code> StringHolder </code>对象的<code> value </code>字段。
     * 
     * 
     * @param input the InputStream containing CDR formatted data from the wire.
     */
    public void _read(InputStream input) {
        value = input.read_string();
    }

    /**
     * Marshals the value held by this <code>StringHolder</code> object
     * to the output stream  <code>output</code>.
     *
     * <p>
     *  将此<code> StringHolder </code>对象的值保存到输出流<code>输出</code>中。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data.
     */
    public void _write(OutputStream output) {
        output.write_string(value);
    }

    /**
     * Retrieves the <code>TypeCode</code> object that corresponds to
     * the value held in this <code>StringHolder</code> object.
     *
     * <p>
     *  检索与此<code> StringHolder </code>对象中保存的值对应的<code> TypeCode </code>对象。
     * 
     * @return    the type code of the value held in this <code>StringHolder</code>
     *            object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_string);
    }
}
