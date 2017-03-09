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
 * The Holder for <tt>Int</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for an <code>int</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>long</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>IntHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myIntHolder</code> is an instance of <code>IntHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myIntHolder.value</code>.
 *
 * <p>
 *  <tt> Int </tt>的持有人。
 * 有关Holder文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：Holder文件"</a>。
 * <P> <code> int <代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> long </code>,则程序员必须传递<code> IntHolder </code>的实例作为方法调用中的相应参数;
 * 对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> int <代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myIntHolder </code>是<code> IntHolder </code>的实例,则存储在其<code> value </code>字段中的值可以通过<code> my
 * IntHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class IntHolder implements Streamable {

    /**
     * The <code>int</code> value held by this <code>IntHolder</code>
     * object in its <code>value</code> field.
     * <p>
     *  在<code> value </code>字段中由<code> IntHolder </code>对象持有的<code> int </code>
     * 
     */
    public int value;

    /**
     * Constructs a new <code>IntHolder</code> object with its
     * <code>value</code> field initialized to <code>0</code>.
     * <p>
     *  构造一个新的<code> IntHolder </code>对象,其<code> value </code>字段初始化为<code> 0 </code>。
     * 
     */
    public IntHolder() {
    }

    /**
     * Constructs a new <code>IntHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>int</code>.
     * <p>
     *  构造一个新的<code> IntHolder </code>对象,其<code> value </code>字段初始化为给定的<code> int </code>。
     * 
     * 
     * @param initial the <code>int</code> with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>IntHolder</code> object
     */
    public IntHolder(int initial) {
        value = initial;
    }

    /**
     * Reads unmarshalled data from <code>input</code> and assigns it to
     * the <code>value</code> field in this <code>IntHolder</code> object.
     *
     * <p>
     *  从<code>输入</code>读取未组合的数据,并将其分配给此<code> IntHolder </code>对象中的<code> value </code>字段。
     * 
     * 
     * @param input the <code>InputStream</code> object containing CDR
     *              formatted data from the wire
     */
    public void _read(InputStream input) {
        value = input.read_long();
    }

    /**
     * Marshals the value in this <code>IntHolder</code> object's
     * <code>value</code> field to the output stream <code>output</code>.
     *
     * <p>
     * 将此<code> IntHolder </code>对象的<code>值</code>字段中的值调整为输出流<code>输出</code>。
     * 
     * 
     * @param output the <code>OutputStream</code> object that will contain
     *               the CDR formatted data
     */
    public void _write(OutputStream output) {
        output.write_long(value);
    }

    /**
     * Retrieves the <code>TypeCode</code> object that corresponds
     * to the value held in this <code>IntHolder</code> object's
     * <code>value</code> field.
     *
     * <p>
     *  检索与此<code> IntHolder </code>对象的<code> value </code>字段中保存的值对应的<code> TypeCode </code>对象。
     * 
     * @return    the type code for the value held in this <code>IntHolder</code>
     *            object
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_long);
    }
}
