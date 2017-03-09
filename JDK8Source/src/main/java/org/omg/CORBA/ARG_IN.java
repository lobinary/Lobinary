/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * Signifies an "input" argument to an invocation,
 * meaning that the argument is being passed from the client to
 * the server.
 * <code>ARG_IN.value</code> is one of the possible values used to
 * indicate the direction in
 * which a parameter is being passed during an invocation performed
 * using the Dynamic Invocation Interface (DII).
 * <P>
 * The code fragment below shows a typical usage:
 * <PRE>
 *    ORB orb = ORB.init(args, null);
 *    org.omg.CORBA.NamedValue nv = orb.create_named_value(
 *         "IDLArgumentIdentifier", myAny, org.omg.CORBA.ARG_IN.value);
 * </PRE>
 *
 * <p>
 *  表示调用的"输入"参数,这意味着该参数正在从客户端传递到服务器。 <code> ARG_IN.value </code>是用于指示在使用动态调用接口(DII)执行调用期间传递参数的方向的可能值之一。
 * <P>
 *  下面的代码片段显示了一个典型的用法：
 * <PRE>
 *  ORB orb = ORB.init(args,null); org.omg.CORBA.NamedValue nv = orb.create_named_value("IDLArgumentIden
 * tifier",myAny,org.omg.CORBA.ARG_IN.value);。
 * 
 * @see     org.omg.CORBA.NamedValue
 * @since   JDK1.2
 */
public interface ARG_IN {

    /**
     * The value indicating an input argument.
     * <p>
     * </PRE>
     * 
     */
    int value = 1;
}
