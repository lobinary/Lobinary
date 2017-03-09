/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * An object used in the DII and DSI to describe
 * arguments and return values. <code>NamedValue</code> objects
 * are also used in the <code>Context</code>
 * object routines to pass lists of property names and values.
 * <P>
 * A <code>NamedValue</code> object contains:
 * <UL>
 * <LI>a name -- If the <code>NamedValue</code> object is used to
 * describe arguments to a request, the name will be an argument
 * identifier specified in the OMG IDL interface definition
 * for the operation being described.
 * <LI>a value -- an <code>Any</code> object
 * <LI>an argument mode flag -- one of the following:
 *   <UL>
 *    <LI><code>ARG_IN.value</code>
 *    <LI><code>ARG_OUT.value</code>
 *    <LI><code>ARG_INOUT.value</code>
 *    <LI>zero -- if this <code>NamedValue</code> object represents a property
 *                in a <code>Context</code> object rather than a parameter or
 *                return value
 *   </UL>
 * </UL>
 * <P>
 * The class <code>NamedValue</code> has three methods, which
 * access its fields.  The following code fragment demonstrates
 * creating a <code>NamedValue</code> object and then accessing
 * its fields:
 * <PRE>
 *    ORB orb = ORB.init(args, null);
 *    String s = "argument_1";
 *    org.omg.CORBA.Any myAny = orb.create_any();
 *    myAny.insert_long(12345);
 *    int in = org.omg.CORBA.ARG_IN.value;

 *    org.omg.CORBA.NamedValue nv = orb.create_named_value(
 *        s, myAny, in);
 *    System.out.println("This nv name is " + nv.name());
 *    try {
 *        System.out.println("This nv value is " + nv.value().extract_long());
 *        System.out.println("This nv flag is " + nv.flags());
 *    } catch (org.omg.CORBA.BAD_OPERATION b) {
 *      System.out.println("extract failed");
 *    }
 * </PRE>
 *
 * <P>
 * If this code fragment were put into a <code>main</code> method,
 * the output would be something like the following:
 * <PRE>
 *    This nv name is argument_1
 *    This nv value is 12345
 *    This nv flag is 1
 * </PRE>
 * <P>
 * Note that the method <code>value</code> returns an <code>Any</code>
 * object. In order to access the <code>long</code> contained in the
 * <code>Any</code> object,
 * we used the method <code>extract_long</code>.
 *
 * <p>
 *  在DII和DSI中用于描述参数和返回值的对象。 <code> NamedValue </code>对象也在<code> Context </code>对象例程中使用,以传递属性名称和值的列表。
 * <P>
 *  <code> NamedValue </code>对象包含：
 * <UL>
 *  <LI> a name  - 如果<code> NamedValue </code>对象用于描述请求的参数,则该名称将是在所描述的操作的OMG IDL接口定义中指定的参数标识符。
 *  <LI> a value  -  an <code> Any </code> object <LI>参数模式标志 - 以下之一：。
 * <UL>
 *  <LI> <code> ARG_IN.value </code> <LI> zero  - 如果此<code> NamedValue </code>对象表示<code> Context </code>
 * 对象中的属性,而不是参数或返回值。
 * </UL>
 * </UL>
 * <P>
 *  类<code> NamedValue </code>有三个方法,它们访问它的字段。以下代码片段演示了创建<code> NamedValue </code>对象,然后访问其字段：
 * <PRE>
 *  ORB orb = ORB.init(args,null); String s ="argument_1"; org.omg.CORBA.Any myAny = orb.create_any(); m
 * yAny.insert_long(12345); int in = org.omg.CORBA.ARG_IN.value;。
 * 
 * org.omg.CORBA.NamedValue nv = orb.create_named_value(s,myAny,in); System.out.println("This nv name is
 * "+ nv.name()); try {System.out.println("This nv value is"+ nv.value()。
 * extract_long()); System.out.println("This nv flag is"+ nv.flags()); } catch(org.omg.CORBA.BAD_OPERATI
 * ON b){System.out.println("extract failed"); }}。
 * 
 * @see Any
 * @see ARG_IN
 * @see ARG_INOUT
 * @see ARG_OUT
 *
 * @since       JDK1.2
 */

public abstract class NamedValue {

    /**
     * Retrieves the name for this <code>NamedValue</code> object.
     *
     * <p>
     * </PRE>
     * 
     * <P>
     *  如果这个代码片段被放入一个<code> main </code>方法,输出将是如下：
     * <PRE>
     *  这个nv名称是argument_1这个nv值是12345这个nv标志是1
     * </PRE>
     * <P>
     *  注意,方法<code> value </code>返回一个<code> Any </code>对象。
     * 为了访问<code> Any </code>对象中包含的<code> long </code>,我们使用方法<code> extract_long </code>。
     * 
     * @return                  a <code>String</code> object representing
     *                    the name of this <code>NamedValue</code> object
     */

    public abstract String name();

    /**
     * Retrieves the value for this <code>NamedValue</code> object.
     *
     * <p>
     * 
     * 
     * @return                  an <code>Any</code> object containing
     *                    the value of this <code>NamedValue</code> object
     */

    public abstract Any value();

    /**
     * Retrieves the argument mode flag for this <code>NamedValue</code> object.
     *
     * <p>
     *  检索此<code> NamedValue </code>对象的名称。
     * 
     * 
     * @return                  an <code>int</code> representing the argument
     *                    mode for this <code>NamedValue</code> object
     */

    public abstract int flags();

}
