/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * The Holder for <tt>Principal</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A container class for values of type <code>Principal</code>
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>Principal</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>PrincipalHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myPrincipalHolder</code> is an instance of <code>PrincipalHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myPrincipalHolder.value</code>.
 *
 * <p>
 *  <tt>主体</tt>的持有人。
 * 有关Holder文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：Holder文件"</a>。
 * <P>类型为<code> Principal的容器类</code>用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> Principal </code>,则程序员必须传递<code> PrincipalHolder </code>的实例作为
 * 方法调用中的相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P>类型为<code> Principal的容器类</code>用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myPrincipalHolder </code>是<code> PrincipalHolder </code>的实例,则存储在其<code> value </code>字段中的值可
 * 以通过<code> myPrincipalHolder.value </code> 。
 * 
 * 
 * @since       JDK1.2
 * @deprecated Deprecated by CORBA 2.2.
 */
@Deprecated
public final class PrincipalHolder implements Streamable {
    /**
     * The <code>Principal</code> value held by this <code>PrincipalHolder</code>
     * object.
     * <p>
     */
    public Principal value;

    /**
     * Constructs a new <code>PrincipalHolder</code> object with its
     * <code>value</code> field initialized to <code>null</code>.
     * <p>
     *  对象拥有<code> PrincipalHolder </code>值的<code> Principal </code>值。
     * 
     */
    public PrincipalHolder() {
    }

    /**
     * Constructs a new <code>PrincipalHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>Principal</code> object.
     * <p>
     *  构造一个新的<code> PrincipalHolder </code>对象,其<code> value </code>字段初始化为<code> null </code>。
     * 
     * 
     * @param initial the <code>Principal</code> with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>PrincipalHolder</code> object
     */
    public PrincipalHolder(Principal initial) {
        value = initial;
    }

    public void _read(InputStream input) {
        value = input.read_Principal();
    }

    public void _write(OutputStream output) {
        output.write_Principal(value);
    }

    public org.omg.CORBA.TypeCode _type() {
        return ORB.init().get_primitive_tc(TCKind.tk_Principal);
    }

}
