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
 * The Holder for <tt>Boolean</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>boolean</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>boolean</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>BooleanHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myBooleanHolder</code> is an instance of <code>BooleanHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myBooleanHolder.value</code>.
 *
 * <p>
 *  <tt>布尔</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> boolean <代码>,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> boolean </code>,则程序员必须传递<code> BooleanHolder </code>的实例作为方法调用
 * 中的相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> boolean <代码>,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myBooleanHolder </code>是<code> BooleanHolder </code>的实例,则存储在其<code> value </code>字段中的值可以通过<code>
 *  myBooleanHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 */
public final class BooleanHolder implements Streamable {

    /**
     * The <code>boolean</code> value held by this <code>BooleanHolder</code>
     * object.
     * <p>
     *  对象拥有的<code> boolean </code>值。
     * 
     */
    public boolean value;

    /**
     * Constructs a new <code>BooleanHolder</code> object with its
     * <code>value</code> field initialized to <code>false</code>.
     * <p>
     *  构造一个新的<code> BooleanHolder </code>对象,其<code> value </code>字段初始化为<code> false </code>。
     * 
     */
    public BooleanHolder() {
    }

    /**
     * Constructs a new <code>BooleanHolder</code> object with its
     * <code>value</code> field initialized with the given <code>boolean</code>.
     * <p>
     *  构造一个新的<code> BooleanHolder </code>对象,其<code> value </code>字段用给定的<code> boolean </code>初始化。
     * 
     * 
     * @param initial the <code>boolean</code> with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>BooleanHolder</code> object
     */
    public BooleanHolder(boolean initial) {
        value = initial;
    }

    /**
     * Reads unmarshalled data from <code>input</code> and assigns it to this
     * <code>BooleanHolder</code> object's <code>value</code> field.
     *
     * <p>
     * 从<code>输入</code>读取未组合的数据,并将其分配给此<code> BooleanHolder </code>对象的<code>值</code>字段。
     * 
     * 
     * @param input the <code>InputStream</code> object containing
     *              CDR formatted data from the wire
     */
    public void _read(InputStream input) {
        value = input.read_boolean();
    }

    /**
     * Marshals the value in this <code>BooleanHolder</code> object's
     * <code>value</code> field to the output stream <code>output</code>.
     *
     * <p>
     *  将<code> BooleanHolder </code>对象的<code> value </code>字段中的值调度为输出流<code>输出</code>。
     * 
     * 
     * @param output the OutputStream which will contain the CDR formatted data
     */
    public void _write(OutputStream output) {
        output.write_boolean(value);
    }

    /**
     * Retrieves the <code>TypeCode</code> object that corresponds to the
     * value held in this <code>BooleanHolder</code> object.
     *
     * <p>
     *  检索与此<code> BooleanHolder </code>对象中保存的值对应的<code> TypeCode </code>对象。
     * 
     * @return    the <code>TypeCode</code> for the value held
     *            in this <code>BooleanHolder</code> object
     */
    public TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_boolean);
    }
}
