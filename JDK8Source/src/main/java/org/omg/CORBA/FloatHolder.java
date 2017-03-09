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
 * The Holder for <tt>Float</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>float</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>float</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>FloatHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myFloatHolder</code> is an instance of <code>FloatHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myFloatHolder.value</code>.
 *
 * <p>
 *  <tt> Float </tt>的持有人。
 * 有关Holder文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> float <代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> float </code>,则程序员必须传递<code> FloatHolder </code>的实例作为方法调用中的相应
 * 参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> float <代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myFloatHolder </code>是<code> FloatHolder </code>的实例,则存储在其<code> value </code>字段中的值可以用<code>
 *  myFloatHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class FloatHolder implements Streamable {
    /**
     * The <code>float</code> value held by this <code>FloatHolder</code>
     * object.
     * <p>
     *  对象的<code> FloatHolder </code>对象保存的<code> float </code>值。
     * 
     */
    public float value;

    /**
     * Constructs a new <code>FloatHolder</code> object with its
     * <code>value</code> field initialized to 0.0.
     * <p>
     *  构造一个新的<code> FloatHolder </code>对象,其<code> value </code>字段初始化为0.0。
     * 
     */
    public FloatHolder() {
    }

    /**
     * Constructs a new <code>FloatHolder</code> object for the given
     * <code>float</code>.
     * <p>
     *  为给定的<code> float </code>构造一个新的<code> FloatHolder </code>对象。
     * 
     * 
     * @param initial the <code>float</code> with which to initialize
     *                the <code>value</code> field of the new
     *                <code>FloatHolder</code> object
     */
    public FloatHolder(float initial) {
        value = initial;
    }

    /**
     * Read a float from an input stream and initialize the value
     * member with the float value.
     *
     * <p>
     *  从输入流读取一个float,并用float值初始化value成员。
     * 
     * 
     * @param input the <code>InputStream</code> to read from.
     */
    public void _read(InputStream input) {
        value = input.read_float();
    }

    /**
     * Write the float value into an output stream.
     *
     * <p>
     *  将浮点值写入输出流。
     * 
     * 
     * @param output the <code>OutputStream</code> to write into.
     */
    public void _write(OutputStream output) {
        output.write_float(value);
    }

    /**
     * Return the <code>TypeCode</code> of this Streamable.
     *
     * <p>
     *  返回此Streamable的<code> TypeCode </code>。
     * 
     * @return the <code>TypeCode</code> object.
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_float);
    }
}
