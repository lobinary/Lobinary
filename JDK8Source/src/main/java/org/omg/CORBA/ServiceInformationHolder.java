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

/**
 * The Holder for <tt>ServiceInformation</tt>.  For more information on
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>ServiceInformation</code> object
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>xxx</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>ServiceInformationHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myServiceInformationHolder</code> is an instance of <code>ServiceInformationHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myServiceInformationHolder.value</code>.
 * <p>
 *  <tt> ServiceInformation </tt>的持有人。
 * 有关持有人文件的详细信息,请参见<a href="doc-files/generatedfiles.html#holder">"生成的文件：持有人文件"</a>。
 * <P> <code> ServiceInformation <代码>对象,用于在IDL方法中存储"out"和"inout"参数。
 * 如果IDL方法签名具有作为"out"或"inout"参数的IDL <code> xxx </code>,则程序员必须传递<code> ServiceInformationHolder </code>的实
 * 例作为方法调用中的相应参数;对于"inout"参数,程序员还必须填写要发送到服务器的"in"值。
 * <P> <code> ServiceInformation <代码>对象,用于在IDL方法中存储"out"和"inout"参数。在方法调用返回之前,ORB将填充从服务器返回的"out"值对应的值。
 * <P>
 *  如果<code> myServiceInformationHolder </code>是<code> ServiceInformationHolder </code>的实例,则可以使用<code> m
 * yServiceInformationHolder.value </code>访问存储在其<code> 。
 * 
 */
public final class ServiceInformationHolder
    implements org.omg.CORBA.portable.Streamable {

    /**
     * The <code>ServiceInformation</code> value held by this
     * <code>ServiceInformationHolder</code> object in its <code>value</code> field.
     * <p>
     *  在<code> value </code>字段中由此<code> ServiceInformationHolder </code>对象持有的<code> ServiceInformation </code>
     * 值。
     * 
     */
    public ServiceInformation value;

    /**
     * Constructs a new <code>ServiceInformationHolder</code> object with its
     * <code>value</code> field initialized to null.
     * <p>
     *  构造一个新的<code> ServiceInformationHolder </code>对象,其<code> value </code>字段初始化为null。
     * 
     */
    public ServiceInformationHolder() {
        this(null);
    }

    /**
     * Constructs a new <code>ServiceInformationHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>ServiceInformation</code> object.
     *
     * <p>
     * 构造一个新的<code> ServiceInformationHolder </code>对象,其<code> value </code>字段初始化为给定的<code> ServiceInformati
     * on </code>对象。
     * 
     * 
     * @param arg the <code>ServiceInformation</code> object with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>ServiceInformationHolder</code> object
     */
    public ServiceInformationHolder(org.omg.CORBA.ServiceInformation arg) {
        value = arg;
    }


    /**
     * Marshals the value in this <code>ServiceInformationHolder</code> object's
     * <code>value</code> field to the output stream <code>out</code>.
     *
     * <p>
     *  将此<code> ServiceInformationHolder </code>对象的<code> value </code>字段中的值调整为输出流<code> out </code>。
     * 
     * 
     * @param out the <code>OutputStream</code> object that will contain
     *               the CDR formatted data
     */
    public void _write(org.omg.CORBA.portable.OutputStream out) {
        org.omg.CORBA.ServiceInformationHelper.write(out, value);
    }

    /**
     * Reads unmarshalled data from the input stream <code>in</code> and assigns it to
     * the <code>value</code> field in this <code>ServiceInformationHolder</code> object.
     *
     * <p>
     *  从</code>中的输入流<code>中读取未组合的数据,并将其分配给此<code> ServiceInformationHolder </code>对象中的<code> value </code>字
     * 段。
     * 
     * 
     * @param in the <code>InputStream</code> object containing CDR
     *              formatted data from the wire
     */
    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = org.omg.CORBA.ServiceInformationHelper.read(in);
    }

    /**
     * Retrieves the <code>TypeCode</code> object that corresponds
     * to the value held in this <code>ServiceInformationHolder</code> object's
     * <code>value</code> field.
     *
     * <p>
     * 
     * @return    the type code for the value held in this <code>ServiceInformationHolder</code>
     *            object
     */
    public org.omg.CORBA.TypeCode _type() {
        return org.omg.CORBA.ServiceInformationHelper.type();
    }
}
