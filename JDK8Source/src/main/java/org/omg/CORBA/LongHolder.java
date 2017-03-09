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
 * The Holder for <tt>Long</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>long</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>long long</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>LongHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myLongHolder</code> is an instance of <code>LongHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myLongHolder.value</code>.
 *
 * <p>
 *  <tt>长</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> long <代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> long long </code>,则程序员必须传递<code> LongHolder </code>的实例作为方法中的相
 * 应参数调用;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> long <代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myLongHolder </code>是<code> LongHolder </code>的实例,则存储在其<code> value </code>字段中的值可以通过<code> 
 * myLongHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class LongHolder implements Streamable {

    /**
     * The <code>long</code> value held by this <code>LongHolder</code>
     * object.
     * <p>
     *  <code> LongHolder </code>对象持有的<code> long </code>值。
     * 
     */
    public long value;

    /**
     * Constructs a new <code>LongHolder</code> object with its
     * <code>value</code> field initialized to <code>0</code>.
     * <p>
     *  构造一个新的<code> LongHolder </code>对象,其<code> value </code>字段初始化为<code> 0 </code>。
     * 
     */
    public LongHolder() {
    }

    /**
     * Constructs a new <code>LongHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>long</code>.
     * <p>
     *  构造一个新的<code> LongHolder </code>对象,其<code> value </code>字段初始化为给定的<code> long </code>。
     * 
     * 
     * @param initial the <code>long</code> with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>LongHolder</code> object
     */
    public LongHolder(long initial) {
        value = initial;
    }

    /**
     * Reads from <code>input</code> and initalizes the value in the Holder
     * with the unmarshalled data.
     *
     * <p>
     *  从<code>输入</code>读取,并使用未组合的数据初始化Holder中的值。
     * 
     * 
     * @param input the InputStream containing CDR formatted data from the wire
     */
    public void _read(InputStream input) {
        value = input.read_longlong();
    }

    /**
     * Marshals to <code>output</code> the value in the Holder.
     *
     * <p>
     *  调度到<code>输出</code>中的值。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data
     */
    public void _write(OutputStream output) {
        output.write_longlong(value);
    }

    /**
     * Returns the <code>TypeCode</code> object
     * corresponding to the value held in the Holder.
     *
     * <p>
     * 返回与保持在持有人中的值相对应的<code> TypeCode </code>对象。
     * 
     * @return    the TypeCode of the value held in the holder
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_longlong);
    }

}
