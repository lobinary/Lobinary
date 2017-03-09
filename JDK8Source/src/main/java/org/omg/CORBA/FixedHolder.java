/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * The Holder for <tt>Fixed</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * FixedHolder is a container class for values of IDL type "fixed",
 * which is mapped to the Java class java.math.BigDecimal.
 * It is usually used to store "out" and "inout" IDL method parameters.
 * If an IDL method signature has a fixed as an "out" or "inout" parameter,
 * the programmer must pass an instance of FixedHolder as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the contained
 * value corresponding to the "out" value returned from the server.
 *
 * <p>
 *  <tt>固定</tt>的持有人。有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> FixedHolder是一个容器类,用于IDL类型"固定",它被映射到Java类java.math.BigDecimal。它通常用于存储"out"和"inout"IDL方法参数。
 * 如果IDL方法签名具有固定的"out"或"inout"参数,则程序员必须传递FixedHolder的实例作为方法调用中的相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * 在方法调用返回之前,ORB将填充与从服务器返回的"out"值相对应的包含值。
 * 
 */
public final class FixedHolder implements Streamable {
    /**
     * The value held by the FixedHolder
     * <p>
     *  FixedHolder保留的值
     * 
     */
    public java.math.BigDecimal value;

    /**
     * Construct the FixedHolder without initializing the contained value.
     * <p>
     *  构造FixedHolder而不初始化包含的值。
     * 
     */
    public FixedHolder() {
    }

    /**
     * Construct the FixedHolder and initialize it with the given value.
     * <p>
     *  构造FixedHolder并使用给定值初始化它。
     * 
     * 
     * @param initial the value used to initialize the FixedHolder
     */
    public FixedHolder(java.math.BigDecimal initial) {
        value = initial;
    }

    /**
     * Read a fixed point value from the input stream and store it in
     * the value member.
     *
     * <p>
     *  从输入流读取固定点值,并将其存储在值成员中。
     * 
     * 
     * @param input the <code>InputStream</code> to read from.
     */
    public void _read(InputStream input) {
        value = input.read_fixed();
    }

    /**
     * Write the fixed point value stored in this holder to an
     * <code>OutputStream</code>.
     *
     * <p>
     *  将存储在此持有者中的固定点值写入<code> OutputStream </code>。
     * 
     * 
     * @param output the <code>OutputStream</code> to write into.
     */
    public void _write(OutputStream output) {
        output.write_fixed(value);
    }


    /**
     * Return the <code>TypeCode</code> of this holder object.
     *
     * <p>
     *  返回此holder对象的<code> TypeCode </code>。
     * 
     * @return the <code>TypeCode</code> object.
     */
    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_fixed);
    }

}
