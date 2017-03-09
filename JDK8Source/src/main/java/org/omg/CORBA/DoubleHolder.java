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
 * The Holder for <tt>Double</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>double</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>double</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>DoubleHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myDoubleHolder</code> is an instance of <code>DoubleHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myDoubleHolder.value</code>.
 *
 * <p>
 *  <tt>双人</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> double <代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> double </code>,则程序员必须传递<code> DoubleHolder </code>的实例作为方法调用中的
 * 相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> double <代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myDoubleHolder </code>是<code> DoubleHolder </code>的实例,则可以使用<code> myDoubleHolder.value </code>
 * 访问存储在其<code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class DoubleHolder implements Streamable {

    /**
     * The <code>double</code> value held by this <code>DoubleHolder</code>
     * object.
     * <p>
     *  此<<code> DoubleHolder </code>对象持有的<code> double </code>值。
     * 
     */

    public double value;

    /**
     * Constructs a new <code>DoubleHolder</code> object with its
     * <code>value</code> field initialized to 0.0.
     * <p>
     *  构造一个新的<code> DoubleHolder </code>对象,其<code> value </code>字段初始化为0.0。
     * 
     */
    public DoubleHolder() {
    }

    /**
     * Constructs a new <code>DoubleHolder</code> object for the given
     * <code>double</code>.
     * <p>
     *  为给定的<code> double </code>构造一个新的<code> DoubleHolder </code>对象。
     * 
     * 
     * @param initial the <code>double</code> with which to initialize
     *                the <code>value</code> field of the new
     *                <code>DoubleHolder</code> object
     */
    public DoubleHolder(double initial) {
        value = initial;
    }

    /**
     * Read a double value from the input stream and store it in the
     * value member.
     *
     * <p>
     *  从输入流读取一个double值并将其存储在value成员中。
     * 
     * 
     * @param input the <code>InputStream</code> to read from.
     */
    public void _read(InputStream input) {
        value = input.read_double();
    }

    /**
     * Write the double value stored in this holder to an
     * <code>OutputStream</code>.
     *
     * <p>
     *  将存储在此持有者中的double值写入<code> OutputStream </code>。
     * 
     * 
     * @param output the <code>OutputStream</code> to write into.
     */
    public void _write(OutputStream output) {
        output.write_double(value);
    }

    /**
     * Return the <code>TypeCode</code> of this holder object.
     *
     * <p>
     * 返回此holder对象的<code> TypeCode </code>。
     * 
     * @return the <code>TypeCode</code> object.
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_double);
    }


}
