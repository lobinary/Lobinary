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
 * The Holder for <tt>Any</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for <code>Any</code> objects
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>any</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>AnyHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myAnyHolder</code> is an instance of <code>AnyHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myAnyHolder.value</code>.
 *
 * <p>
 *  <tt>任何</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code>任何</code >用于在IDL方法中存储"out"和"inout"参数的对象。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> any </code>,则程序员必须传递<code> AnyHolder </code>的实例作为方法调用中的相应参数;对
 * 于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code>任何</code >用于在IDL方法中存储"out"和"inout"参数的对象。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myAnyHolder </code>是<code> AnyHolder </code>的实例,则存储在其<code> value </code>字段中的值可以通过<code> my
 * AnyHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class AnyHolder implements  Streamable {
    /**
     * The <code>Any</code> value held by this <code>AnyHolder</code> object.
     * <p>
     *  由此<code> AnyHolder </code>对象持有的<code> Any </code>值。
     * 
     */

    public Any value;

    /**
     * Constructs a new <code>AnyHolder</code> object with its
     * <code>value</code> field initialized to <code>null</code>.
     * <p>
     *  构造一个新的<code> AnyHolder </code>对象,其<code> value </code>字段初始化为<code> null </code>。
     * 
     */
    public AnyHolder() {
    }

    /**
     * Constructs a new <code>AnyHolder</code> object for the given
     * <code>Any</code> object.
     * <p>
     *  为给定的<code> Any </code>对象构造一个新的<code> AnyHolder </code>对象。
     * 
     * 
     * @param initial the <code>Any</code> object with which to initialize
     *                the <code>value</code> field of the new
     *                <code>AnyHolder</code> object
     */
    public AnyHolder(Any initial) {
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
     * @param input the InputStream containing CDR formatted data from the wire.
     */
    public void _read(InputStream input) {
        value = input.read_any();
    }

    /**
     * Marshals to <code>output</code> the value in
     * this <code>AnyHolder</code> object.
     *
     * <p>
     *  在<code> AnyHolder </code>对象中输入<code>输出</code>的值。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data.
     */
    public void _write(OutputStream output) {
        output.write_any(value);
    }

    /**
     * Returns the <code>TypeCode</code> object corresponding to the value
     * held in this <code>AnyHolder</code> object.
     *
     * <p>
     * 返回与此<code> AnyHolder </code>对象中保存的值对应的<code> TypeCode </code>对象。
     * 
     * @return    the TypeCode of the value held in
     *              this <code>AnyHolder</code> object
     */
    public TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_any);
    }
}
